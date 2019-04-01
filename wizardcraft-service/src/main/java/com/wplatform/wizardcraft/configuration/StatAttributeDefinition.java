package com.wplatform.wizardcraft.configuration;

import org.hibernate.persister.walking.spi.AttributeDefinition;

/// <summary>
/// Defines a stat attribute, which may be increasable by the player.
/// </summary>
public class StatAttributeDefinition {


    private AttributeDefinition attribute;

    /// <summary>
    /// Initializes a new instance of the <see cref="StatAttributeDefinition"/> class.
    /// </summary>
    public StatAttributeDefinition() {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="StatAttributeDefinition" /> class.
    /// </summary>
    /// <param name="attribute">The attribute.</param>
    /// <param name="baseValue">The base value.</param>
    /// <param name="increasableByPlayer">if set to <c>true</c> it is increasable by the player.</param>
    public StatAttributeDefinition(AttributeDefinition attribute, float baseValue, boolean increasableByPlayer) {
        this.attribute = attribute;
        //this.BaseValue = baseValue;
        //this.IncreasableByPlayer = increasableByPlayer;
    }


     //public  AttributeDefinition attribute;



    /// <summary>
    /// Gets or sets the base value, which is the initial value without an increase of the player.
    /// </summary>
    public float baseValue;



    /// <summary>
    /// Gets or sets a value indicating whether this stat is increasable by the player in any way.
    /// </summary>
    public boolean increasableByPlayer;

}

