package com.wplatform.muonline.domain.configuration;// <copyright file="WarpInfo.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


/// <summary>
/// Defines a warp list entry.
/// </summary>
public class WarpInfo {
    /// <summary>
    /// Gets or sets the index.
    /// </summary>
    public int Index;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets the warp costs.
    /// </summary>
    public int Costs;

    /// <summary>
    /// Gets or sets the level requirement which a character needs to fulfill so that it can warp to the <see cref="Gate"/>.
    /// </summary>
    public int LevelRequirement;

    /// <summary>
    /// Gets or sets the gate.
    /// </summary>
    public ExitGate Gate;

}

