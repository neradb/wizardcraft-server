package com.wplatform.wizardcraft.configuration;

import com.wplatform.wizardcraft.items.ItemDefinition;

import java.util.List;

/// <summary>
/// Idea: append several "drop item groups" with its certain probability.
/// In the drop generator sort all DropItemGroups by its chance.
/// Classes which can have DropItemGroups: Maps, Monsters(for example the kundun drops), Players(for quest items)
/// The game server configuration also got the basic drop groups (for example for random items and jewels).
/// </summary>
public class DropItemGroup {
    /// <summary>
    /// Gets or sets the chance of the item drop group to apply. From 0.0 to 1.0.
    /// </summary>
    public double Chance;

    /// <summary>
    /// Gets or sets the special type of the item.
    /// </summary>
    public SpecialItemType ItemType;

    /// <summary>
    /// Gets or sets the possible items which can be dropped.
    /// </summary>
    public List<ItemDefinition> PossibleItems;


    /// <summary>
    /// Enumeration of special item types.
    /// </summary>
    public static enum SpecialItemType {
        /// <summary>
        /// No special item type.
        /// </summary>
        None,

        /// <summary>
        /// The ancient special item type.
        /// </summary>
        Ancient,

        /// <summary>
        /// The excellent special item type.
        /// </summary>
        Excellent,

        /// <summary>
        /// The random item special item type.
        /// </summary>
        RandomItem,

        /// <summary>
        /// The socket item special item type.
        /// </summary>
        SocketItem,

        /// <summary>
        /// The money special item type.
        /// </summary>
        Money
    }

}

