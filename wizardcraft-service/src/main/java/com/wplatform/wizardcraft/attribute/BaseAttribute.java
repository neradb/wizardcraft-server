package com.wplatform.wizardcraft.attribute;// <copyright file="BaseAttribute.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/// <summary>
/// The base class for an attribute.
/// </summary>
public abstract class BaseAttribute implements IAttribute {

    @Getter
    @Setter
    private AttributeDefinition definition;
    private AggregateType aggregateType;

    /// <summary>
    /// Initializes a new instance of the <see cref="BaseAttribute"/> class.
    /// </summary>
    /// <param name="definition">The definition.</param>
    /// <param name="aggregateType">Type of the aggregate.</param>
    protected BaseAttribute(AttributeDefinition definition, com.wplatform.wizardcraft.attribute.AggregateType aggregateType) {
        this.definition = definition;
        this.aggregateType = aggregateType;
    }

    /// <inheritdoc/>
    public PropertyChangeSupport support = new PropertyChangeSupport(this);


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    @Override
    public AggregateType getAggregateType() {
        return null;
    }


}

