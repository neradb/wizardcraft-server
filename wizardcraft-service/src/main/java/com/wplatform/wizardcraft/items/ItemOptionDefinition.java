package com.wplatform.wizardcraft.items;

import java.util.List;

/// <summary>
/// The definition of an item option.
/// </summary>
public class ItemOptionDefinition {
    /// <summary>
    /// Gets or sets the name of the option, for example "Luck", "Skill", "NORMAL Option"
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets a value indicating whether this option adds randomly.
    /// </summary>
    public boolean AddsRandomly;

    /// <summary>
    /// Gets or sets the add chance if this option adds randomly.
    /// </summary>
    public float AddChance;

    /// <summary>
    /// Gets or sets the maximum options per item when it adds randomly by drop.
    /// </summary>
    /// <remarks>
    /// Usually this is 1. But for some options (e.g. excellent) this can be bigger than 1.
    /// </remarks>
    public int MaximumOptionsPerItem;


    /// <summary>
    /// Gets or sets the possible options.
    /// </summary>
    public List<IncreasableItemOption> PossibleOptions;
}

