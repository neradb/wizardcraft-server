package com.wplatform.muoline.network;

import io.netty.buffer.ByteBuf;

public class Packets {

    /// <summary>
    /// A speak packet.
    /// </summary>
    public static final int Speak = 0; ////+

    /// <summary>
    /// A Whisper packet.
    /// </summary>
    public static final int Whisper = 2; ////+

    /// <summary>
    /// A walk packet.
    /// </summary>
    /// <remarks>ENG protocol specific.</remarks>
    public static final int Walk = 0xD4;

    /// <summary>
    /// A teleport packet.
    /// </summary>
    /// <remarks>ENG protocol specific.</remarks>
    public static final int Teleport = 0x15;

    /// <summary>
    /// A hit packet.
    /// </summary>
    /// <remarks>ENG protocol specific.</remarks>
    public static final int Hit = 0x11;

    /// <summary>
    /// A LoginLogoutGroup packet.
    /// </summary>
    public static final int LoginLogoutGroup = 0xF1; ////+

    /// <summary>
    /// A Ping packet.
    /// </summary>
    public static final int Ping = 0x0E; ////+

    /// <summary>
    /// A Checksum packet.
    /// </summary>
    public static final int Checksum = 0x03;

    /// <summary>
    /// A SkillAttack packet.
    /// </summary>
    public static final int SkillAttack = 0x19; ////+

    /// <summary>
    /// A WarpGate packet.
    /// </summary>
    public static final int WarpGate = 0x1C; ////+

    /// <summary>
    /// A TargetTeleport packet.
    /// </summary>
    public static final int TargetTeleport = 0xB0;

    /// <summary>
    /// A AreaSkill packet.
    /// </summary>
    public static final int AreaSkill = 0x1E; ////+

    /// <summary>
    /// A AreaSkillHit packet.
    /// </summary>
    public static final int AreaSkillHit = 0xDB; ////+

    /// <summary>
    /// A PickupItem packet.
    /// </summary>
    public static final int PickupItem = 0x22; // +

    /// <summary>
    /// A DropItem packet.
    /// </summary>
    public static final int DropItem = 0x23; // +

    /// <summary>
    /// A InventoryMove packet.
    /// </summary>
    public static final int InventoryMove = 0x24; // +

    /// <summary>
    /// A ConsumeItem packet.
    /// </summary>
    public static final int ConsumeItem = 0x26; ////+

    /// <summary>
    /// A TalkNPC packet.
    /// </summary>
    public static final int TalkNPC = 0x30; ////+

    /// <summary>
    /// A BuyNPCItem packet.
    /// </summary>
    public static final int BuyNPCItem = 0x32; ////+

    /// <summary>
    /// A SellNPCItem packet.
    /// </summary>
    public static final int SellNPCItem = 0x33; ////+

    /// <summary>
    /// A ItemRepair packet.
    /// </summary>
    public static final int ItemRepair = 0x34; ////+

    /// <summary>
    /// A TradeRequest packet.
    /// </summary>
    public static final int TradeRequest = 0x36; ////+

    /// <summary>
    /// A TradeButton packet.
    /// </summary>
    public static final int TradeButton = 0x3C; ////+

    /// <summary>
    /// A TradeCancel packet.
    /// </summary>
    public static final int TradeCancel = 0x3D; ////+

    /// <summary>
    /// A PersonalShopGroup packet.
    /// </summary>
    public static final int PersonalShopGroup = 0x3F; ////+

    /// <summary>
    /// A PartyRequest packet.
    /// </summary>
    public static final int PartyRequest = 0x40; ////+

    /// <summary>
    /// A PartyRequestAnswer packet.
    /// </summary>
    public static final int PartyRequestAnswer = 0x41; ////+

    /// <summary>
    /// A ChangeServerAuth packet.
    /// </summary>
    public static final int ChangeServerAuth = 0xB1; ////dont need yet--

    /// <summary>
    /// A GGAuth packet.
    /// </summary>
    public static final int GGAuth = 0x73; ////dont need yet--

    /// <summary>
    /// A LoggedIn packet.
    /// </summary>
    public static final int LoggedIn = 0xB8; ////+

    /// <summary>
    /// A CharacterGroup packet.
    /// </summary>
    public static final int CharacterGroup = 0xF3; ////+

    /// <summary>
    /// A Animation packet.
    /// </summary>
    public static final int Animation = 0x18; ////+

    /// <summary>
    /// A MagicCancel packet.
    /// </summary>
    public static final int MagicCancel = 0x1B;

    /// <summary>
    /// A CloseNPC packet.
    /// </summary>
    public static final int CloseNPC = 0x31; ////+

    /// <summary>
    /// A TradeAccept packet.
    /// </summary>
    public static final int TradeAccept = 0x37; ////+

    /// <summary>
    /// A TradeMoney packet.
    /// </summary>
    public static final int TradeMoney = 0x3A; ////+

    /// <summary>
    /// A RequestPartyList packet.
    /// </summary>
    public static final int RequestPartyList = 0x42; ////+

    /// <summary>
    /// A PartyKick packet.
    /// </summary>
    public static final int PartyKick = 0x43; ////+

    ////Guild Stuff:

    /// <summary>
    /// A GuildJoinRequest packet.
    /// </summary>
    public static final int GuildJoinRequest = 0x50; ////+

    /// <summary>
    /// A GuildJoinAnswer packet.
    /// </summary>
    public static final int GuildJoinAnswer = 0x51; ////+

    /// <summary>
    /// A RequestGuildList packet.
    /// </summary>
    public static final int RequestGuildList = 0x52; ////+

    /// <summary>
    /// A GuildKickPlayer packet.
    /// </summary>
    public static final int GuildKickPlayer = 0x53; ////+

    /// <summary>
    /// A GuildMasterAnswer packet.
    /// </summary>
    public static final int GuildMasterAnswer = 0x54; ////+

    /// <summary>
    /// A GuildMasterInfoSave packet.
    /// </summary>
    public static final int GuildMasterInfoSave = 0x55; ////+

    /// <summary>
    /// A GuildMasterCreateCancel packet.
    /// </summary>
    public static final int GuildMasterCreateCancel = 0x56; ////dun need to handle i think

    /// <summary>
    /// A GuildWarReqRes packet.
    /// </summary>
    public static final int GuildWarReqRes = 0x61;

    /// <summary>
    /// A GuildInfoRequest packet.
    /// </summary>
    public static final int GuildInfoRequest = 0x66; ////+

    ////Castle Siege:

    /// <summary>
    /// A CastleSiegeGroup packet.
    /// </summary>
    public static final int CastleSiegeGroup = 0xB1;

    /// <summary>
    /// A CSRequest_NPC_DB_List packet.
    /// </summary>
    public static final int CSRequest_NPC_DB_List = 0xB3;

    /// <summary>
    /// A RequestCSRegGuildList packet.
    /// </summary>
    public static final int RequestCSRegGuildList = 0xB4;

    /// <summary>
    /// A RequestCSAttGuildList packet.
    /// </summary>
    public static final int RequestCSAttGuildList = 0xB5;

    /// <summary>
    /// A RequestCSWeaponUse packet.
    /// </summary>
    public static final int RequestCSWeaponUse = 0xB7;

    /// <summary>
    /// A RequestGuildMarkCastleOwner packet.
    /// </summary>
    public static final int RequestGuildMarkCastleOwner = 0xB9;
    ////Lahap:

    /// <summary>
    /// A JewelMix packet.
    /// </summary>
    public static final int JewelMix = 0xBC; ////+

    /// <summary>
    /// A CrywolfGroup packet.
    /// </summary>
    public static final int CrywolfGroup = 0xBD;

    ////Alliance Stuff:

    /// <summary>
    /// A GuildAssignStatus packet.
    /// </summary>
    public static final int GuildAssignStatus = 0xBE;

    /// <summary>
    /// A GuildAssignType packet.
    /// </summary>
    public static final int GuildAssignType = 0xE2;

    /// <summary>
    /// A RequestAllyJoinLeave packet.
    /// </summary>
    public static final int RequestAllyJoinLeave = 0xE5;

    /// <summary>
    /// A AnswerAllyJoinLeave packet.
    /// </summary>
    public static final int AnswerAllyJoinLeave = 0xE6;

    /// <summary>
    /// A RequestAllyList packet.
    /// </summary>
    public static final int RequestAllyList = 0xE9;

    /// <summary>
    /// A RequestAllyKickGuild packet.
    /// </summary>
    public static final int RequestAllyKickGuild = 0xEB;

    ////Vault:

    /// <summary>
    /// A VaultMoneyInOut packet.
    /// </summary>
    public static final int VaultMoneyInOut = 0x81; ////+

    /// <summary>
    /// A VaultClose packet.
    /// </summary>
    public static final int VaultClose = 0x82; ////+

    /// <summary>
    /// A VaultPassword packet.
    /// </summary>
    public static final int VaultPassword = 0x83; ////+

    ////CM:

    /// <summary>
    /// A ChaosMachineMix packet.
    /// </summary>
    public static final int ChaosMachineMix = 0x86; ////+

    /// <summary>
    /// A ChaosMachineClose packet.
    /// </summary>
    public static final int ChaosMachineClose = 0x87; ////+

    /// <summary>
    /// A WarpCommand packet.
    /// </summary>
    public static final int WarpCommand = 0x8E; ////+

    /// <summary>
    /// A DevilSquareEnter packet.
    /// </summary>
    public static final int DevilSquareEnter = 0x90;

    /// <summary>
    /// A DevilSquareRemainingTime packet.
    /// </summary>
    public static final int DevilSquareRemainingTime = 0x91;

    /// <summary>
    /// A RegEventChip packet.
    /// </summary>
    public static final int RegEventChip = 0x95;

    /// <summary>
    /// A GetMutoNum packet.
    /// </summary>
    public static final int GetMutoNum = 0x96; ////?

    /// <summary>
    /// A UseEndEventChip packet.
    /// </summary>
    public static final int UseEndEventChip = 0x97; ////?

    /// <summary>
    /// A UseRenaExchangeZen packet.
    /// </summary>
    public static final int UseRenaExchangeZen = 0x98; ////?

    /// <summary>
    /// A RequestChangeServer packet.
    /// </summary>
    public static final int RequestChangeServer = 0x99;

    /// <summary>
    /// A RequestQuestInfo packet.
    /// </summary>
    public static final int RequestQuestInfo = 0xA0;

    /// <summary>
    /// A SetQuestState packet.
    /// </summary>
    public static final int SetQuestState = 0xA2;

    /// <summary>
    /// A PetItemCommand packet.
    /// </summary>
    public static final int PetItemCommand = 0xA7;

    /// <summary>
    /// A RequestPetItemInfo packet.
    /// </summary>
    public static final int RequestPetItemInfo = 0xA9; ////(Horse and Raven Exp; lvl etc...)

    ////Duel:

    /// <summary>
    /// A RequestDuelStart packet.
    /// </summary>
    public static final int RequestDuelStart = 0xAA;

    /// <summary>
    /// A RequestDuelEnd packet.
    /// </summary>
    public static final int RequestDuelEnd = 0xAB;

    /// <summary>
    /// A RequestDuelOK packet.
    /// </summary>
    public static final int RequestDuelOK = 0xAC;
    ////BC:

    /// <summary>
    /// A RequestEnterBC packet.
    /// </summary>
    public static final int RequestEnterBC = 0x9A;

    /// <summary>
    /// A BCStuff packet.
    /// </summary>
    public static final int BCStuff = 0x9B;

    /// <summary>
    /// A RequestEventEnterCount packet.
    /// </summary>
    public static final int RequestEventEnterCount = 0x9F;

    /// <summary>
    /// A RequestLottoRegister packet.
    /// </summary>
    public static final int RequestLottoRegister = 0x9D;

    /// <summary>
    /// A RequestEnterChaosCastle packet.
    /// </summary>
    public static final int RequestEnterChaosCastle = 0xAF;
    ////MUssenger / Friendlist:

    /// <summary>
    /// A FriendListRequest packet.
    /// </summary>
    public static final int FriendListRequest = 0xC0; ////? i think it doesnt exit. friendlist is always sent after char selection

    /// <summary>
    /// A FriendAdd packet.
    /// </summary>
    public static final int FriendAdd = 0xC1; ////+

    /// <summary>
    /// A FriendAddReponse packet.
    /// </summary>
    public static final int FriendAddReponse = 0xC2; ////+

    /// <summary>
    /// A FriendDelete packet.
    /// </summary>
    public static final int FriendDelete = 0xC3; ////+

    /// <summary>
    /// A FriendStateClient packet.
    /// </summary>
    public static final int FriendStateClient = 0xC4; ////+

    /// <summary>
    /// A FriendMemoSend packet.
    /// </summary>
    public static final int FriendMemoSend = 0xC5; ////+

    /// <summary>
    /// A FriendMemoReadRequest packet.
    /// </summary>
    public static final int FriendMemoReadRequest = 0xC7; ////+

    /// <summary>
    /// A FriendMemoDelete packet.
    /// </summary>
    public static final int FriendMemoDelete = 0xC8; ////+

    /// <summary>
    /// A FriendMemoListRequest packet.
    /// </summary>
    public static final int FriendMemoListRequest = 0xC9; ////? did not see this yet

    /// <summary>
    /// A ChatRoomCreate packet.
    /// </summary>
    public static final int ChatRoomCreate = 0xCA; ////+

    /// <summary>
    /// A ChatRoomInvitationReq packet.
    /// </summary>
    public static final int ChatRoomInvitationReq = 0xCB;

    /// <summary>
    /// A KanturuGroup packet.
    /// </summary>
    public static final int KanturuGroup = 0xD1;

    /// <summary>
    /// A CashShopGroup packet.
    /// </summary>
    public static final int CashShopGroup = 0xD2;


    public static int getPacketHeaderSize(ByteBuf buff) {
        short b0 = buff.getUnsignedByte(0);
        switch (b0) {
            case 0xC1:
            case 0xC3:
                return 2;
            case 0xC2:
            case 0xC4:
                return 3;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(b0));
        }
    }

    public static short getPacketSize(ByteBuf buff) {
        short b0 = buff.getUnsignedByte(0);
        switch (b0) {
            case 0xC1:
            case 0xC3:
                return buff.getUnsignedByte(1);
            case 0xC2:
            case 0xC4:
                return buff.getShort(1);
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(b0));
        }
    }

    public static void setPacketSize(ByteBuf buff) {
        short b0 = buff.getUnsignedByte(0);
        switch (b0) {
            case 0xC1:
            case 0xC3:
                buff.setByte(1, buff.readableBytes());
                break;
            case 0xC2:
            case 0xC4:
                buff.setShort(1, buff.readableBytes());
                break;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0] : " + Integer.toHexString(b0));
        }
    }


}
