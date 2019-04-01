package com.wplatform.wizardcraft.configuration;

/// <summary>
/// The calculation strategy for maximum fruit points.
/// </summary>
public enum FruitCalculationStrategy {
    /// <summary>
    /// The default strategy (maximum 127).
    /// </summary>
    Default,

    /// <summary>
    /// The strategy to calculate the fruits for magic gladiator classes (maximum 100).
    /// </summary>
    MagicGladiator,

    /// <summary>
    /// The strategy to calculate the fruits for dark lord classes (maximum 115).
    /// </summary>
    DarkLord,
}
