package com.wplatform.muonline.domain.configuration.items;// <copyright file="AttributeRequirement.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import org.hibernate.persister.walking.spi.AttributeDefinition;

/// <summary>
/// Defines a requirement of an attribute with the specified value.
/// </summary>
public class AttributeRequirement {
    /// <summary>
    /// Gets or sets the attribute which is required.
    /// </summary>
    public AttributeDefinition Attribute;

    /// <summary>
    /// Gets or sets the minimum value the attribute needs to have.
    /// </summary>
    public int MinimumValue;
}

