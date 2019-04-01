package com.wplatform.wizardcraft.domain;

import com.wplatform.wizardcraft.configuration.Skill;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/// <summary>
/// An actual entry of a skill in the characters skill list.
/// </summary>
public class SkillEntry {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private int level;

    /// <summary>
    /// Occurs when a property changed.
    /// </summary>
    public PropertyChangeSupport PropertyChanged;

    /// <summary>
    /// Gets or sets the skill definition.
    /// </summary>
    public Skill skill;

    /// <summary>
    /// Gets or sets the level of the skill, primarily master skill level.
    /// </summary>
    public int Level; //TODO Level addPropertyChangeListener

    /// <summary>
    /// Gets or sets the power up element of this skill of this player. It is a "cached" element which will be created on demand and can be applied multiple times.
    /// </summary>
    //public IElement buffPowerUp;

    /// <summary>
    /// Gets or sets the duration of the <see cref="BuffPowerUp"/>.
    /// </summary>
    /// <remarks>
    /// It is an IElement, because the duration can be dependent from the player attributes.
    /// </remarks>
    //public IElement powerUpDuration;


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


}

