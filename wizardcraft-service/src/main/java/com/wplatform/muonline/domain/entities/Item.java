package com.wplatform.muonline.domain.entities;

import com.wplatform.muonline.domain.configuration.items.ItemDefinition;
import com.wplatform.muonline.domain.configuration.items.ItemSetGroup;

import java.util.List;

/// <summary>
/// The item.
/// </summary>
public class Item {
    /// <summary>
    /// Gets or sets the item slot in the <see cref="ItemStorage"/>.
    /// </summary>
    public byte ItemSlot;

    /// <summary>
    /// Gets or sets the item definition.
    /// </summary>
    public ItemDefinition itemDefinition;

    /// <summary>
    /// Gets or sets the currently remaining durability.
    /// </summary>
    public byte durability;

    /// <summary>
    /// Gets or sets the level of the item.
    /// </summary>
    public byte level;

    /// <summary>
    /// Gets or sets a value indicating whether this item instance provides the weapon skill while being equipped.
    /// </summary>
    public boolean hasSkill;

    /// <summary>
    /// Gets or sets the item options.
    /// </summary>
    public List<ItemOptionLink> ItemOptions;

    /// <summary>
    /// Gets or sets the item set group (Ancient Set,).
    /// </summary>
    public List<ItemSetGroup> ItemSetGroups;

    /// <summary>
    /// Gets or sets the socket count. This limits the amount of socket options in the <see cref="ItemOptions"/>.
    /// </summary>
    public int SocketCount;

    /// <summary>
    /// Gets or sets the price which was set by the player for his personal store.
    /// </summary>
    public int StorePrice;


    /// <summary>
    /// Clones the item option link.
    /// </summary>
    /// <param name="link">The link.</param>
    /// <returns>The cloned item option link.</returns>
    protected ItemOptionLink CloneItemOptionLink(ItemOptionLink link) {
        return link.clone();
    }
}

