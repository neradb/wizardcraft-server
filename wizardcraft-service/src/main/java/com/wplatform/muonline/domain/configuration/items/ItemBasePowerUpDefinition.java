package com.wplatform.muonline.domain.configuration.items;

import org.hibernate.persister.walking.spi.AttributeDefinition;

/// <summary>
/// Defines an item base power up definition.
/// </summary>
public class ItemBasePowerUpDefinition {
    /// <summary>
    /// Gets or sets the target attribute.
    /// </summary>
    public AttributeDefinition TargetAttribute;

    /// <summary>
    /// Gets or sets the base value.
    /// </summary>
    //public ConstantElement BaseValueElement;

    /// <summary>
    /// Gets or sets the bonus per level.
    /// </summary>
    //public ICollection<LevelBonus> BonusPerLevel;

    /// <summary>
    /// Gets or sets the additional value to the base value.
    /// </summary>
    public float BaseValue;
}

