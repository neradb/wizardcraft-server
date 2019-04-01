package com.wplatform.wizardcraft.configuration;// <copyright file="MonsterDefinition.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import com.wplatform.wizardcraft.items.ItemCrafting;
import com.wplatform.wizardcraft.domain.ItemStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;

/// <summary>
/// A definition for a monster (or NPC in general).
/// </summary>
public class MonsterDefinition {
    /// <summary>
    /// Gets or sets the unique number of this monster.
    /// </summary>
    public short Number;

    /// <summary>
    /// Gets or sets the designation of this monster.
    /// Not relevant for the server, however helpful for debugging/logging.
    /// </summary>
    public String Designation;

    /// <summary>
    /// Gets or sets the range in which a monster will move randomly?
    /// It is not used yet. TODO: Find out what it's really good for. Remove, if not needed.
    /// </summary>
    public byte MoveRange;

    /// <summary>
    /// Gets or sets the attack range in which the monster can attack without moving closer to the target.
    /// </summary>
    /// <value>
    /// The attack range.
    /// </value>
    public byte AttackRange;

    /// <summary>
    /// Gets or sets the view range in which the monster can recognize its targets.
    /// </summary>
    public short ViewRange;

    /// <summary>
    /// Gets or sets the move delay for each step.
    /// </summary>
    public TimeUnit MoveDelay;

    /// <summary>
    /// Gets or sets the attack delay, which is the time between attacks.
    /// </summary>
    public TimeUnit AttackDelay;

    /// <summary>
    /// Gets or sets the delay which is waited until a died instance respawns.
    /// </summary>
    public TimeUnit RespawnDelay;

    /// <summary>
    /// Gets or sets the attribute.
    /// Not sure what this is.
    /// Maybe the maximum numbers of concurrent additional attributes / magic effects?
    /// TODO
    /// </summary>
    public byte Attribute;

    /// <summary>
    /// Gets or sets the skill.
    /// TODO Not sure what this means yet.
    /// I guess it is the magic effect, like stunning (skill 23 @ Dark Elf)
    /// </summary>
    public short Skill;

    /// <summary>
    /// Gets or sets the number of maximum item drops after an instance of this monster died.
    /// </summary>
    public int NumberOfMaximumItemDrops;

    /// <summary>
    /// Gets or sets the id of the npc window.
    /// </summary>
    public NpcWindow NpcWindow;

    /// <summary>
    /// Gets or sets the skill with which this monster is attacking. Also known as "Attack type".
    /// </summary>
    public Skill AttackSkill;

    /// <summary>
    /// Gets or sets the items of the merchant store. Is only relevant for merchant NPCs.
    /// </summary>
    public ItemStorage MerchantStore;

    /// <summary>
    /// Gets or sets the item craftings. Is only relevant for crafting NPCs (chaos goblin etc.).
    /// </summary>
    public List<ItemCrafting> ItemCraftings;

    /// <summary>
    /// Gets or sets the drop item groups.
    /// Some monsters drop special items. Examples: Kundun has the chance to drop ancient items.
    /// </summary>
    public List<DropItemGroup> DropItemGroups;

    /// <summary>
    /// Gets or sets the attributes of this monster.
    /// </summary>
    //// public IDictionary<AttributeDefinition, float> attributes { get; }
    public List<MonsterAttribute> Attributes;


    /// <summary>
    /// Type of the window which will be openend when talking to the npc.
    /// TODO: Maybe change to a class and do it data-driven.
    /// </summary>
    public static enum NpcWindow {
        /// <summary>
        /// No window defined.
        /// </summary>
        Undefined,

        /// <summary>
        /// A merchant window.
        /// </summary>
        Merchant,

        /// <summary>
        /// Another merchant window.
        /// </summary>
        Merchant1,

        /// <summary>
        /// A common window.
        /// </summary>
        Storage,

        /// <summary>
        /// A vault common.
        /// </summary>
        VaultStorage,

        /// <summary>
        /// A chaos machine window.
        /// </summary>
        ChaosMachine,

        /// <summary>
        /// A devil square window.
        /// </summary>
        DevilSquare,

        /// <summary>
        /// A blood castle window.
        /// </summary>
        BloodCastle,

        /// <summary>
        /// The pet trainer window.
        /// </summary>
        PetTrainer,

        /// <summary>
        /// The lahap window.
        /// </summary>
        Lahap,

        /// <summary>
        /// The castle senior window.
        /// </summary>
        CastleSeniorNPC,

        /// <summary>
        /// The elphis refinery window.
        /// </summary>
        ElphisRefinery,

        /// <summary>
        /// The refine stone making window.
        /// </summary>
        RefineStoneMaking,

        /// <summary>
        /// The jewel of harmony option removal window.
        /// </summary>
        RemoveJohOption,

        /// <summary>
        /// The illusion temple window.
        /// </summary>
        IllusionTemple,

        /// <summary>
        /// The chaos card combination window.
        /// </summary>
        ChaosCardCombination,

        /// <summary>
        /// The cherry blossom branches assembly window.
        /// </summary>
        CherryBlossomBranchesAssembly,

        /// <summary>
        /// The seed master window.
        /// </summary>
        SeedMaster,

        /// <summary>
        /// The seed researcher window.
        /// </summary>
        SeedResearcher,

        /// <summary>
        /// The stat reinitializer window.
        /// </summary>
        StatReInitializer,

        /// <summary>
        /// The delgado lucky coin registration window.
        /// </summary>
        DelgadoLuckyCoinRegistration,

        /// <summary>
        /// The doorkeeper titus duel watch window.
        /// </summary>
        DoorkeeperTitusDuelWatch,

        /// <summary>
        /// The lugard doppelganger entry window.
        /// </summary>
        LugardDoppelgangerEntry,

        /// <summary>
        /// The jerint gaion event entry window.
        /// </summary>
        JerintGaionEvententry,

        /// <summary>
        /// The julia warp market server window.
        /// </summary>
        JuliaWarpMarketServer,

        /// <summary>
        /// The guild master window.
        /// </summary>
        GuildMaster,

        /// <summary>
        /// The dialog window which allows to exchange or refine Lucky Item.
        /// Used by NPC "David".
        /// </summary>
        CombineLuckyItem,
    }
}
