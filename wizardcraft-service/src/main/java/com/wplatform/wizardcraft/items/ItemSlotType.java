package com.wplatform.wizardcraft.items;// <copyright file="ItemSlotType.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import java.util.List;

/// <summary>
/// The item slot type. Each of this may have one or more actual item slots.
/// </summary>
public class ItemSlotType {
    /// <summary>
    /// Gets or sets the description.
    /// </summary>
    public String Description;

    /// <summary>
    /// Gets or sets the item slots of this slot type.
    /// </summary>
    public List<Integer> ItemSlots;
}

