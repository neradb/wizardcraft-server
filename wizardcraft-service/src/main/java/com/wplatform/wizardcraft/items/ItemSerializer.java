package com.wplatform.wizardcraft.items;

import com.wplatform.wizardcraft.configuration.GameConfiguration;
import com.wplatform.wizardcraft.domain.Item;
import com.wplatform.wizardcraft.domain.ItemOptionLink;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class ItemSerializer {


    private static final int LUCK_FLAG = 4;

    private static final int SKILL_FLAG = 128;

    private static final int LEVEL_MASK = 0x78;

    private static final int GUARDIAN_OPTION_FLAG = 0x08;

    private static final int NO_SOCKET = 0xFF;

    private static final int EMPTY_SOCKET = 0xFE;

    private static final int MAXIMUM_SOCKETS = 5;

    private static final int ANCIENT_BONUS_LEVEL_MASK = 0b1100;
    private static final int ANCIENT_DISCRIMINATOR_MASK = 0b0011;
    private static final int AncientMask = ANCIENT_BONUS_LEVEL_MASK | ANCIENT_DISCRIMINATOR_MASK;

    private static final int BLACK_FENRIR_FLAG = 0x01;
    private static final int BLUE_FENRIR_FLAG = 0x02;
    private static final int GOLD_FENRIR_FLAG = 0x04;

    private static final int NEEDED_SPACE = 12;

    public byte[] serializeItem(Item item) {
        byte[] target = new byte[NEEDED_SPACE];
        target[0] = (byte) item.getItemDefinition().Number;
        target[1] = (byte) ((item.getLevel() << 3) & LEVEL_MASK);

        Optional<ItemOptionLink> first = item.ItemOptions.stream().filter(e -> e.ItemOption.OptionType == ItemOptionTypes.Option).findFirst();
        if (first.isPresent()) {
            ItemOptionLink itemOption = first.get();
            // The item option level is splitted into 2 parts.
            target[1] += (byte) (itemOption.Level & 3); // setting the first 2 bits
            target[3] = (byte) ((itemOption.Level & 4) << 4); // The highest bit is placed into the 2nd bit of the exc byte (0x40).

            // Some items (wings) can have different options (3rd wings up to 3!)
            // Alternate options are set at array[startIndex + 3] |= 0x20 and 0x10
            if (itemOption.ItemOption.Number != 0) {
                target[3] |= (byte) ((itemOption.ItemOption.Number & 0b11) << 4);
            }
        }

        target[2] = item.getDurability();

        target[3] |= getExcellentByte(item);

        if ((item.getItemDefinition().Number & 0x100) == 0x100) {
            // Support for 512 items per Group
            target[3] |= 0x80;
        }

        if (item.ItemOptions.stream().anyMatch(e -> e.ItemOption.OptionType == ItemOptionTypes.Luck)) {
            target[1] |= LUCK_FLAG;
        }

        if (item.hasSkill) {
            target[1] |= SKILL_FLAG;
        }

        Optional<ItemSetGroup> itemSetGroup = item.ItemSetGroups.stream().filter(e -> e.AncientSetDiscriminator != 0).findFirst();
        if (itemSetGroup.isPresent()) {
            ItemSetGroup ancientSet = itemSetGroup.get();
            target[4] |= (byte) (ancientSet.AncientSetDiscriminator & ANCIENT_DISCRIMINATOR_MASK);

            // an ancient item always has an ancient bonus option (e.g. 5 Vitality). If that's not the case, we should throw an exception.
            Optional<ItemOptionLink> itemOptionLink = item.ItemOptions.stream().filter(e -> e.ItemOption.OptionType == ItemOptionTypes.AncientBonus).findFirst();
            ItemOptionLink ancientBonus = itemOptionLink.orElseThrow(() -> new IllegalStateException(format("Item %s belongs to an ancient set but doesn't have an ancient bonus option", item)));
            target[4] |= (byte) ((ancientBonus.Level << 2) & ANCIENT_BONUS_LEVEL_MASK);
        }

        target[5] = (byte) (item.getItemDefinition().Group << 4);
        if (item.ItemOptions.stream().allMatch(e -> e.ItemOption.OptionType == ItemOptionTypes.GuardianOption)) {
            target[5] |= GUARDIAN_OPTION_FLAG;
        }

        target[6] = (byte) (getHarmonyByte(item) | getFenrirByte(item));
        setSocketBytes(target, 7, item);

        return target;
    }

    /// <inheritdoc />
    public Item deserializeItem(byte[] array, GameConfiguration gameConfiguration) {
        int itemNumber = array[0] + ((array[0] & 0x80) << 1);
        int itemGroup = (array[5] & 0xF0) >> 4;
        Optional<ItemDefinition> first = gameConfiguration
                .Items.stream()
                .filter(e -> e.Number == itemNumber && e.Group == itemGroup)
                .findFirst();

        ItemDefinition definition = first.orElseThrow(() -> new IllegalStateException("Couldn't find the item definition for the given byte array. Extracted item number and group: " + itemNumber + ", " + itemGroup));

        Item item = new Item();

        item.setItemDefinition(definition);
        item.setLevel((byte) ((array[1] & LEVEL_MASK) >> 3));

        item.setDurability(array[2]);

        Optional<ItemOptionDefinition> excellentFirst = item.getItemDefinition().PossibleItemOptions.stream()
                .filter(e -> e.PossibleOptions.stream().anyMatch(i -> i.OptionType == ItemOptionTypes.Excellent))
                .findFirst();

        Optional<ItemOptionDefinition> wingFirst = item.getItemDefinition().PossibleItemOptions.stream()
                .filter(e -> e.PossibleOptions.stream().anyMatch(i -> i.OptionType == ItemOptionTypes.Wing))
                .findFirst();

        if (excellentFirst.isPresent()) {
            readExcellentOption(array[3], item, excellentFirst.get());
        } else if (wingFirst.isPresent()) {
            readWingOption(array[3], item, wingFirst.get());
        } else {
            // set nothing.
        }

        readSkillFlag(array[1], item);
        readLuckOption(array[1], item);
        readNormalOption(array, item);
        readAncientOption(array[4], item);
        readLevel380Option(array[5], item);
        if (item.getItemDefinition().PossibleItemOptions.stream()
                .anyMatch(o -> o.PossibleOptions.stream().anyMatch(e -> e.OptionType == ItemOptionTypes.BlackFenrir))) {
            readFenrirOptions(array[6], item);
        } else {
            readHarmonyOption(array[6], item);
        }
        readSockets(array, 7, item);
        return item;
    }

    private static void readSkillFlag(byte optionByte, Item item) {
        if ((optionByte & SKILL_FLAG) == 0) {
            return;
        }

        if (item.getItemDefinition().Skill == null) {
            throw new IllegalStateException("The skill flag was set, but a skill is not defined for the specified item ({item.Definition.Number}, {item.Definition.Group})");
        }

        item.hasSkill = true;
    }

    private static void readLuckOption(byte optionByte, Item item) {
        if ((optionByte & LUCK_FLAG) == 0) {
            return;
        }
        IncreasableItemOption luckOption = item.getItemDefinition().PossibleItemOptions.stream()
                .flatMap(e -> e.PossibleOptions.stream().filter(i -> i.OptionType == ItemOptionTypes.Luck))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("The luck flag was set, but luck option is not defined as possible option in the item definition %s ({item.Definition.Number}, {item.Definition.Group}).", item)));
        ItemOptionLink optionLink = new ItemOptionLink();
        optionLink.ItemOption = luckOption;
        item.ItemOptions.add(optionLink);
    }

    private static void readWingOption(byte wingbyte, Item item, ItemOptionDefinition wingOptionDefinition) {
        int wingBits = wingbyte & 0x0F;
        for (IncreasableItemOption wingOption : wingOptionDefinition.PossibleOptions) {
            byte optionMask = (byte) (1 << (wingOption.Number - 1));
            if ((wingBits & optionMask) == optionMask) {
                ItemOptionLink optionLink = new ItemOptionLink();
                optionLink.ItemOption = wingOption;
                item.ItemOptions.add(optionLink);
            }
        }

    }

    private static void readExcellentOption(byte excByte, Item item, ItemOptionDefinition excellentOD) {
        int excellentBits = excByte & 0x3F;
        for (IncreasableItemOption excellentOption : excellentOD.PossibleOptions) {
            byte optionMask = (byte) (1 << (excellentOption.Number - 1));
            if ((excellentBits & optionMask) == optionMask) {
                ItemOptionLink optionLink = new ItemOptionLink();
                optionLink.ItemOption = excellentOption;
                item.ItemOptions.add(optionLink);
            }
        }

    }

    private static void readNormalOption(byte[] array, Item item) {
        int optionLevel = (array[1] & 3) + ((array[3] >> 4) & 4);
        if (optionLevel == 0) {
            return;
        }
        boolean itemIsWing = item.getItemDefinition().PossibleItemOptions.stream()
                .anyMatch(e -> e.PossibleOptions.stream().anyMatch(i -> i.OptionType == ItemOptionTypes.Wing));
        int optionNumber = itemIsWing ? (array[3] >> 4) & 0b11 : 0;
        IncreasableItemOption option = item.getItemDefinition().PossibleItemOptions.stream()
                .flatMap(e -> e.PossibleOptions.stream().filter(i -> i.OptionType == ItemOptionTypes.Option && i.Number == optionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The option with level {optionLevel} and number {optionNumber} is not defined as possible option in the item definition ({item.Definition.Number}, {item.Definition.Group})."));
        ItemOptionLink optionLink = new ItemOptionLink();
        optionLink.ItemOption = option;
        optionLink.Level = optionLevel;
        item.ItemOptions.add(optionLink);
    }

    private static void readAncientOption(byte ancientByte, Item item) {
        if ((ancientByte & AncientMask) == 0) {
            return;
        }

        int bonusLevel = (ancientByte & ANCIENT_BONUS_LEVEL_MASK) >> 2;
        int setDiscriminator = ancientByte & ANCIENT_DISCRIMINATOR_MASK;
        List<ItemSetGroup> itemSetGroups = item.getItemDefinition().PossibleItemSetGroups.stream()
                .filter(set -> set.AncientSetDiscriminator == setDiscriminator && set.Options.stream().anyMatch(o -> o.OptionType == ItemOptionTypes.AncientBonus))
                .collect(toList());

        if (itemSetGroups.size() > 1) {
            throw new IllegalStateException("Ambigious ancient set discriminator: {ancientSets.Count} sets with discriminator {setDiscriminator} found for item definition ({item.Definition.Number}, {item.Definition.Group}).");
        } else if (itemSetGroups.isEmpty()) {
            throw new IllegalStateException("Couldn't find ancient set (discriminator {setDiscriminator}) for item ({item.Definition.Number}, {item.Definition.Group}).");
        }

        ItemSetGroup ancientSet = itemSetGroups.get(0);

        item.ItemSetGroups.add(ancientSet);
        IncreasableItemOption ancientBonus = ancientSet.Options.stream().filter(o -> o.OptionType == ItemOptionTypes.AncientBonus).findFirst().get();
        ItemOptionLink optionLink = new ItemOptionLink();
        optionLink.ItemOption = ancientBonus;
        optionLink.Level = bonusLevel;
    }

    private static void readLevel380Option(byte option380Byte, Item item) {
        if ((option380Byte & GUARDIAN_OPTION_FLAG) == 0) {
            return;
        }

        if (!item.getItemDefinition().PossibleItemOptions.stream()
                .anyMatch(e -> e.PossibleOptions.stream().anyMatch(i -> i.OptionType == ItemOptionTypes.GuardianOption))) {
            throw new IllegalStateException("The lvl380 option flag was set, but the option is not defined as possible option in the item definition ({item.Definition.Number}, {item.Definition.Group}).");
        }

        item.getItemDefinition().PossibleItemOptions.stream()
                .flatMap(e -> e.PossibleOptions.stream().filter(i -> i.OptionType == ItemOptionTypes.GuardianOption))
                .forEach(e -> {
                    ItemOptionLink optionLink = new ItemOptionLink();
                    optionLink.ItemOption = e;
                    item.ItemOptions.add(optionLink);
                });

    }

    private static void readSockets(byte[] socketBytes, int startIndex, Item item) {
        int maximumSockets = item.getItemDefinition().MaximumSockets;
        if (maximumSockets == 0) {
            return;
        }

        int numberOfSockets = 0;
        for (int i = startIndex; i < startIndex + maximumSockets; i++) {
            int socketByte = socketBytes[i];
            if (socketByte == NO_SOCKET) {
                continue;
            }
            numberOfSockets++;
            IncreasableItemOption socketOption = item.getItemDefinition().PossibleItemOptions.stream()
                    .flatMap(e -> e.PossibleOptions.stream().filter(o -> o.OptionType == ItemOptionTypes.SocketOption && o.Number == socketByte))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The socket option {socketByte} was set, but the option is not defined as possible option in the item definition ({item.Definition.Number}, {item.Definition.Group})."));

            ItemOptionLink optionLink = new ItemOptionLink();
            optionLink.ItemOption = socketOption;
            item.ItemOptions.add(optionLink);
        }

        item.SocketCount = numberOfSockets;
    }

    private static void readHarmonyOption(byte harmonyByte, Item item) {
        if (harmonyByte == 0) {
            return;
        }

        int level = harmonyByte & 0x0F;
        int optionNumber = (harmonyByte & 0xF0) >> 4;
        IncreasableItemOption harmonyOption = item.getItemDefinition().PossibleItemOptions.stream()
                .flatMap(e -> e.PossibleOptions.stream().filter(i -> i.OptionType == ItemOptionTypes.HarmonyOption && i.Number == optionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The harmony option {optionNumber} was set, but the option is not defined as possible option in the item definition ({item.Definition.Number}, {item.Definition.Group})."));
        ItemOptionLink optionLink = new ItemOptionLink();
        optionLink.ItemOption = harmonyOption;
        optionLink.Level = level;
        item.ItemOptions.add(optionLink);
    }

    private static void readFenrirOptions(byte fenrirByte, Item item) {
        if (fenrirByte == 0) {
            return;
        }
        addFenrirOptionIfFlagSet(fenrirByte, BLACK_FENRIR_FLAG, ItemOptionTypes.BlackFenrir, item);
        addFenrirOptionIfFlagSet(fenrirByte, BLUE_FENRIR_FLAG, ItemOptionTypes.BlueFenrir, item);
        addFenrirOptionIfFlagSet(fenrirByte, GOLD_FENRIR_FLAG, ItemOptionTypes.GoldFenrir, item);
    }

    private static void addFenrirOptionIfFlagSet(int fenrirByte, int fenrirFlag, ItemOptionType fenrirOptionType, Item item) {
        boolean isFlagSet = (fenrirByte & fenrirFlag) == fenrirFlag;
        if (!isFlagSet) {
            return;
        }
        ItemOptionDefinition blackOption = item.getItemDefinition().PossibleItemOptions.stream()
                .filter(e -> e.PossibleOptions.stream().anyMatch(i -> i.OptionType == fenrirOptionType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The fenrir flag {fenrirFlag} in {fenrirByte} was set, but the option is not defined as possible option in the item definition ({item.Definition.Number}, {item.Definition.Group})."));

        ItemOptionLink optionLink = new ItemOptionLink();
        optionLink.ItemOption = blackOption.PossibleOptions.get(0);
        item.ItemOptions.add(optionLink);
    }

    private static byte getFenrirByte(Item item) {
        byte result = 0;
        if (item.ItemOptions.stream().anyMatch(o -> o.ItemOption.OptionType == ItemOptionTypes.BlackFenrir)) {
            result |= BLACK_FENRIR_FLAG;
        }

        if (item.ItemOptions.stream().anyMatch(o -> o.ItemOption.OptionType == ItemOptionTypes.BlueFenrir)) {
            result |= BLUE_FENRIR_FLAG;
        }

        if (item.ItemOptions.stream().anyMatch(o -> o.ItemOption.OptionType == ItemOptionTypes.GoldFenrir)) {
            result |= GOLD_FENRIR_FLAG;
        }

        return result;
    }

    private static void setSocketBytes(byte[] target, int startIndex, Item item) {
        for (int i = startIndex; i < MAXIMUM_SOCKETS; i++) {
            target[i] = (byte) (i < item.SocketCount ? EMPTY_SOCKET : NO_SOCKET);
        }

        List<Integer> socketOptions = item.ItemOptions.stream()
                .filter(e -> e.ItemOption.OptionType == ItemOptionTypes.SocketOption)
                .map(e -> e.ItemOption.Number)
                .collect(toList());
        int j = startIndex;
        for (Integer socketOption : socketOptions) {
            target[j++] = socketOption.byteValue();
        }
    }

    private static byte getHarmonyByte(Item item) {
        byte result = 0;
        Optional<ItemOptionLink> first = item.ItemOptions.stream()
                .filter(o -> o.ItemOption.OptionType == ItemOptionTypes.HarmonyOption)
                .findFirst();
        if (first.isPresent()) {
            ItemOptionLink harmonyOption = first.get();
            result = (byte) (harmonyOption.ItemOption.Number << 4);
            result |= (byte) harmonyOption.Level;
        }

        return result;
    }

    private static byte getExcellentByte(Item item) {
        byte result = 0;
        List<ItemOptionLink> collect = item.ItemOptions.stream()
                .filter(o -> o.ItemOption.OptionType == ItemOptionTypes.Excellent || o.ItemOption.OptionType == ItemOptionTypes.Wing)
                .collect(toList());
        for (ItemOptionLink itemOptionLink : collect) {
            result |= (byte) (1 << (itemOptionLink.ItemOption.Number - 1));
        }
        return result;
    }

}
