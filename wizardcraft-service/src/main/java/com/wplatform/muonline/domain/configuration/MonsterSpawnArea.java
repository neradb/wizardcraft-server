package com.wplatform.muonline.domain.configuration;

/// <summary>
/// Defines an monster spawn area.
/// </summary>
public class MonsterSpawnArea {
    /// <summary>
    /// Gets or sets the monster definition.
    /// </summary>
    public com.wplatform.muonline.domain.configuration.MonsterDefinition MonsterDefinition;

    /// <summary>
    /// Gets or sets the game map.
    /// </summary>
    public GameMapDefinition GameMap;

    /// <summary>
    /// Gets or sets the upper left corner x coordinate.
    /// </summary>
    public byte X1;

    /// <summary>
    /// Gets or sets the upper left corner y coordinate.
    /// </summary>
    public byte Y1;

    /// <summary>
    /// Gets or sets the bottom right corner x coordinate.
    /// </summary>
    public byte X2;

    /// <summary>
    /// Gets or sets the bottom right corner y coordinate.
    /// </summary>
    public byte Y2;

    /// <summary>
    /// Gets or sets the looking direction when spawning.
    /// </summary>
    public Direction Direction;

    /// <summary>
    /// Gets or sets the quantity of monsters which should spawn in the defined area.
    /// </summary>
    public short Quantity;

    /// <summary>
    /// Gets or sets the spawn trigger.
    /// </summary>
    public SpawnTrigger SpawnTrigger;


    /// <summary>
    /// Defines the trigger when a monster spawns.
    /// </summary>
    public static enum SpawnTrigger {
        /// <summary>
        /// The monster spawns and respawns automatically.
        /// </summary>
        Automatic,

        /// <summary>
        /// The monster spawns automatically during an event.
        /// </summary>
        AutomaticDuringEvent,

        /// <summary>
        /// The monster spawns just once at the beginning of an event.
        /// </summary>
        /// <remarks>
        /// For example blood castle gates, statues. Also golden monsters.
        /// </remarks>
        OnceAtEventStart
    }
}

