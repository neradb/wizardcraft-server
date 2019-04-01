package com.wplatform.wizardcraft.attribute;// <copyright file="BaseStatAttribute.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

/// <summary>
/// An attribute which represents an increasable stat attribute (e.g. by level-up points).
/// </summary>
/// <remarks>
/// Intermediate class, needed because we want to add a setter.
/// We do just override the getter here and have to introduce a new Value get/set-property on a derived type.
/// </remarks>
public abstract class BaseStatAttribute extends BaseAttribute {
    /// <summary>
    /// Initializes a new instance of the <see cref="BaseStatAttribute"/> class.
    /// </summary>
    /// <param name="definition">The definition.</param>
    /// <param name="aggregateType">Type of the aggregate.</param>
    protected BaseStatAttribute(AttributeDefinition definition, AggregateType aggregateType) {
        super(definition, aggregateType);
    }



    /// <inheritdoc/>
    public float getValue() {
        return this.getValueGetter();
    }




    /// <summary>
    /// Gets the value.
    /// </summary>
    protected abstract float getValueGetter();
}

