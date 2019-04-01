package com.wplatform.wizardcraft.domain;

/// <summary>
/// The account of a player.
/// </summary>

import com.google.common.collect.Lists;
import com.wplatform.wizardcraft.configuration.CharacterClass;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    /// <summary>
    /// Gets or sets the unique login name.
    /// </summary>
    @Id
    @Column(name = "account_name", length = 10)
    private String accountName;

    /// <summary>
    /// Gets or sets the hash of the password, preferrably of BCrypt.
    /// </summary>
    @Column(name = "password_hash")
    private String passwordHash;

    /// <summary>
    /// Gets or sets the security code which is used to confirm character deletion and guild kicks.
    /// </summary>
    @Column(name = "security_code")
    private String securityCode;

    /// <summary>
    /// Gets or sets the e mail address.
    /// </summary>
    @Column(name = "email")
    private String email;

    /// <summary>
    /// Gets or sets the e mail address.
    /// </summary>
    @Column(name = "phone")
    private String phone;

    /// <summary>
    /// Gets or sets the unlocked character classes which are locked by default.
    /// </summary>
    /// <remarks>
    /// Some classes are only available when the player reached a certain level before, or when he paid for some unlock ticket.
    /// </remarks>
    @Transient
    private List<CharacterClass> unlockedCharacterClasses;

    /// <summary>
    /// Gets or sets the registration date.
    /// </summary>
    @Column(name = "registration_date")
    private Date registrationDate;

    /// <summary>
    /// Gets or sets the state.
    /// </summary>
    @Column(name = "acc_state")
    private State state = State.NORMAL;

    /// <summary>
    /// Gets or sets the timezone of the player, difference to UTC.
    /// </summary>
    @Column(name = "time_zone")
    private short timeZone;

    /// <summary>
    /// Gets or sets the vault password.
    /// </summary>
    @Column(name = "vault_password")
    private String vaultPassword;

    /// <summary>
    /// Gets or sets the vault.
    /// </summary>
    @Transient
    private ItemStorage vault;

    /// <summary>
    /// Gets or sets a value indicating whether this instance is vault extended.
    /// </summary>
    @Transient
    private boolean vaultExtended;

    /// <summary>
    /// Gets or sets the characters.
    /// </summary>
    @Transient
    private List<Characters> characters = Lists.newArrayList();

    /// <summary>
    /// The state of an account.
    /// </summary>
    public enum State {
        /// <summary>
        /// NORMAL player account.
        /// </summary>
        NORMAL,

        /// <summary>
        /// SPECTATOR account, invisible to players and monsters.
        /// </summary>
        SPECTATOR,

        /// <summary>
        /// Game Master account.
        /// </summary>
        GAME_MASTER,

        /// <summary>
        /// Game Master account, invisible to players and monsters.
        /// </summary>
        GAME_MASTER_INVISIBLE,

        /// <summary>
        /// BANNED account.
        /// </summary>
        BANNED,

        /// <summary>
        /// Temporarily banned account.
        /// </summary>
        TEMPORARILY_BANNED,
    }
}

