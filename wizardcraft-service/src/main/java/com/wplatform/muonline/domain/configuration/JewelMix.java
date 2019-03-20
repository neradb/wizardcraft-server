package com.wplatform.muonline.domain.configuration;

import com.wplatform.muonline.domain.configuration.items.ItemDefinition;

/// <summary>
/// Defines a jewel mix.
/// Some <see cref="SingleJewel"/> can be mixed together to a single <see cref="MixedJewel"/> to save storage place.
/// When single jewels are needed again, the client can unmix his <see cref="MixedJewel"/> back to several <see cref="SingleJewel"/>.
/// </summary>
public class JewelMix {
    /// <summary>
    /// Gets or sets gets the number of the mix.
    /// </summary>
    /// <remarks>
    /// This number is a reference for the client.
    /// </remarks>
    public byte Number;

    /// <summary>
    /// Gets or sets gets the single jewel item definition.
    /// </summary>
    public ItemDefinition SingleJewel;

    /// <summary>
    /// Gets or sets gets the mixed jewel item definition.
    /// </summary>
    public ItemDefinition MixedJewel;
}

