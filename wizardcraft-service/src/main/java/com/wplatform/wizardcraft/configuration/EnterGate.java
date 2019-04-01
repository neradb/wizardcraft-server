package com.wplatform.wizardcraft.configuration;

import lombok.Getter;
import lombok.Setter;

/// Defines a gate which a player can enter to move to another <see cref="ExitGate"/>.
/// </summary>
@Getter
@Setter
public class EnterGate extends Gate {
    /// <summary>
/// Gets or sets the target gate.
/// </summary>
//    public ExitGate TargetGate

    /// <summary>
/// Gets or sets the level requirement which the player needs to move through the gate.
/// </summary>
    private short levelRequirement;

    /// <summary>
/// Gets or sets the number of the gate.
/// </summary>
    private short number;
}