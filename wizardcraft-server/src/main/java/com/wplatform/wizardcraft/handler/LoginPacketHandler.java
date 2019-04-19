package com.wplatform.wizardcraft.handler;

import com.wplatform.wizardcraft.constants.LoginConstants;
import com.wplatform.wizardcraft.engine.GameEngine;
import com.wplatform.wizardcraft.network.NetworkConnection;
import com.wplatform.wizardcraft.network.NetworkTransport;
import com.wplatform.wizardcraft.proto.Proto;
import com.wplatform.wizardcraft.util.MD5Utils;
import com.wplatform.wizardcraft.domain.Account;
import com.wplatform.wizardcraft.repostory.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Optional;

@Slf4j
//@Processor(opcode = Proto.LoginLogoutGroup)
public class LoginPacketHandler implements PacketProcessor {


    @Value("${server.game-server.auto-registration}")
    private boolean autoCreateAccount;


    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private GameEngine gameEngine;


    @Override
    public void handlePacket(NetworkTransport io) throws PacketHandleException {
        short subcode = io.readUnsignedByte();
        switch (subcode) {
            case 0x01:
                this.handleLoginPacket(io);
                break;
            case 0x02:
                this.handleLogoutPacket(io);
                break;
            default:
                throw new IllegalArgumentException("Unknown packet subcode : " + Integer.toHexString(subcode));
        }
    }

    private void handleLoginPacket(NetworkTransport io) {
        String userName = io.decodeAndReadString(10);
        String password = io.decodeAndReadString(10);
        String version = io.readString(5);
        String mainSerial = io.readString(16);

        Optional<Account> accountable = accountRepository.findById(userName);
        Account account = null;
        if (accountable.isPresent()) {
            account = accountable.get();
        } else if (autoCreateAccount) {
            account = new Account();
            account.setAccountName(userName);
            account.setPasswordHash(MD5Utils.md5(password));
            account.setState(Account.State.NORMAL);
            account.setRegistrationDate(new Date());
            accountRepository.save(account);
        }
        int loginResult;
        if (account == null) {
            loginResult = LoginConstants.ACCOUNT_INVALID;
        } else if(gameEngine.getUserConnection(userName).isPresent()){
            loginResult = LoginConstants.ACCOUNT_ALREADY_CONNECTED;
        } else if (account.getState() == Account.State.BANNED
                || account.getState() == Account.State.TEMPORARILY_BANNED) {
            loginResult = LoginConstants.ACCOUNT_BLOCKED;
        } else {
            String loginPasswordHash = MD5Utils.md5(password);
            String passwordHash = account.getPasswordHash();
            if (loginPasswordHash.equals(passwordHash)) {
                NetworkConnection connection = io.getConnection();
                connection.setAttachment(LoginConstants.ACCOUNT_SESSION_KEY, account);
                gameEngine.addUserConnection(userName, connection);
                loginResult = LoginConstants.OK;
            } else {
                loginResult = LoginConstants.INVALID_PASSWORD;
            }
        }

        io.writeByte(0xC1);
        io.writeByte(0x05);
        io.writeByte(0xf1);
        io.writeByte(0x01);
        io.writeByte(loginResult);


    }

    private void handleLogoutPacket(NetworkTransport io) {


    }


}
