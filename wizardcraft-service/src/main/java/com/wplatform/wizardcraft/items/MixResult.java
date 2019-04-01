package com.wplatform.wizardcraft.items;

/// <summary>
/// Defines what should happen with the required input items when the item crafting finished.
/// </summary>
public enum MixResult {
    /// <summary>
    /// The item will disappear.
    /// </summary>
    Disappear,

    /// <summary>
    /// The item will stay as is.
    /// </summary>
    StaysAsIs,

    /// <summary>
    /// The item will be downgraded to level 0.
    /// </summary>
    DowngradedTo0,

    /// <summary>
    /// The item will be downgraded to a random level.
    /// </summary>
    DowngradedRandom
}

