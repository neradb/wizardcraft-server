package com.wplatform.muonline.domain.configuration.itemcrafting;

/// <summary>
/// Description of IItemCrafting.
/// </summary>
public class ItemCrafting {
    /// <summary>
    /// Gets or sets the number.
    /// </summary>
    /// <remarks>
    /// Referenced by the client with this number.
    /// </remarks>
    public byte Number;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets the name of the item crafting handler class.
    /// </summary>
    public String ItemCraftingHandlerClassName;

    /// <summary>
    /// Gets or sets the simple crafting settings.
    /// </summary>
    public SimpleCraftingSettings SimpleCraftingSettings;
}

