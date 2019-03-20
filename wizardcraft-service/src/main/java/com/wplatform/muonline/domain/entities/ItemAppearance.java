package com.wplatform.muonline.domain.entities;

import com.wplatform.muonline.domain.configuration.items.ItemDefinition;
import com.wplatform.muonline.domain.configuration.items.ItemOptionType;

import java.util.List;

/// <summary>
/// Appearance of an item.
/// </summary>
public class ItemAppearance {
    /// <summary>
    /// Gets or sets the item slot.
    /// </summary>
    public byte ItemSlot;

    /// <summary>
    /// Gets or sets the definition of the item.
    /// </summary>
    public ItemDefinition Definition;

    /// <summary>
    /// Gets or sets the level.
    /// </summary>
    public byte Level;

    /// <summary>
    /// Gets or sets the visible options.
    /// </summary>
    public List<ItemOptionType> VisibleOptions;
}

