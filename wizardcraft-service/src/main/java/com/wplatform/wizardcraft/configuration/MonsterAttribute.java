package com.wplatform.wizardcraft.configuration;

/// <summary>
/// The attribute and value of a monster.
/// </summary>
/// <remarks>
/// Just needed for entity framework, because it does not support the mapping of dictionaries. May be removed in the future.
/// </remarks>
public class MonsterAttribute {
    /// <summary>
    /// Gets or sets the attribute definition.
    /// </summary>
    public org.hibernate.persister.walking.spi.AttributeDefinition AttributeDefinition;

    /// <summary>
    /// Gets or sets the value.
    /// </summary>
    public float Value;
}

