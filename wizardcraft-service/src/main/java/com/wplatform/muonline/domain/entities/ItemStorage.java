package com.wplatform.muonline.domain.entities;// <copyright file="ItemStorage.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import java.util.List;

/// <summary>
/// A storage where items can be stored.
/// </summary>
public class ItemStorage {
    /// <summary>
    /// Gets or sets the items which are stored.
    /// </summary>
    public List<Item> Items;

    /// <summary>
    /// Gets or sets the money which is stored.
    /// </summary>
    public int Money;
}

