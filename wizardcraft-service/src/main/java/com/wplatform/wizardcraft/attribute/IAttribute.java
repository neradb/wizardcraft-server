package com.wplatform.wizardcraft.attribute;// <copyright file="IAttribute.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


public interface IAttribute extends IElement {
    /// <summary>
    /// Gets the attribute definition.
    /// </summary>
    AttributeDefinition getDefinition();
}