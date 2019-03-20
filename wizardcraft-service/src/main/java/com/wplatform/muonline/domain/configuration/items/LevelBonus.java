package com.wplatform.muonline.domain.configuration.items;

/// <summary>
/// Defines a constant bonus, depending on item level.
/// </summary>
public class LevelBonus {
    /// <summary>
    /// Initializes a new instance of the <see cref="LevelBonus"/> class.
    /// </summary>
    public LevelBonus() {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="LevelBonus"/> class.
    /// </summary>
    /// <param name="level">The level.</param>
    /// <param name="constantValue">The constant value.</param>
    public LevelBonus(int level, float constantValue) {
        this.Level = level;
        this.AdditionalValue = constantValue;
    }

    /// <summary>
    /// Gets or sets the level of the item.
    /// </summary>
    public int Level;

    /// <summary>
    /// Gets or sets the additional value element to the base value.
    /// </summary>
    //public ConstantElement AdditionalValueElement;

    /// <summary>
    /// Gets or sets the additional value to the base value.
    /// </summary>
    public float AdditionalValue;
}
