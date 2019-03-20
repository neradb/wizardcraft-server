package com.wplatform.muonline.domain.configuration;

/// Defines a gate which a player can enter to move to another <see cref="ExitGate"/>.
/// </summary>
public class EnterGate extends Gate {
    /// <summary>
/// Gets or sets the target gate.
/// </summary>
//    public ExitGate TargetGate

    /// <summary>
/// Gets or sets the level requirement which the player needs to move through the gate.
/// </summary>
    public short LevelRequirement;

    /// <summary>
/// Gets or sets the number of the gate.
/// </summary>
    public short Number;
}