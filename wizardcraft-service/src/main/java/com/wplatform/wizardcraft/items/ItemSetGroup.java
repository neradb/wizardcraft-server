package com.wplatform.wizardcraft.items;// <copyright file="ItemSetGroup.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import java.util.List;

/// <summary>
/// Defines an item set group. With (partial) completion of the set, addional options are getting applied.
/// </summary>
/// <remarks>
/// With this we can define a lot of things, for example:
///   - double wear bonus of single swords
///   - set bonus for defense rate
///   - set bonus for defense, if level is greater than 9
///   - ancient sets
/// </remarks>
public class ItemSetGroup {
    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    /// <value>
    /// The name.
    /// </value>
    public String Name;

    /// <summary>
    /// Gets or sets a value indicating whether all of the options of this item set always apply.
    /// If not, the minimum item count and the minimum set levels are respected.
    /// </summary>
    public boolean AlwaysApplies;

    /// <summary>
    /// Gets or sets a value indicating whether the items are counted distincly.
    /// </summary>
    /// <remarks>
    /// For example, for the double wear bonus this has to be non-distinct, else we wouldn't get the bonus for wearing two of the same kind of swords.
    /// </remarks>
    public boolean CountDistinct;

    /// <summary>
    /// Gets or sets the minimum item count which is needed to get the bonus.
    /// </summary>
    public int MinimumItemCount;

    /// <summary>
    /// Gets or sets the minimum level which all of the items of the set need to have to get the bonus.
    /// </summary>
    public int SetLevel;

    /// <summary>
    /// Gets or sets the ancient set discriminator.
    /// </summary>
    /// <remarks>
    /// Only relevant to ancient sets. One item can only be in one ancient set with the same discriminator.
    /// The original mu online protocol supports up to two different ancient sets per item - with discriminator values 1 and 2.
    /// E.g. a 'Warrior Leather' set would have a discriminator value of 1, the 'Anonymous Leather' set would have 2.
    /// </remarks>
    public int AncientSetDiscriminator;

    /// <summary>
    /// Gets or sets the options. If the options depend on the item count, this options need to be ordered correctly.
    /// </summary>
    /// <remarks>
    /// The order is defined by <see cref="ItemOption.Number"/>.
    /// </remarks>
    public List<IncreasableItemOption> Options;

    /// <summary>
    /// Gets or sets the items of this set.
    /// </summary>
    /// <remarks>
    /// Here we can define additional bonus options, like the ancient options (e.g. +5 / +10 Str etc.).
    /// </remarks>
    public List<ItemOfItemSet> Items;
}

