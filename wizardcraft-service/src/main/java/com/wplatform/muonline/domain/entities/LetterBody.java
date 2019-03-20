package com.wplatform.muonline.domain.entities;

import java.util.Date;

/// <summary>
/// The body of a letter.
/// </summary>
public class LetterBody {
    /// <summary>
    /// Gets or sets the identifier.
    /// </summary>
    public String Id ;

    /// <summary>
    /// Gets or sets the sender.
    /// </summary>
    /// <remarks>
    /// The persistence implementation can implement to keep an internal id, too.
    /// However, it should keep the name, if it should stay available after a sender character got deleted.
    /// </remarks>
    public String SenderName ;

    /// <summary>
    /// Gets or sets the receiver.
    /// </summary>
    /// <remarks>
    /// The persistence implementation can implement to keep an internal id, too.
    /// In this case, it may not be required to save the name itself.
    /// </remarks>
    public String ReceiverName ;

    /// <summary>
    /// Gets or sets the subject.
    /// </summary>
    public String Subject ;

    /// <summary>
    /// Gets or sets the letter date.
    /// </summary>
    public Date LetterDate ;

    /// <summary>
    /// Gets or sets a value indicating whether the letter has been read.
    /// </summary>
    public boolean ReadFlag ;
    

    /// <summary>
    /// Gets or sets the message.
    /// </summary>
    public String Message;

    /// <summary>
    /// Gets or sets the rotation of the sender character.
    /// </summary>
    public byte Rotation;

    /// <summary>
    /// Gets or sets the animation of the sender character.
    /// </summary>
    public byte Animation;

    /// <summary>
    /// Gets or sets the sender appearance data.
    /// </summary>
    public AppearanceData SenderAppearance;
}

