package com.wplatform.wizardcraft.handler;

import com.wplatform.wizardcraft.constants.LoginConstants;
import com.wplatform.wizardcraft.network.NetworkTransport;
import com.wplatform.wizardcraft.network.Packets;
import com.wplatform.wizardcraft.domain.Characters;
import com.wplatform.wizardcraft.domain.Account;
import com.wplatform.wizardcraft.repostory.CharacterRepository;
import com.wplatform.wizardcraft.utils.Appearance;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
@Packet(opcode = Packets.CHARACTER_GROUP)
public class CharacterPacketHandler implements PacketHandler {


    @Autowired
    private CharacterRepository characterRepository;


    @Override
    public void handlePacket(NetworkTransport io) throws PacketHandleException {
        short subcode = io.readUnsignedByte();
        switch (subcode) {
            case 0x00:
                this.RequestCharacterList(io);
                break;
            case 0x01:
                this.ReadCreateCharacter(io);
                break;
            case 0x02:
                this.ReadDeleteCharacter(io);
                break;
            case 0x15:
                this.ReadFocusCharacter(io);
                break;
            case 0x03:
                this.ReadSelectCharacter(io);
                break;
            case 0x06:
                this.ReadIncreaseStats(io);
                break;
            case 0x12: ////Data Loaded by Client
                this.ClientReadyAfterMapChange();
                break;
            case 0x30: ////GCSkillKeyRecv
                this.SaveKeyConfiguration(io);
                break;
            case 0x52:
                this.AddMasterSkillPoint(io);
                break;
            default:
                throw new IllegalArgumentException("Unknown packet subcode : " + Integer.toHexString(subcode));

        }
    }

    private void AddMasterSkillPoint(NetworkTransport io) {

    }

    private void SaveKeyConfiguration(NetworkTransport io) {

    }

    private void ClientReadyAfterMapChange() {

    }

    private void ReadIncreaseStats(NetworkTransport io) {

    }

    private void ReadSelectCharacter(NetworkTransport io) {

    }

    private void ReadFocusCharacter(NetworkTransport io) {

    }

    private void ReadDeleteCharacter(NetworkTransport io) {

    }

    private void ReadCreateCharacter(NetworkTransport io) {
        String characterName = io.readString(10);
        int classNumber = io.readUnsignedByte() >> 2;
        //this.createCharacterAction.CreateCharacter(player, characterName, classNumber);
        Account user = io.getConnection().getSession().getAccount();
        List<Characters> characters = user.getCharacters();
        int freeSlot = characters.stream()
                .map(Characters::getCharacterSlot)
                .max(Integer::compare)
                .orElse(1);
        Characters character = new Characters();
        character.setCharacterSlot(freeSlot);
        character.setAccountName(user.getAccountName());
        //character.setCharacterClass();
        character.setCharacterStatus(Characters.CharacterStatus.Normal);
        character.setLevel(1);
        character.setExperience(0);
        character.setName(characterName);
        character.setCreateDate(new Date());
        characterRepository.save(character);
    }

    private void RequestCharacterList(NetworkTransport io) {
        Account user = io.getConnection().getSession().getAccount();
        byte allowed = 0;
        List<Characters> characters = characterRepository.findByAccountName(user.getAccountName());
        io.writeByte(0xC1);
        io.writeByte(0x00);
        io.writeByte(0xF3);
        io.writeByte(0x00);
        io.writeByte(allowed);
        io.writeByte(0x00);
        io.writeByte(characters.size());
        io.writeByte(0x00);
        for (Characters character : characters) {
            //var characterBlock = packet.Slice((i * characterSize) + 8, characterSize);
            io.writeByte(character.getCharacterSlot());
            io.writeFixLengthString(character.getName(), 10);
            io.writeByte(0x01); // unknown
            io.writeShortLE(character.getLevel());
            //characterBlock[12] = level.GetLowByte();
            //characterBlock[13] = level.GetHighByte();
            io.writeByte(character.getCharacterStatus().value);
            //characterBlock[14] = (byte)character.CharacterStatus; // | 0x10 for item block?
            byte[] appearanceData = Appearance.writeAppearanceData(character);
            io.writeBytes(appearanceData);
        }


    }


}
