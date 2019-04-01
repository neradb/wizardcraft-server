package com.wplatform.wizardcraft.items;

import com.wplatform.wizardcraft.attribute.PowerUpDefinition;

/// <summary>
/// Defines the option of an item.
/// </summary>
public class ItemOption {
    /// <summary>
    /// Gets or sets the number.
    /// </summary>
    /// <remarks>
    /// This number in combination with the option type is a reference for the client.
    /// </remarks>
    public int Number;

    /// <summary>
    /// Gets or sets the type of the option.
    /// </summary>
    public ItemOptionType OptionType;

    /// <summary>
    /// Gets or sets the power up definition which should apply when this item is carried.
    /// </summary>
    public PowerUpDefinition PowerUpDefinition;
}

