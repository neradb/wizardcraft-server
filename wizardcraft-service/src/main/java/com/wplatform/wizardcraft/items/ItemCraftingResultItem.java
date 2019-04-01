package com.wplatform.wizardcraft.items;

/// <summary>
/// Defines the resulting item of a crafting.
/// TODO: Some properties are not used yet.
/// </summary>
public class ItemCraftingResultItem {
    /// <summary>
    /// Gets or sets the item definition.
    /// </summary>
    public ItemDefinition ItemDefinition;

    /// <summary>
    /// Gets or sets the random minimum level.
    /// </summary>
    public byte RandLvlMin;

    /// <summary>
    /// Gets or sets the random maximum level.
    /// </summary>
    public byte RandLvlMax;

    // public byte Level ;

    /// <summary>
    /// Gets or sets the reference identifier.
    /// </summary>
    /// <remarks>
    /// For Item Upping.
    /// </remarks>
    public byte RefID;

    /// <summary>
    /// Gets or sets the add level.
    /// </summary>
    /// <remarks>
    /// For Item Upping.
    /// </remarks>
    public byte AddLevel;
}

