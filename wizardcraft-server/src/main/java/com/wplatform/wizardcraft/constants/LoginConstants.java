package com.wplatform.wizardcraft.constants;

public class LoginConstants {

    /// <summary>
    /// The password was wrong.
    /// </summary>
    public static final int INVALID_PASSWORD = 0;

    /// <summary>
    /// The login succeeded.
    /// </summary>
    public static final int OK = 1;

    /// <summary>
    /// The account is invalid.
    /// </summary>
    public static final int ACCOUNT_INVALID = 2;

    /// <summary>
    /// The account is already connected.
    /// </summary>
    public static final int ACCOUNT_ALREADY_CONNECTED = 3;

    /// <summary>
    /// The server is full.
    /// </summary>
    public static final int ServerIsFull = 4;

    /// <summary>
    /// The account is blocked.
    /// </summary>
    public static final int ACCOUNT_BLOCKED = 5;

    /// <summary>
    /// The client has a wrong version.
    /// </summary>
    public static final int WrongVersion = 6;

    /// <summary>
    /// An error occured during connection.
    /// </summary>
    /// <remarks>I think in the original game server it is the connection to some of the servers behind.</remarks>
    public static final int ConnectionError = 7;

    /// <summary>
    /// Connection closed because of three failed login requests.
    /// </summary>
    public static final int ConnectionClosed3Fails = 8;

    /// <summary>
    /// There is no payment information.
    /// </summary>
    public static final int NoChargeInfo = 9;

    /// <summary>
    /// Subscription term is over.
    /// </summary>
    public static final int SubscriptionTermOver = 10;

    /// <summary>
    /// Subscription time is over.
    /// </summary>
    public static final int SubscriptionTimeOver = 11;

    /// <summary>
    /// Only players over 15 years are allowed.
    /// </summary>
    public static final int OnlyPlayersOver15Yrs = 0x11;

    /// <summary>
    /// The account is temporarily blocked.
    /// </summary>
    public static final int TemporaryBlocked = 0x0E;

    /// <summary>
    /// The client connected from a blocked country.
    /// </summary>
    public static final int BadCountry = 0xD2;


    public static final String ACCOUNT_SESSION_KEY = "ACCOUNT_SESSION_KEY";
    public static final String CHARACTER_SESSION_KEY = "CHARACTER_SESSION_KEY";
}
