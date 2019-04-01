package com.wplatform.wizardcraft.configuration;// <copyright file="Direction.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


/// <summary>
/// Direction where an object is looking at.
/// </summary>
/// <remarks>
/// The directions are named/valued after how they look like due the game client.
/// That means, when a character looks to south, it looks straight downwards. Because the map is rotated on the game client, that's actually a corner.
/// Since we use the value 0 as 'Undefined' and the original game client uses value 0 as 'West', this has to be considered when communicating with it.
/// </remarks>
public enum Direction {
    /// <summary>
    /// The undefined direction.
    /// </summary>
    Undefined,

    /// <summary>
    /// The direction looking to the west.
    /// </summary>
    West,

    /// <summary>
    /// The direction looking to the south east.
    /// </summary>
    SouthWest,

    /// <summary>
    /// The direction looking to the south.
    /// </summary>
    South,

    /// <summary>
    /// The direction looking to the south west.
    /// </summary>
    SouthEast,

    /// <summary>
    /// The direction looking to the east.
    /// </summary>
    East,

    /// <summary>
    /// The direction looking to the north east.
    /// </summary>
    NorthEast,

    /// <summary>
    /// The direction looking to the north.
    /// </summary>
    North,

    /// <summary>
    /// The direction looking to the north west.
    /// </summary>
    NorthWest,
}
