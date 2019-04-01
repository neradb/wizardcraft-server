package com.wplatform.wizardcraft.items;

import java.util.List;

/// <summary>
/// Describes an required item for a crafting.
/// TODO: Some properties are not used yet.
/// </summary>
public class ItemCraftingRequiredItem {
    /// <summary>
    /// Gets or sets the item definition.
    /// </summary>
    public ItemDefinition ItemDefinition;

    /// <summary>
    /// Gets or sets the minimum level.
    /// </summary>
    public byte MinLvl;

    /// <summary>
    /// Gets or sets the required item options.
    /// </summary>
    public List<ItemOptionType> RequiredItemOptions;

    /// <summary>
    /// Gets or sets the minimum amount.
    /// </summary>
    public byte MinAmount;

    /// <summary>
    /// Gets or sets the success result.
    /// </summary>
    public MixResult SuccessResult;

    /// <summary>
    /// Gets or sets the fail result.
    /// </summary>
    public MixResult FailResult;

    /// <summary>
    /// Gets or sets the NPC price divisor. For each full division, the percentage gets increased by 1 percent, and the mix price rises.
    /// </summary>
    public int NpcPriceDivisor;

    /// <summary>
    /// Gets or sets the add percentage per division.
    /// </summary>
    /// <value>
    /// The add percentage.
    /// </value>
    public byte AddPercentage;

    /// <summary>
    /// Gets or sets the reference identifier.
    /// </summary>
    public byte RefID;
}

