package com.wplatform.muonline.domain.configuration.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// <summary>
/// Type of an item option.
/// </summary>
/// <remarks>For example excellent, option, socket, luck.</remarks>
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOptionType {
    /// <summary>
    /// Gets or sets the identifier.
    /// </summary>
    public String Id;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets the description.
    /// </summary>
    public String Description;
    /// <summary>
    /// Gets or sets a value indicating whether this item option is visible to other players.
    /// </summary>
    public boolean IsVisible;


}

