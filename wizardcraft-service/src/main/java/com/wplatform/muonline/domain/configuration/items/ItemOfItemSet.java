package com.wplatform.muonline.domain.configuration.items;

/// <summary>
/// Defines additional bonus options for this item of a set.
/// </summary>
/// <remarks>
/// Here we can define additional bonus options, like the ancient options (e.g. +5 / +10 Str etc.).
/// </remarks>
public class ItemOfItemSet {
    /// <summary>
    /// Gets or sets the item's definition for which the bonus should apply.
    /// </summary>
    public ItemDefinition ItemDefinition;

    /// <summary>
    /// Gets or sets the bonus option.
    /// </summary>
    public IncreasableItemOption BonusOption;
}
