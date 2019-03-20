package com.wplatform.muonline.domain.entities;

import java.util.List;

/// <summary>
/// The appearance data of an character.
/// </summary>
public class AppearanceData extends IAppearanceData {
    /// <summary>
    /// Occurs when the appearance of the player changed.
    /// </summary>
    /// <remarks>
    /// This never happens in this implementation.
    /// </remarks>
    //public EventHandler AppearanceChanged;

    /// <summary>
    /// Gets or sets the character class.
    /// </summary>
    public com.wplatform.muonline.domain.configuration.CharacterClass CharacterClass;

    /// <inheritdoc />
    public Character.CharacterPose Pose;

    /// <inheritdoc />
    public boolean FullAncientSetEquipped;

    /// <summary>
    /// Gets or sets the equipped items.
    /// </summary>
    public List<ItemAppearance> EquippedItems;

    /// <inheritdoc />
    //IEnumerable<ItemAppearance> IAppearanceData.EquippedItems => this.EquippedItems;
}

