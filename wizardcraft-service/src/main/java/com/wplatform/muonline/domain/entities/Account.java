package com.wplatform.muonline.domain.entities;

/// <summary>
/// The account of a player.
/// </summary>

import com.wplatform.muonline.domain.configuration.CharacterClass;

import java.util.Date;
import java.util.List;

public class Account {
    /// <summary>
    /// Gets or sets the unique login name.
    /// </summary>
    public String LoginName;

    /// <summary>
    /// Gets or sets the hash of the password, preferrably of BCrypt.
    /// </summary>
    public String PasswordHash;

    /// <summary>
    /// Gets or sets the security code which is used to confirm character deletion and guild kicks.
    /// </summary>
    public String SecurityCode;

    /// <summary>
    /// Gets or sets the e mail address.
    /// </summary>
    public String EMail;

    /// <summary>
    /// Gets or sets the e mail address.
    /// </summary>
    public String phone;

    /// <summary>
    /// Gets or sets the unlocked character classes which are locked by default.
    /// </summary>
    /// <remarks>
    /// Some classes are only available when the player reached a certain level before, or when he paid for some unlock ticket.
    /// </remarks>
    public List<CharacterClass> UnlockedCharacterClasses;

    /// <summary>
    /// Gets or sets the registration date.
    /// </summary>
    public Date RegistrationDate;

    /// <summary>
    /// Gets or sets the state.
    /// </summary>
    public State State;

    /// <summary>
    /// Gets or sets the timezone of the player, difference to UTC.
    /// </summary>
    public short TimeZone;

    /// <summary>
    /// Gets or sets the vault password.
    /// </summary>
    public String VaultPassword;

    /// <summary>
    /// Gets or sets the vault.
    /// </summary>
    public ItemStorage Vault;

    /// <summary>
    /// Gets or sets a value indicating whether this instance is vault extended.
    /// </summary>
    public boolean IsVaultExtended;

    /// <summary>
    /// Gets or sets the characters.
    /// </summary>
    public List<Character> Characters;

    /// <summary>
    /// The state of an account.
    /// </summary>
    public enum State {
        /// <summary>
        /// Normal player account.
        /// </summary>
        Normal,

        /// <summary>
        /// Spectator account, invisible to players and monsters.
        /// </summary>
        Spectator,

        /// <summary>
        /// Game Master account.
        /// </summary>
        GameMaster,

        /// <summary>
        /// Game Master account, invisible to players and monsters.
        /// </summary>
        GameMasterInvisible,

        /// <summary>
        /// Banned account.
        /// </summary>
        Banned,

        /// <summary>
        /// Temporarily banned account.
        /// </summary>
        TemporarilyBanned,
    }
}

