package com.wplatform.wizardcraft.items;// <copyright file="ItemOptionOfLevel.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import com.wplatform.wizardcraft.attribute.PowerUpDefinition;

/// <summary>
/// The item option, depending on the specified item level.
/// </summary>
public class ItemOptionOfLevel {
    /// <summary>
    /// Gets or sets the level.
    /// </summary>
    public int Level;

    /// <summary>
    /// Gets or sets the required item level.
    /// </summary>
    public int RequiredItemLevel;

    /// <summary>
    /// Gets or sets the power up definition.
    /// </summary>
    public PowerUpDefinition PowerUpDefinition;
}

