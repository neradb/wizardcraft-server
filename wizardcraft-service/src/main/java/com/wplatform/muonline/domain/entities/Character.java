package com.wplatform.muonline.domain.entities;

import com.wplatform.muonline.domain.configuration.CharacterClass;
import com.wplatform.muonline.domain.configuration.DropItemGroup;
import com.wplatform.muonline.domain.configuration.GameMapDefinition;

import java.util.Date;
import java.util.List;

/// <summary>
/// The character of a player.
/// </summary>
public class Character {
    /// <summary>
    /// Gets or sets the identifier.
    /// </summary>
    public String id;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    public String name;

    /// <summary>
    /// Gets or sets the character class.
    /// </summary>
    public CharacterClass CharacterClass;

    /// <summary>
    /// Gets or sets the character slot in the account.
    /// </summary>
    public byte characterSlot;

    /// <summary>
    /// Gets or sets the create date.
    /// </summary>
    public Date createDate;

    /// <summary>
    /// Gets or sets the experience.
    /// </summary>
    public long experience;

    /// <summary>
    /// Gets or sets the master experience.
    /// </summary>
    public long masterExperience;

    /// <summary>
    /// Gets or sets the remaining level up points which can be spent on increasable stat attributes.
    /// </summary>
    public int levelUpPoints;

    /// <summary>
    /// Gets or sets the master level up points which can be spent on master skills.
    /// </summary>
    public int masterLevelUpPoints;

    /// <summary>
    /// Gets or sets the current game map.
    /// </summary>
    public GameMapDefinition currentMap;

    /// <summary>
    /// Gets or sets the x-coordinate of its map position.
    /// </summary>
    public byte PositionX;

    /// <summary>
    /// Gets or sets the y-coordinate of its map position.
    /// </summary>
    public byte positionY;

    /// <summary>
    /// Gets or sets the player kill count.
    /// </summary>
    public int playerKillCount;

    /// <summary>
    /// Gets or sets the remaining seconds for the current hero state, when the player state is not normal.
    /// </summary>
    public int stateRemainingSeconds;

    /// <summary>
    /// Gets or sets the hero state.
    /// </summary>
    public HeroState state;

    /// <summary>
    /// Gets or sets the character status.
    /// </summary>
    public CharacterStatus characterStatus;

    /// <summary>
    /// Gets or sets the pose.
    /// </summary>
    public CharacterPose pose;

    /// <summary>
    /// Gets or sets the quest info. Don't know yet what its content is.
    /// </summary>
    public byte[] questInfo;

    /// <summary>
    /// Gets or sets the used fruit points.
    /// </summary>
    public int usedFruitPoints;

    /// <summary>
    /// Gets or sets the used negative fruit points.
    /// </summary>
    public int UsedNegFruitPoints;

    /// <summary>
    /// Gets or sets the number of inventory extensions.
    /// </summary>
    public int inventoryExtensions;

    /// <summary>
    /// Gets or sets the key configuration, which is set by the client and just saved as is.
    /// </summary>
    public byte[] keyConfiguration;

    /// <summary>
    /// Gets or sets the stat attributes.
    /// </summary>
    //public List<StatAttribute> attributes;

    /// <summary>
    /// Gets or sets the letters.
    /// </summary>
    public List<LetterBody> letters;

    /// <summary>
    /// Gets or sets the learned skills.
    /// </summary>
    public List<SkillEntry> learnedSkills;

    /// <summary>
    /// Gets or sets the inventory.
    /// </summary>
    public ItemStorage inventory;

    /// <summary>
    /// Gets or sets the drop item groups.
    /// </summary>
    public List<DropItemGroup> dropItemGroups;


    /// <summary>
    /// The hero state of a player. Given enough time, the state converges to <see cref="Normal"/>.
    /// </summary>
    public enum HeroState {
        /// <summary>
        /// The character is new.
        /// </summary>
        New,

        /// <summary>
        /// The character is a hero.
        /// </summary>
        Hero,

        /// <summary>
        /// The character is a hero, but the hero state is almost gone.
        /// </summary>
        LightHero,

        /// <summary>
        /// The normal state.
        /// </summary>
        Normal,

        /// <summary>
        /// The character killed another character, and has a kill warning.
        /// </summary>
        PlayerKillWarning,

        /// <summary>
        /// The character killed two characters, and has some restrictions.
        /// </summary>
        PlayerKiller1stStage,

        /// <summary>
        /// The character killed more than two characters, and has hard restrictions.
        /// </summary>
        PlayerKiller2ndStage
    }

    /// <summary>
    /// The Character Status of a player.
    /// </summary>
    public enum CharacterStatus {
        /// <summary>
        /// The character is normal
        /// </summary>
        Normal,

        /// <summary>
        /// The character is banned
        /// </summary>
        Banned,

        /// <summary>
        /// The character is a GameMaster (have mu logo on the head)
        /// </summary>
        GameMaster
    }

    /// <summary>
    /// The character pose.
    /// </summary>
    public enum CharacterPose {
        /// <summary>
        /// The character is standing (normal).
        /// </summary>
        Standing,

        /// <summary>
        /// The character is sitting on an object.
        /// </summary>
        Sitting,

        /// <summary>
        /// The character is leaning towards something (wall etc).
        /// </summary>
        Leaning,

        /// <summary>
        /// The character is hanging on something.
        /// </summary>
        Hanging
    }
}

