package com.wplatform.muonline.domain.configuration;

import com.wplatform.muonline.domain.configuration.items.*;
import org.hibernate.persister.walking.spi.AttributeDefinition;

import java.util.List;

/// <summary>
/// Defines the game configuration.
/// A game configuration contains the whole configuration of a game, directly or indirectly.
/// </summary>
public class GameConfiguration {
    /// <summary>
    /// Gets or sets the maximum reachable level.
    /// </summary>
    public short MaximumLevel;

    /// <summary>
    /// Gets or sets the information range. This defines how far players can see other game objects.
    /// </summary>
    public byte InfoRange;

    /// <summary>
    /// Gets or sets a value indicating whether area skills hit players.
    /// </summary>
    /// <remarks>
    /// Usually false, during castle siege this might be true.
    /// </remarks>
    public boolean AreaSkillHitsPlayer;

    /// <summary>
    /// Gets or sets the maximum inventory money value.
    /// </summary>
    public int MaximumInventoryMoney;

    /// <summary>
    /// Gets or sets the maximum vault money value.
    /// </summary>
    public int MaximumVaultMoney;

    /// <summary>
    /// Gets or sets the experience table. Index is the player level, value the needed experience to reach that level.
    /// </summary>
    public long[] ExperienceTable;

    /// <summary>
    /// Gets or sets the master experience table. Index is the player level, value the needed experience to reach that level.
    /// </summary>
    public long[] MasterExperienceTable;

    /// <summary>
    /// Gets or sets the interval for attribute recoveries. See also MUnique.OpenMU.GameLogic.attributes.Stats.Regeneration.
    /// </summary>
    public int RecoveryInterval;

    /// <summary>
    /// Gets or sets the maximum numbers of letters a player can have in his inbox.
    /// </summary>
    public int MaximumLetters;

    /// <summary>
    /// Gets or sets the price of sending a letter.
    /// </summary>
    public int LetterSendPrice;

    /// <summary>
    /// Gets or sets the maximum number of characters per account.
    /// </summary>
    public byte MaximumCharactersPerAccount;

    /// <summary>
    /// Gets or sets the character name regex.
    /// </summary>
    /// <remarks>
    /// "^[a-zA-Z0-9]{3,10}$";
    /// </remarks>
    public String CharacterNameRegex;

    /// <summary>
    /// Gets or sets the maximum length of the password.
    /// </summary>
    public int MaximumPasswordLength;

    /// <summary>
    /// Gets or sets the maximum size of parties.
    /// </summary>
    public byte MaximumPartySize;

    /// <summary>
    /// Gets or sets the possible jewel mixes.
    /// </summary>
    public List<JewelMix> JewelMixes;

    /// <summary>
    /// Gets or sets the warp list.
    /// </summary>
    public List<WarpInfo> WarpList;

    /// <summary>
    /// Gets or sets the base drop item groups which are valid for the whole game.
    /// </summary>
    public List<DropItemGroup> BaseDropItemGroups;

    /// <summary>
    /// Gets or sets the skills of this game configuration.
    /// </summary>
    public List<Skill> Skills;

    /// <summary>
    /// Gets or sets the character classes.
    /// </summary>
    public List<CharacterClass> CharacterClasses;

    /// <summary>
    /// Gets or sets the item definitions.
    /// </summary>
    public List<ItemDefinition> Items;

    /// <summary>
    /// Gets or sets the item slot types.
    /// </summary>
    public List<ItemSlotType> ItemSlotTypes;

    /// <summary>
    /// Gets or sets the item option definitions.
    /// </summary>
    public List<ItemOptionDefinition> ItemOptions;

    /// <summary>
    /// Gets or sets the item option types.
    /// </summary>
    public List<ItemOptionType> ItemOptionTypes;

    /// <summary>
    /// Gets or sets the item set groups.
    /// </summary>
    public List<ItemSetGroup> ItemSetGroups;

    /// <summary>
    /// Gets or sets the map definitions.
    /// </summary>
    public List<GameMapDefinition> Maps;

    /// <summary>
    /// Gets or sets the monster definitions.
    /// </summary>
    public List<MonsterDefinition> Monsters;

    /// <summary>
    /// Gets or sets the attributes.
    /// </summary>
    public List<AttributeDefinition> Attributes;

    /// <summary>
    /// Gets or sets the magic effects.
    /// </summary>
    public List<MagicEffectDefinition> MagicEffects;

    /// <summary>
    /// Gets or sets the master skill roots.
    /// </summary>
    public List<MasterSkillRoot> MasterSkillRoots;

    /// <summary>
    /// Gets or sets the plug in configurations.
    /// </summary>
    //public List<PlugInConfiguration> PlugInConfigurations;
}
