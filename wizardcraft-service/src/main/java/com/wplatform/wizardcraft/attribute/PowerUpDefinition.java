package com.wplatform.wizardcraft.attribute;

import org.hibernate.persister.walking.spi.AttributeDefinition;

/// <summary>
/// The power up definition which describes the boost of an target attribute.
/// </summary>
public class PowerUpDefinition {
    /// <summary>
    /// Gets or sets the target attribute.
    /// </summary>
    public AttributeDefinition TargetAttribute;

    /// <summary>
    /// Gets or sets the boost.
    /// </summary>
    public PowerUpDefinitionValue Boost;


}

