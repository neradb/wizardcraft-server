package com.wplatform.wizardcraft.configuration;

/// <summary>
/// Defines a gate through which a player can exit or enter to other maps.
/// </summary>
public class Gate {
    /// <summary>
    /// Gets or sets the upper left corner, x-coordinate.
    /// </summary>
    public byte X1;

    /// <summary>
    /// Gets or sets the upper left corner, y-coordinate.
    /// </summary>
    public byte Y1;

    /// <summary>
    /// Gets or sets the bottom right corner, x-coordinate.
    /// </summary>
    public byte X2;

    /// <summary>
    /// Gets or sets the bottom right corner, y-coordinate.
    /// </summary>
    public byte Y2;
}

