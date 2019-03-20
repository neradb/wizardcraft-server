package com.wplatform.muonline.domain.configuration;

/// <summary>
/// The configuration for a main packet handler.
/// </summary>
public class MainPacketHandlerConfiguration {
    /// <summary>
    /// Gets or sets the client version.
    /// </summary>
    /// <remarks>
    /// = new byte[] { 0x31, 0x30, 0x34, 0x30, 0x34 };
    /// </remarks>
    public byte[] ClientVersion;

    /// <summary>
    /// Gets or sets the client serial.
    /// </summary>
    /// <remarks>
    /// Encoding.ASCII.GetBytes("k1Pk2jcET48mxL3b");
    /// </remarks>
    public byte[] ClientSerial;
    /// <summary>
    /// Gets or sets the name of the appearance serializer class.
    /// If no explicit class name is specified, a default one will be used.
    /// </summary>
    public String AppearanceSerializerClassName;

    /// <summary>
    /// Gets or sets the packet handlers of this main packet handler.
    /// </summary>
    //public List<PacketHandlerConfiguration> PacketHandlers;
}

