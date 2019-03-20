package com.wplatform.muonline.domain.configuration;

import java.util.List;

/// <summary>
/// Defines the game server configuration.
/// </summary>
public class GameServerConfiguration {
    /// <summary>
    /// Gets or sets the supported packet handlers, for each version.
    /// </summary>
    public List<MainPacketHandlerConfiguration> SupportedPacketHandlers;

    /// <summary>
    /// Gets or sets the maximum number of players which can connect.
    /// </summary>
    public short MaximumPlayers;

    /// <summary>
    /// Gets or sets the maps which should be hosted on the server.
    /// </summary>
    public List<GameMapDefinition> Maps;
}

