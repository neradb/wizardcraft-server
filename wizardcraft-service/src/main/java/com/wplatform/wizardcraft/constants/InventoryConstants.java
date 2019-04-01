package com.wplatform.wizardcraft.constants;

import com.wplatform.wizardcraft.domain.Characters;

public class InventoryConstants {


    /// <summary>
    /// The first equippable item slot index.
    /// </summary>
    public static final byte FirstEquippableItemSlotIndex = 0;

    /// <summary>
    /// The last equippable item slot index.
    /// </summary>
    public static final byte LastEquippableItemSlotIndex = 11;

    /// <summary>
    /// The equippable slots count.
    /// </summary>
    public static final byte EquippableSlotsCount = (byte) (LastEquippableItemSlotIndex - FirstEquippableItemSlotIndex + 1);

    /// <summary>
    /// The left hand inventory slot index.
    /// </summary>
    public static final byte LeftHandSlot = 0;

    /// <summary>
    /// The right hand inventory slot index.
    /// </summary>
    public static final byte RightHandSlot = 1;

    /// <summary>
    /// The helm inventory slot index.
    /// </summary>
    public static final byte HelmSlot = 2;

    /// <summary>
    /// The armor inventory slot index.
    /// </summary>
    public static final byte ArmorSlot = 3;

    /// <summary>
    /// The pants inventory slot index.
    /// </summary>
    public static final byte PantsSlot = 4;

    /// <summary>
    /// The gloves inventory slot index.
    /// </summary>
    public static final byte GlovesSlot = 5;

    /// <summary>
    /// The boots inventory slot index.
    /// </summary>
    public static final byte BootsSlot = 6;

    /// <summary>
    /// The wings inventory slot index.
    /// </summary>
    public static final byte WingsSlot = 7;

    /// <summary>
    /// The pet inventory slot index.
    /// </summary>
    public static final byte PetSlot = 8;

    /// <summary>
    /// The size of a row.
    /// </summary>
    public static final int RowSize = 8;

    /// <summary>
    /// Number of rows in the inventory.
    /// </summary>
    public static final byte InventoryRows = 8;

    /// <summary>
    /// The maximum number of extensions.
    /// </summary>
    public static final byte MaximumNumberOfExtensions = 4;

    /// <summary>
    /// The number of rows of one extension.
    /// </summary>
    public static final byte RowsOfOneExtension = 4;

    /// <summary>
    /// Number of rows of the inventory extension.
    /// </summary>
    public static final byte AllInventoryExtensionRows = (byte) (MaximumNumberOfExtensions * RowsOfOneExtension);

    /// <summary>
    /// Index of the first personal store slot.
    /// 12 = number of wearable item slots
    /// 64 = number of inventory slots
    /// 128 = number of extended inventory slots (64 are hidden in game, S6E3)
    /// </summary>
    public static final byte FirstStoreItemSlotIndex = (byte) (
            EquippableSlotsCount + // 12
                    (InventoryRows * RowSize) + // 64
                    (AllInventoryExtensionRows * RowSize)); // 128

    /// <summary>
    /// The number of personal store rows.
    /// </summary>
    public static final byte StoreRows = 4;

    /// <summary>
    /// The size of the personal store.
    /// </summary>
    public static final byte StoreSize = (byte) (StoreRows * RowSize);

    /// <summary>
    /// The number of temporary common rows.
    /// </summary>
    public static final byte TemporaryStorageRows = 4;

    /// <summary>
    /// The number of temporary common slots.
    /// </summary>
    public static final byte TemporaryStorageSize = (byte) (TemporaryStorageRows * RowSize);

    /// <summary>
    /// The number of rows in the warehouse (vault).
    /// </summary>
    public static final byte WarehouseRows = 15;

    /// <summary>
    /// The number of warehouse item slots.
    /// </summary>
    public static final byte WarehouseSize = (byte) (WarehouseRows * RowSize);

    /// <summary>
    /// Gets the size of the inventory of the specified player.
    /// </summary>
    /// <param name="player">The player.</param>
    /// <returns>The size of the inventory.</returns>
    public static byte GetInventorySize(Characters selectedCharacter) {
        int size = EquippableSlotsCount +
                (InventoryRows * RowSize) +
                (RowsOfOneExtension * Math.min(selectedCharacter.getInventoryExtensions(), MaximumNumberOfExtensions));

        return (byte) size;
    }
}
