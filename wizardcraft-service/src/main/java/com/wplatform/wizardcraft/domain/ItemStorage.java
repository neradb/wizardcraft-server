package com.wplatform.wizardcraft.domain;// <copyright file="ItemStorage.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import lombok.Data;

import java.util.List;

/// <summary>
/// A common where items can be stored.
/// </summary>
@Data
public class ItemStorage {

    /// <summary>
    /// Gets or sets the items which are stored.
    /// </summary>
    private List<Item> items;

    /// <summary>
    /// Gets or sets the money which is stored.
    /// </summary>
    private int money;
}

