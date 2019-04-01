package com.wplatform.wizardcraft.domain;// <copyright file="Guild.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import java.util.List;

/// <summary>
/// A guild is a group of players who like to play together.
/// </summary>
public class Guild {
    /// <summary>
    /// Gets or sets the identifier.
    /// </summary>
    public String id;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    public String Name ;

    /// <summary>
    /// Gets or sets the logo.
    /// </summary>
    /// <remarks>
    /// It's like a 16 color 8x8 pixel bitmap, therefore has a size of 32 bytes.
    /// </remarks>
    public byte[] Logo ;

    /// <summary>
    /// Gets or sets the score.
    /// </summary>
    public int Score ;

    /// <summary>
    /// Gets or sets the guild notice which can be set by the guild master
    /// </summary>
    /// <remarks>Visible in green color after a character entered the game.</remarks>
    public String Notice ;

    /// <summary>
    /// Gets or sets the hostile guild. Members of a hostile guild can be killed without consequences.
    /// </summary>
    public  Guild Hostility ;

    /// <summary>
    /// Gets or sets the parent alliance guild.
    /// </summary>
    /// <value>
    /// The alliance guild.
    /// </value>
    public  Guild AllianceGuild ;

    /// <summary>
    /// Gets or sets the members.
    /// </summary>
    public List<GuildMember> Members;
}

