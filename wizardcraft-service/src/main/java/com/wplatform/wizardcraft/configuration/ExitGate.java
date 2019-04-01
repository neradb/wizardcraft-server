package com.wplatform.wizardcraft.configuration;

import lombok.Getter;
import lombok.Setter;

/// <summary>
/// Defines a gate through which a player enters a map.
/// </summary>
@Getter
@Setter
public class ExitGate extends Gate {
    /// <summary>
/// Gets or sets the direction to which the player looks when he enters the map.
/// </summary>
    private Direction direction;

    /// <summary>
/// Gets or sets the map which will be entered.
/// </summary>
    private GameMapDefinition map;

    /// <summary>
/// Gets or sets a value indicating whether this instance is a spawn gate.
/// If it's not a spawn gate, it's a target of an <see cref="EnterGate"/>.
/// </summary>
/// <value>
///   <c>true</c> if this instance is spawn gate; otherwise, <c>false</c>.
/// </value>
    private boolean isSpawnGate;
}

