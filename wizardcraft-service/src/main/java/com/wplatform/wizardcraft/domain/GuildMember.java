package com.wplatform.wizardcraft.domain;

/// <summary>
/// Information about a guild member.
/// </summary>
public class GuildMember {

    /// <summary>
    /// Gets or sets the identifier. Should be the same id as the character id to which it belongs.
    /// </summary>
    public String id;

    /// <summary>
    /// Gets or sets the guild identifier to which the member belongs.
    /// </summary>
    public String guildId;

    /// <summary>
    /// Gets or sets the status of the member.
    /// </summary>
    //public GuildPosition status;
}

