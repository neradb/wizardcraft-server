package com.wplatform.wizardcraft.domain;

import com.wplatform.wizardcraft.items.ItemDefinition;
import com.wplatform.wizardcraft.items.ItemOptionType;
import com.wplatform.wizardcraft.items.ItemSetGroup;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;

/// <summary>
/// The item.
/// </summary>
@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "id")
    private int id;
    /// <summary>
    /// Gets or sets the item slot in the <see cref="ItemStorage"/>.
    /// </summary>
    @Column(name = "item_slot")
    private byte itemSlot;

    /// <summary>
    /// Gets or sets the item definition.
    /// </summary>
    @Transient
    public ItemDefinition itemDefinition;

    /// <summary>
    /// Gets or sets the currently remaining durability.
    /// </summary>
    @Column(name = "durability")
    public byte durability;

    /// <summary>
    /// Gets or sets the level of the item.
    /// </summary>
    @Column(name = "level")
    public byte level;

    /// <summary>
    /// Gets or sets a value indicating whether this item instance provides the weapon skill while being equipped.
    /// </summary>
    @Column(name = "has_skill")
    public boolean hasSkill;

    /// <summary>
    /// Gets or sets the item options.
    /// </summary>
    @Transient
    public List<ItemOptionLink> ItemOptions;

    /// <summary>
    /// Gets or sets the item set group (Ancient Set,).
    /// </summary>
    @Transient
    public List<ItemSetGroup> ItemSetGroups;

    /// <summary>
    /// Gets or sets the socket count. This limits the amount of socket options in the <see cref="ItemOptions"/>.
    /// </summary>
    @Column(name = "socket_count")
    public int SocketCount;

    /// <summary>
    /// Gets or sets the price which was set by the player for his personal store.
    /// </summary>
    @Column(name = "store_price")
    public int StorePrice;


    /// <summary>
    /// Clones the item option link.
    /// </summary>
    /// <param name="link">The link.</param>
    /// <returns>The cloned item option link.</returns>
    protected ItemOptionLink CloneItemOptionLink(ItemOptionLink link) {
        return link.clone();
    }


    public List<ItemOptionType> getVisibleOptions() {
        List<ItemOptionType> collect = this.ItemOptions.stream()
                .map(e -> e.ItemOption.OptionType)
                .filter(e -> e.IsVisible)
                .collect(Collectors.toList());
        return collect;
    }


}

