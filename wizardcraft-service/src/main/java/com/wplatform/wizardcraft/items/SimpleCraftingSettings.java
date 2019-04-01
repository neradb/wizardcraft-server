package com.wplatform.wizardcraft.items;

import java.util.List;

/// <summary>
/// Crafting settings for the simple item crafting handler.
/// </summary>
public class SimpleCraftingSettings {
    /// <summary>
    /// Gets or sets the price to do the crafting.
    /// </summary>
    public int Money;

    /// <summary>
    /// Gets or sets the success percent.
    /// </summary>
    public byte SuccessPercent;

    /// <summary>
    /// Gets or sets a value indicating whether multiple crafting at the same time are allowed for this crafting.
    /// </summary>
    public boolean MultipleAllowed;

    /// <summary>
    /// Gets or sets the required items.
    /// </summary>
    public List<ItemCraftingRequiredItem> RequiredItems;

    /// <summary>
    /// Gets or sets the result items, which are generated when the crafting succeeded.
    /// </summary>
    public List<ItemCraftingResultItem> ResultItems;

    /// <summary>
    /// Gets or sets the result item selection.
    /// </summary>
    public ResultItemSelection ResultItemSelect;

    /// <summary>
    /// Gets or sets the luck option chance.
    /// </summary>
    public byte LuckOptionChance;

    /// <summary>
    /// Gets or sets the skill option chance.
    /// </summary>
    public byte SkillOptionChance;

    /// <summary>
    /// Gets or sets the excellent option chance.
    /// </summary>
    public byte ExcOptionChance;

    /// <summary>
    /// Gets or sets the maximum excellent options.
    /// </summary>
    public byte MaxExcOptions;
}

