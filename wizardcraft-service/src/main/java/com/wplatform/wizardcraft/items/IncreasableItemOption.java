package com.wplatform.wizardcraft.items;// <copyright file="IncreasableItemOption.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import lombok.Getter;
import lombok.Setter;

import java.util.List;


/// <summary>
/// Defines an item option which can be increased.
/// </summary>
@Setter
@Getter
public class IncreasableItemOption extends ItemOption {
    /// <summary>
    /// Gets or sets a value which defines by which "level" the option is increased with <see cref="LevelDependentOptions"/>.
    /// </summary>
    /// <value>
    /// The type of the level.
    /// </value>
    public LevelType LevelType;

    /// <summary>
    /// Gets or sets the level dependent options for option levels over 1.
    /// </summary>
    public List<ItemOptionOfLevel> LevelDependentOptions;


    /// <summary>
    /// Defines by which "level" the option is increased with <see cref="IncreasableItemOption.LevelDependentOptions"/>.
    /// </summary>
    public enum LevelType {
        /// <summary>
        /// It's increased by the option level.
        /// </summary>
        /// <remarks>
        /// This one is used by item options which can be increased by separate jewels, e.g. Jewel of Life or Jewel of Harmony.
        /// </remarks>
        OptionLevel,

        /// <summary>
        /// It's increased by the level of the item which has the option.
        /// </summary>
        /// <remarks>
        /// As far as I know, this is only required for wing options, e.g. 'Increase max HP +50~115'. That's why <see cref="OptionLevel"/> is the default, too.
        /// </remarks>
        ItemLevel,
    }
}

