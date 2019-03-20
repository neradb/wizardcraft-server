package com.wplatform.muonline.domain.configuration;// <copyright file="GameMapDefinition.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import java.util.List;

/// <summary>
/// configuration of a map. Contains all information to create an instance of a GameMap.
/// </summary>
/// <remarks>
/// Some maps have different possible status, for expample the crywolf map:
///     1) Balgass is undeafeated - monsters are not dropping special items
///     2) Balgass is defeated - monsters are dropping special items
///     3) Crywolf event is ongoing - The normal monsters are not there(?), but event monsters.
/// For each of this status, there exist different terrain maps (safezones are different, etc.).
/// To reflect this requirement on this data models, for each status there must be one game map definition.
/// The switch between this status and its corresponding game map definitions should be done in game logic.
/// </remarks>
public class GameMapDefinition {
    /// <summary>
    /// Gets or sets the number of the map.
    /// </summary>
    /// <remarks>
    /// This number is identifying the map on the client.
    /// </remarks>
    public short Number;

    /// <summary>
    /// Gets or sets the name of the map.
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets the terrain data.
    /// </summary>
    /// <remarks>
    /// Content of the *.att file in the original server.
    /// </remarks>
    public byte[] TerrainData;

    /// <summary>
    /// Gets or sets the defined monster spawn areas.
    /// </summary>
    public List<MonsterSpawnArea> MonsterSpawns;

    /// <summary>
    /// Gets or sets the enter gates, though which the player can move to other maps.
    /// </summary>
    public List<EnterGate> EnterGates;

    /// <summary>
    /// Gets or sets the exp multiplier for this map.
    /// </summary>
    /// <value>
    /// The exp multiplier.
    /// </value>
    public double ExpMultiplier;

    /// <summary>
    /// Gets or sets the game map to which the player will be brought when it died.
    /// One of the <see cref="ExitGates"/> where <see cref="ExitGate.IsSpawnGate"/> is selected.
    /// </summary>
    public GameMapDefinition SafezoneMap;

    /// <summary>
    /// Gets or sets the spawn gates.
    /// </summary>
    public List<ExitGate> ExitGates;

    /// <summary>
    /// Gets or sets the DropItemGroup of this map.
    /// Some maps contain different drops. Examples: land of trials drops ancient items, kanturu drops gemstones.
    /// </summary>
    public List<DropItemGroup> DropItemGroups;


}

