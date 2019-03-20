package com.wplatform.muonline.domain.configuration.items;

import com.wplatform.muonline.domain.configuration.CharacterClass;
import com.wplatform.muonline.domain.configuration.Skill;

import java.util.List;

/// <summary>
/// Defines an item.
/// </summary>
public class ItemDefinition {
    /// <summary>
    /// Gets or sets the (Sub-)Id of this item. Must be unique in an item group.
    /// </summary>
    public short Number;

    /// <summary>
    /// Gets or sets the item slot where it can get equipped.
    /// </summary>
    public ItemSlotType ItemSlot;

    /// <summary>
    /// Gets or sets the width of the Item.
    /// </summary>
    public byte Width;

    /// <summary>
    /// Gets or sets the weight of the Item.
    /// </summary>
    public byte Height;

    /// <summary>
    /// Gets or sets a value indicating whether the item can be dropped by monsters.
    /// </summary>
    public boolean DropsFromMonsters;

    /// <summary>
    /// Gets or sets the name of the item.
    /// </summary>
    public String Name;

    /// <summary>
    /// Gets or sets the item drop level, which indicates the minimum monster lvl of which this item can be dropped
    /// </summary>
    public byte DropLevel;

    /// <summary>
    /// Gets or sets the maximum durability of this item at Level 0.
    /// </summary>
    public byte Durability;

    /// <summary>
    /// Gets or sets the item Group (0-15). TODO: Might change item groups to classes, and replace this by it.
    /// </summary>
    public byte Group;

    /// <summary>
    /// Gets or sets the value which defines the worth of an item in zen currency.
    /// </summary>
    public int Value;

    /// <summary>
    /// Gets or sets the class name of the consumer handler.
    /// </summary>
    public String ConsumeHandlerClass;

    /// <summary>
    /// Gets or sets the maximum number of sockets an instance of this item can have.
    /// </summary>
    public int MaximumSockets;

    /// <summary>
    /// Gets or sets the skill which this items adds to the skill list while wearing or which can be learned by consuming this item.
    /// TODO: Split these two usages into different properties?
    /// </summary>
    public Skill Skill;

    /// <summary>
    /// Gets or sets the character classes which are qualified to wear this Item.
    /// </summary>
    public List<CharacterClass> QualifiedCharacters;

    /// <summary>
    /// Gets or sets the possible item set groups.
    /// </summary>
    /// <remarks>
    /// With this we can define a lot of things, for example:
    ///   - double wear bonus of single swords
    ///   - set bonus for defense rate
    ///   - set bonus for defense, if level is greater than 9
    ///   - ancient sets
    /// </remarks>
    public List<ItemSetGroup> PossibleItemSetGroups;

    /// <summary>
    /// Gets or sets the possible item options.
    /// </summary>
    public List<ItemOptionDefinition> PossibleItemOptions;

    /// <summary>
    /// Gets or sets the requirements for wearing this item.
    /// </summary>
    public List<AttributeRequirement> Requirements;

    /// <summary>
    /// Gets or sets the base PowerUps of this item, for example min/max damage for weapons.
    /// </summary>
    public List<ItemBasePowerUpDefinition> BasePowerUpAttributes;
}

