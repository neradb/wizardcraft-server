package com.wplatform.muoline.network;

import com.wplatform.muoline.engine.GameEngine;
import com.wplatform.muoline.handler.PacketHandleException;
import com.wplatform.muoline.util.AccessLogger;
import com.wplatform.muoline.util.ExtendableThreadPoolExecutor;
import com.wplatform.muoline.util.SysProperties;
import com.wplatform.muoline.util.Threads;
import com.wplatform.muonline.domain.models.ServerRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AllInOneNetworkServer implements NetworkServer {

    private static final Logger LOG = LoggerFactory.getLogger(NetworkServer.class);
    private static final AccessLogger ACCESSLOGGER = new AccessLogger();


    private NioEventLoopGroup connectionServerBossGroup;
    private NioEventLoopGroup connectServerWorkerGroup;
    private NioEventLoopGroup gameServerBossGroup;
    private NioEventLoopGroup gameServerWorkerGroup;


    @Value("${server.connection-server.port}")
    private int connectionPort;

    //@Value("${server.game-server.port}")
    private int gamePorts[];


    @Value("${server.connection-server.internet-ip}")
    private String internetIp;


    @Autowired
    private GameEngine gameEngine;

    private ThreadPoolExecutor userExecutor;

    private ServerRegister serverRegister = new ServerRegister();

    public AllInOneNetworkServer() {
        this.userExecutor = createThreadPoolExecutor();
    }


    @Override
    public void run() {


        connectionServerBossGroup = new NioEventLoopGroup(SysProperties.CS_GROUPTHREADS, new DefaultThreadFactory("LoginServer-NettyBossGroup"));
        connectServerWorkerGroup = new NioEventLoopGroup(SysProperties.CS_WORKERTHREADS, new DefaultThreadFactory("LoginServer-NettyWorkerGroup"));

        ServerBootstrap connectionServerBootstrap = new ServerBootstrap()
                .group(connectionServerBossGroup, connectServerWorkerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.SO_RCVBUF, 1024 * 8)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(512, 2048, 1024 * 64))
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .childOption(ChannelOption.SO_RCVBUF, 1024 * 8)
                .childOption(ChannelOption.SO_SNDBUF, 1024 * 8)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //pipeline.addLast(new NetworkFrameDecoder());
                        //pipeline.addLast(new NetworkPacketEncoder());
                        pipeline.addLast(new ConnectionServerHandler());
                    }
                });


        gameServerBossGroup = new NioEventLoopGroup(SysProperties.GS_GROUPTHREADS, new DefaultThreadFactory("GameServer-NettyBossGroup"));
        gameServerWorkerGroup = new NioEventLoopGroup(SysProperties.GS_WORKERTHREADS, new DefaultThreadFactory("GameServer-NettyWorkerGroup"));

        ServerBootstrap gameServerBootstrap = new ServerBootstrap()
                .group(gameServerBossGroup, gameServerWorkerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.SO_RCVBUF, 1024 * 16)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(255, 2048, 65535))
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .childOption(ChannelOption.SO_RCVBUF, 1024 * 16)
                .childOption(ChannelOption.SO_SNDBUF, 1024 * 16)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(SysProperties.GS_IDLE_TIME_SECONDS, 0, 0));
                        pipeline.addLast(new NetworkFrameDecoder());
                        pipeline.addLast(new NetworkPacketEncoder());
                        pipeline.addLast(new GameServerHandler());
                    }
                });

        try {
            ChannelGroup serverChannels = new DefaultChannelGroup("ServerChannelGroups", GlobalEventExecutor.INSTANCE);
            Channel channel = connectionServerBootstrap.bind(connectionPort).sync().channel();
            serverChannels.add(channel);
            for (int port : gamePorts) {
                channel = gameServerBootstrap.bind(port).sync().channel();
                serverChannels.add(channel);
                //serverRegister.registerServer(ServerRegister.ServerInfo.create(internetIp, port));
            }
            serverChannels.newCloseFuture().sync();
            //bind.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.error("AllInOneNetworkServer binding interrupted.", e);
        } finally {
            connectionServerBossGroup.shutdownGracefully();
            connectServerWorkerGroup.shutdownGracefully();
            gameServerBossGroup.shutdownGracefully();
            gameServerWorkerGroup.shutdownGracefully();
            Threads.shutdownGracefully(userExecutor, 5, 5, TimeUnit.SECONDS);
        }


    }


    @PreDestroy
    public void shutdown() {
        Optional.ofNullable(connectionServerBossGroup).ifPresent(e -> e.shutdownGracefully());
        Optional.ofNullable(connectServerWorkerGroup).ifPresent(e -> e.shutdownGracefully());
        Optional.ofNullable(gameServerBossGroup).ifPresent(e -> e.shutdownGracefully());
        Optional.ofNullable(gameServerWorkerGroup).ifPresent(e -> e.shutdownGracefully());
        Threads.shutdownGracefully(userExecutor, 5, 5, TimeUnit.SECONDS);
    }


    private ThreadPoolExecutor createThreadPoolExecutor() {
        ExtendableThreadPoolExecutor.TaskQueue queue = new ExtendableThreadPoolExecutor.TaskQueue(SysProperties.THREAD_QUEUE_SIZE);
        int poolCoreSize = SysProperties.THREAD_POOL_SIZE_CORE;
        int poolMaxSize = SysProperties.THREAD_POOL_SIZE_MAX;
        poolMaxSize = poolMaxSize > poolCoreSize ? poolMaxSize : poolCoreSize;
        ExtendableThreadPoolExecutor userExecutor = new ExtendableThreadPoolExecutor(poolCoreSize, poolMaxSize, 5L,
                TimeUnit.MINUTES, queue, Threads.newThreadFactory("request-handler"));
        return userExecutor;
    }

    @Autowired
    public void setGamePorts(@Value("${server.game-server.port}") String gamePorts) {
        try {
            String[] configPorts = StringUtils.tokenizeToStringArray(gamePorts, ",;", true, true);
            int[] ports = new int[configPorts.length];
            for (int i = 0; i < configPorts.length; i++) {
                ports[i] = Integer.parseInt(configPorts[i]);
            }
            this.gamePorts = ports;
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect config server.game-server.port = " + gamePorts);
        }
        if(this.gamePorts.length < 1) {
            throw new IllegalArgumentException("Incorrect config server.game-server.port = " + gamePorts);
        }
    }

    @ChannelHandler.Sharable
    public class ConnectionServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ByteBuf buff = ctx.alloc().buffer(4);
            ctx.writeAndFlush(buff.writeBytes(new byte[]{(byte) 0xC1, 0x04, 0x00, 0x01}));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            NetworkConnection conn = NetworkConnection.wrapped(ctx.channel());
            NetworkTransport io = conn.newNetworkTransport((ByteBuf) msg);
            // we are not interested in the header and size;
            io.skipRead(2);
            int opcode = io.readUnsignedShort();
            try {
                switch (opcode) {
                    case 0xF4_03: { // Request ServerInfo information
                        int serverId = io.readUnsignedByte();
                        ServerRegister.ServerInfo server = serverRegister.getServerById(serverId);
                        if (server == null) {
                            server = serverRegister.getRegisteredServer().iterator().next();
                        }
                        io.writeByte(0xC1);
                        io.writeShort(0x00);//packet length;
                        io.writeShort(0xF4_03);

                        io.writeString(internetIp);
                        io.writeShortLE(gamePorts[0]);
                        break;
                    }

                    case 0xF4_06: { // Request ServerInfo list
                        Collection<ServerRegister.ServerInfo> servers = serverRegister.getRegisteredServer();
                        io.writeByte(0xC2);
                        io.writeShort(0x00);
                        io.writeShort(0xF4_06);
                        io.writeShort(8);
                        int i =0;
                        ServerRegister.ServerInfo server = serverRegister.getRegisteredServer().iterator().next();
                        for (int j = 0; j < 8; j++) {
                            io.writeShortLE(server.getServerId());
                            //ServerLoad = server.OnlinePlayerCount * 100f / server.MaximumPlayers
                            io.writeShortLE(server.getServerLoad() * i++);
                        }
                        break;
                    }
                    default:
                        LOG.warn("ConnectionServer Unkown packet opcode = {}, hexDump = ()", Integer.toHexString(opcode),
                                ByteBufUtil.hexDump(io.getInput(), 0, io.getInput().readableBytes()));
                        throw new PacketHandleException("Unkown packet opcode = "+Integer.toHexString(opcode));

                }
                Packets.setPacketSize(io.getOutput());
                System.out.println(ByteBufUtil.hexDump(io.getOutput()));
                conn.getChannel().writeAndFlush(io.getOutput());
            } catch (Exception e) {
                io.getOutput().release();
                LOG.error("an exception happen in ConnectionServer when handlePacket request", e);
            } finally {
                io.getInput().release();
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            NetworkConnection conn = NetworkConnection.wrapped(ctx.channel());
            conn.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            LOG.warn("Handler exceptionCaught", cause);
            ctx.close();
        }

    }

    @ChannelHandler.Sharable
    public class GameServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.ALL_IDLE) {
                    LOG.info("Game server userEventTriggered all_idle event");
                    ctx.close();
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }


        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            NetworkConnection connection = NetworkConnection.wrapped(ctx.channel());
            gameEngine.onConnected(connection);
            NetworkTransport networkTransport = connection.getOutgoingNetworkTransport();
            networkTransport.writeBytes(new byte[]{(byte) 0xC1, 0x0C, (byte) 0xF1, 0x00, 0x01});
            networkTransport.writeInt(connection.getConnectionId());
            networkTransport.writeString(SysProperties.GS_LOWEST_CLIENT_VERSION);
            ctx.channel().writeAndFlush(networkTransport.getOutput());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            userExecutor.execute(new HandleWorker(ctx.channel(), (ByteBuf) msg));
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            NetworkConnection connection = NetworkConnection.wrapped(ctx.channel());
            gameEngine.onConnectionClosed(connection);
            connection.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            LOG.error("Game server Handler exceptionCaught", cause);
            //ctx.close();
        }

    }


    /**
     * Execute the handler in user threads.
     */
    private class HandleWorker implements Runnable {
        private final Channel channel;
        private final ByteBuf packet;

        private HandleWorker(Channel channel, ByteBuf buf) {
            this.channel = channel;
            this.packet = buf;
        }

        @Override
        public void run() {
            NetworkConnection connection = NetworkConnection.wrapped(channel);
            NetworkTransport transport = connection.newNetworkTransport(packet);
            try {
                gameEngine.processPacket(transport);
                Packets.setPacketSize(transport.getOutput());
                channel.writeAndFlush(transport.getOutput());
            } catch (Throwable e) {
                transport.getOutput().release();
                LOG.error("an exception happen in GameServer when handlePacket request", e);
            } finally {
                transport.getInput().release();
                ACCESSLOGGER.log();
            }
        }

    }

}
