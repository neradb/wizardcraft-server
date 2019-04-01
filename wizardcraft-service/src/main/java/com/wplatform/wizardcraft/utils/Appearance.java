package com.wplatform.wizardcraft.utils;

import com.google.common.collect.Maps;
import com.wplatform.wizardcraft.constants.InventoryConstants;
import com.wplatform.wizardcraft.domain.Characters;
import com.wplatform.wizardcraft.domain.Item;
import com.wplatform.wizardcraft.items.ItemOptionTypes;

import java.util.List;
import java.util.Map;


public class Appearance {

    private enum PetIndex {
        ANGEL(0),
        IMP(1),
        UNICORN(2),
        DINORANT(3),
        DARK_HORSE(4),
        DARK_RAVEN(5),
        FENRIR(37),
        DEMON(64),
        SPIRIT_OF_GUARDIAN(65),
        RUDOLPH(66),
        PANDA(80),
        PET_UNICORN(106),
        SKELETON(123);

        private static Map<Integer, PetIndex> mapping = Maps.newHashMap();

        static {
            for (PetIndex value : PetIndex.values()) {
                mapping.put(value.idx, value);
            }
        }

        final int idx;

        PetIndex(int idx) {
            this.idx = idx;
        }

        public static PetIndex valueOf(int number) {
            return mapping.get(number);
        }
    }

    private enum WingIndex {
        WingsOfElf(0),
        WingsOfHeaven(1),
        WingsOfSatan(2),
        WingsOfMistery(41),
        WingsOfSpirit(3),
        WingsOfSoul(4),
        WingsOfDragon(5),
        WingsOfDarkness(6),
        CapeOfLord(30), // other group, but index not overlapping with other wings
        WingsOfDespair(42),
        CapeOfFighter(49),
        WingOfStorm(36),
        WingOfEternal(37),
        WingOfIllusion(38),
        WingOfRuin(39),
        CapeOfEmperor(40),
        WingOfDimension(43),
        CapeOfOverrule(50),
        SmallCapeOfLord(130),
        SmallWingsOfMistery(131),
        SmallWingsOfElf(132),
        SmallWingsOfHeaven(133),
        SmallWingsOfSatan(134),
        SmallCloakOfWarrior(135);
        final int idx;

        WingIndex(int idx) {
            this.idx = idx;
        }

        private static Map<Integer, WingIndex> mapping = Maps.newHashMap();

        static {
            for (WingIndex value : WingIndex.values()) {
                mapping.put(value.idx, value);
            }
        }


        public static WingIndex valueOf(int number) {
            return mapping.get(number);
        }
    }

    /// <inheritdoc/>
    private static int NeededSpace = 18;


    /// <inheritdoc/>
    public static byte[] writeAppearanceData(Characters characters) {
        byte[] target = new byte[NeededSpace];

        Item[] itemArray = new Item[InventoryConstants.EquippableSlotsCount];
        List<Item> equippedItems = characters.getEquippedItems();
        for (Item e : equippedItems) {
            byte itemSlot = e.getItemSlot();
            itemArray[itemSlot] = e;
        }


        target[0] = (byte) (characters.getCharacterClass().Number << 3 & 0xF8);
        target[0] |= (byte) characters.getPose().value;
        setHand(target, itemArray[InventoryConstants.LeftHandSlot], 1, 12);

        setHand(target, itemArray[InventoryConstants.RightHandSlot], 2, 13);

        setArmorPiece(target, itemArray[InventoryConstants.HelmSlot], 3, true, 0x80, 13, false);

        setArmorPiece(target, itemArray[InventoryConstants.ArmorSlot], 3, false, 0x40, 14, true);

        setArmorPiece(target, itemArray[InventoryConstants.PantsSlot], 4, true, 0x20, 14, false);

        setArmorPiece(target, itemArray[InventoryConstants.GlovesSlot], 4, false, 0x10, 15, true);

        setArmorPiece(target, itemArray[InventoryConstants.BootsSlot], 5, true, 0x08, 15, false);

        setItemLevels(target, itemArray);

        if (characters.hasFullAncientSetEquipped()) {
            target[11] |= 0x01;
        }

        addWing(target, itemArray[InventoryConstants.WingsSlot]);

        addPet(target, itemArray[InventoryConstants.PetSlot]);
        return target;

    }


    private static void setHand(byte[] preview, Item item, int indexIndex, int groupIndex) {
        if (item == null) {
            preview[indexIndex] = (byte) 0xFF;
            preview[groupIndex] |= 0xF0;
        } else {
            preview[indexIndex] = (byte) item.getItemDefinition().Number;
            preview[groupIndex] |= (byte) (item.getItemDefinition().Group << 5);
        }
    }

    private static byte getOrMaskForHighNibble(int value) {
        return (byte) ((value << 4) & 0xF0);
    }

    private static byte getOrMaskForLowNibble(int value) {
        return (byte) (value & 0x0F);
    }

    private static void setEmptyArmor(byte[] preview, int firstIndex, boolean firstIndexHigh, int secondIndexMask, int thirdIndex, boolean thirdIndexHigh) {
        // if the item is not equipped every index bit is set to 1
        preview[firstIndex] |= firstIndexHigh ? getOrMaskForHighNibble(0x0F) : getOrMaskForLowNibble(0x0F);
        preview[9] |= (secondIndexMask & 0xFF);
        preview[thirdIndex] |= thirdIndexHigh ? getOrMaskForHighNibble(0x0F) : getOrMaskForLowNibble(0x0F);
    }

    private static void setArmorItemIndex(byte[] preview, Item item, int firstIndex, boolean firstIndexHigh, int secondIndexMask, int thirdIndex, boolean thirdIndexHigh) {
        preview[firstIndex] |= firstIndexHigh ? getOrMaskForHighNibble(item.getItemDefinition().Number) : getOrMaskForLowNibble(item.getItemDefinition().Number);
        byte multi = (byte) (item.getItemDefinition().Number / 16);
        if (multi > 0) {
            byte bit1 = (byte) (multi % 2);
            byte byte2 = (byte) (multi / 2);
            if (bit1 == 1) {
                preview[9] |= secondIndexMask & 0xFF;
            }

            if (byte2 > 0) {
                preview[thirdIndex] |= thirdIndexHigh ? getOrMaskForHighNibble(byte2) : getOrMaskForLowNibble(byte2);
            }
        }
    }

    private static void setArmorPiece(byte[] preview, Item item, int firstIndex, boolean firstIndexHigh, int secondIndexMask, int thirdIndex, boolean thirdIndexHigh) {
        if (item == null) {
            setEmptyArmor(preview, firstIndex, firstIndexHigh, secondIndexMask, thirdIndex, thirdIndexHigh);
        } else {
            // item id
            setArmorItemIndex(preview, item, firstIndex, firstIndexHigh, secondIndexMask, thirdIndex, thirdIndexHigh);

            // exc bit
            if (isExcellent(item)) {
                preview[10] |= secondIndexMask;
            }

            // ancient bit
            if (isAncient(item)) {
                preview[11] |= secondIndexMask;
            }
        }
    }

    private static void setItemLevels(byte[] preview, Item[] itemArray) {
        int levelindex = 0;
        for (int i = 0; i < 7; i++) {
            if (itemArray[i] != null) {
                levelindex |= ((itemArray[i].getLevel() - 1) / 2) << (i * 3);
            }
        }

        preview[6] = (byte) ((levelindex >> 16) & 255);
        preview[7] = (byte) ((levelindex >> 8) & 255);
        preview[8] = (byte) (levelindex & 255);
    }

    private static void addWing(byte[] preview, Item wing) {
        if (wing == null) {
            return;
        }
        WingIndex wingIndex = WingIndex.valueOf(wing.getItemDefinition().Number);
        switch (wingIndex) {
            case WingsOfElf:
            case WingsOfHeaven:
            case WingsOfSatan:
            case WingsOfMistery:
                preview[5] |= 0x04;
                break;
            case WingsOfSpirit:
            case WingsOfSoul:
            case WingsOfDragon:
            case WingsOfDarkness:
            case CapeOfLord:
            case WingsOfDespair:
            case CapeOfFighter:
                preview[5] |= 0x08;
                break;
            case WingOfStorm:
            case WingOfEternal:
            case WingOfIllusion:
            case WingOfRuin:
            case CapeOfEmperor:
            case WingOfDimension:
            case CapeOfOverrule:
            case SmallCapeOfLord:
            case SmallWingsOfMistery:
            case SmallWingsOfElf:
            case SmallWingsOfHeaven:
            case SmallWingsOfSatan:
            case SmallCloakOfWarrior:
                preview[5] |= 0x0C;
                break;
            default:
                // nothing to do
                break;
        }

        switch (wingIndex/*(WingIndex)wing.Definition.Number*/) {
            case WingsOfElf:
            case WingsOfSpirit:
            case WingOfStorm:
                preview[9] |= 0x01;
                break;
            case WingsOfHeaven:
            case WingsOfSoul:
            case WingOfEternal:
                preview[9] |= 0x02;
                break;
            case WingsOfSatan:
            case WingsOfDragon:
            case WingOfIllusion:
                preview[9] |= 0x03;
                break;
            case WingsOfMistery:
            case WingsOfDarkness:
            case WingOfRuin:
                preview[9] |= 0x04;
                break;
            case CapeOfLord:
            case CapeOfEmperor:
                preview[9] |= 0x05;
                break;
            case WingsOfDespair:
            case WingOfDimension:
                preview[9] |= 0x06;
                break;
            case CapeOfFighter:
            case CapeOfOverrule:
                preview[9] |= 0x07;
                break;
            case SmallCapeOfLord:
                preview[17] |= 0x20;
                break;
            case SmallWingsOfMistery:
                preview[17] |= 0x40;
                break;
            case SmallWingsOfElf:
                preview[17] |= 0x60;
                break;
            case SmallWingsOfHeaven:
                preview[17] |= 0x80;
                break;
            case SmallWingsOfSatan:
                preview[17] |= 0xA0;
                break;
            case SmallCloakOfWarrior:
                preview[17] |= 0xC0;
                break;
            default:
                // nothing to do
                break;
        }
    }

    private static void addPet(byte[] preview, Item pet) {
        if (pet == null) {
            preview[5] |= 0b0000_0011;
            return;
        }
        short number = pet.getItemDefinition().Number;
        PetIndex petIndex = PetIndex.valueOf(number);

        switch (petIndex) {
            case ANGEL:
            case IMP:
            case UNICORN:
                preview[5] |= (byte) number;
                break;
            case DINORANT:
                preview[5] |= 0x03;
                preview[10] |= 0x01;
                break;
            case DARK_HORSE:
                preview[5] = 0x03;
                preview[12] |= 0x01;
                break;
            case FENRIR:
                preview[5] |= 0x03;
                preview[10] &= 0xFE;
                preview[12] &= 0xFE;
                preview[12] |= 0x04;
                preview[16] = 0x00;

                if (pet.getVisibleOptions().contains(ItemOptionTypes.BlackFenrir)) {
                    preview[16] |= 0x01;
                }

                if (pet.getVisibleOptions().contains(ItemOptionTypes.BlueFenrir)) {
                    preview[16] |= 0x02;
                }

                if (pet.getVisibleOptions().contains(ItemOptionTypes.GoldFenrir)) {
                    preview[17] |= 0x01;
                }

                break;
            default:
                preview[5] |= 0x03;
                break;
        }

        switch (petIndex) {
            case PANDA:
                preview[16] |= 0xE0;
                break;
            case PET_UNICORN:
                preview[16] |= 0xA0;
                break;
            case SKELETON:
                preview[16] |= 0x60;
                break;
            case RUDOLPH:
                preview[16] |= 0x80;
                break;
            case SPIRIT_OF_GUARDIAN:
                preview[16] |= 0x40;
                break;
            case DEMON:
                preview[16] |= 0x20;
                break;
            default:
                // no further flag required.
                break;
        }
    }

    private static boolean isExcellent(Item item) {
        return item.getVisibleOptions().contains(ItemOptionTypes.Excellent);
    }

    private static boolean isAncient(Item item) {
        return item.getVisibleOptions().contains(ItemOptionTypes.AncientOption);
    }
}
