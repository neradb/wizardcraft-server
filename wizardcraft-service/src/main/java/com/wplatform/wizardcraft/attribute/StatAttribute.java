package com.wplatform.wizardcraft.attribute;

/// <summary>
/// An attribute which represents an increasable stat attribute (e.g. by level-up points).
/// </summary>
public class StatAttribute extends BaseStatAttribute {
    private float statValue;

    /// <summary>
    /// Initializes a new instance of the <see cref="StatAttribute"/> class.
    /// </summary>
    public StatAttribute() {
        super(null, AggregateType.AddRaw);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="StatAttribute"/> class.
    /// </summary>
    /// <param name="definition">The definition.</param>
    /// <param name="baseValue">The base value.</param>
    public StatAttribute(AttributeDefinition definition, float baseValue) {
        super(definition, AggregateType.AddRaw);
        this.statValue = baseValue;
    }


    @Override
    public float getValue() {
        return statValue;
    }


    public void setValue(float value) {
        if (Math.abs(this.statValue - value) > 0.01f) {
            this.statValue = value;
            this.support.firePropertyChange("statValue", statValue, value);

        }
        statValue = value;
    }


    /// <inheritdoc/>
    protected float getValueGetter() {
        return this.statValue;
    }
}

