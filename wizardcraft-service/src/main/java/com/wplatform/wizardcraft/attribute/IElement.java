package com.wplatform.wizardcraft.attribute;// <copyright file="IElement.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import java.beans.PropertyChangeListener;

/// <summary>
/// The interface of an element.
/// </summary>
public interface IElement {
    /// <summary>
    /// Occurs when the value has been changed.
    /// </summary>
    //event EventHandler ValueChanged;

    void addPropertyChangeListener(PropertyChangeListener listener);

    /// <summary>
    /// Gets the value.
    /// </summary>
    float getValue();

    /// <summary>
    /// Gets the type of the aggregate.
    /// </summary>
    AggregateType getAggregateType();
}

