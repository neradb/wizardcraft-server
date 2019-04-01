package com.wplatform.wizardcraft.domain;

import com.wplatform.wizardcraft.items.IncreasableItemOption;

/// <summary>
/// This class defines a link between the item and the conrete item option which the actual item instance possess.
/// </summary>
public class ItemOptionLink {
    /// <summary>
    /// Gets or sets the item option.
    /// </summary>
    public IncreasableItemOption ItemOption;

    /// <summary>
    /// Gets or sets the level.
    /// </summary>
    public int Level = 1;

    /// <summary>
    /// Clones this instance.
    /// </summary>
    /// <returns>The cloned instance.</returns>
    public ItemOptionLink clone() {
        ItemOptionLink link = new ItemOptionLink();
        link.AssignValues(this);
        return link;
    }

    /// <summary>
    /// Assigns the values.
    /// </summary>
    /// <param name="otherLink">The other link.</param>
    public void AssignValues(ItemOptionLink otherLink) {
        this.ItemOption = otherLink.ItemOption;
        this.Level = otherLink.Level;
    }
}

