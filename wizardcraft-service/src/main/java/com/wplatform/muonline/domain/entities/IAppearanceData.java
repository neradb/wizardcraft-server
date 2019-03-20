package com.wplatform.muonline.domain.entities;// <copyright file="IAppearanceData.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import com.wplatform.muonline.domain.configuration.CharacterClass;

/// <summary>
/// The interface for the appearance data.
/// </summary>
public class IAppearanceData {
    /// <summary>
    /// Occurs when the appearance of the player changed.
    /// </summary>
    //event EventHandler AppearanceChanged;

    /// <summary>
    /// Gets the character class.
    /// </summary>
    CharacterClass characterClass;

    /// <summary>
    /// Gets the current pose.
    /// </summary>
    Character.CharacterPose pose;

    /// <summary>
    /// Gets a value indicating whether a full ancient set is equipped.
    /// </summary>
    boolean fullAncientSetEquipped;

    /// <summary>
    /// Gets the equipped items.
    /// </summary>
    //Iterator<ItemAppearance> EquippedItems;
}

