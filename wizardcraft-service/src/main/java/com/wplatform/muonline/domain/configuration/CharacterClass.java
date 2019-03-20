package com.wplatform.muonline.domain.configuration;// <copyright file="CharacterClass.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import java.util.List;

/// <summary>
/// Defines a character class.
/// </summary>
public class CharacterClass {
    /// <summary>
    /// Gets or sets the id of a character class.
    /// This will be used to identify the class when getting created,
    /// and as identifier to be sent to client.
    /// </summary>
    public byte Number;

    /// <summary>
    /// Gets or sets the name of the character class.
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets a value indicating whether this character class can get created by the user.
    /// </summary>
    public boolean CanGetCreated;

    /// <summary>
    /// Gets or sets the level requirement when getting created.
    /// The level requirement must be fulfilled by another character of the same account.
    /// </summary>
    public short LevelRequirementByCreation;

    /// <summary>
    /// Gets or sets the creation allowed flag which is sent to the client in the character list message if this character class is in its <see cref="Account.UnlockedCharacterClasses"/>.
    /// </summary>
    /// <remarks>
    /// Flag about which characters can be created with this account:
    /// 1 = Summoner
    /// 2 = Dark Lord
    /// 4 = Magic Gladiator
    /// 8 = Rage Fighter
    /// </remarks>
    public byte CreationAllowedFlag;

    /// <summary>
    /// Gets or sets the upgrade points per level up.
    /// </summary>
    public short PointsPerLevelUp;

    /// <summary>
    /// Gets or sets the next generation class, to which a character can upgrade after
    /// fulfilling certain requirements, like quests.
    /// </summary>
    public CharacterClass NextGenerationClass;

    /// <summary>
    /// Gets or sets a value indicating whether this class is a master class and therefore can receive master experience for the master tree.
    /// </summary>
    public boolean IsMasterClass;

    /// <summary>
    /// Gets or sets the percent by which the moving level requirement for warping to other maps is reduced.
    /// </summary>
    public int LevelWarpRequirementReductionPercent;

    /// <summary>
    /// Gets or sets the fruit calculation strategy.
    /// </summary>
    public FruitCalculationStrategy FruitCalculation;

    /// <summary>
    /// Gets or sets the stat attributes.
    /// </summary>
    public List<StatAttributeDefinition> StatAttributes;

    /// <summary>
    /// Gets or sets the attribute combinations.
    /// </summary>
    //public List<AttributeRelationship> AttributeCombinations;

    /// <summary>
    /// Gets or sets the base attribute values.
    /// For example the amount of health a character got without any added stat point.
    /// </summary>
    //public List<ConstValueAttribute> BaseAttributeValues;

    /// <summary>
    /// Gets or sets the home map.
    /// </summary>
    public GameMapDefinition HomeMap;

}
