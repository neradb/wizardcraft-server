package com.wplatform.muonline.domain.configuration;// <copyright file="Skill.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import com.wplatform.muonline.domain.configuration.items.AttributeRequirement;
import org.hibernate.persister.walking.spi.AttributeDefinition;

import java.util.List;

/// <summary>
/// Defines a skill.
/// </summary>
public class Skill {
    /// <summary>
    /// Gets or sets the skill number.
    /// </summary>
    /// <remarks>
    /// The client is referencing skills by this number.
    /// </remarks>
    public short number;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    public String name;

    /// <summary>
    /// Gets or sets the requirements to execute the skill.
    /// </summary>
    public List<AttributeRequirement> requirements;

    /// <summary>
    /// Gets or sets the attributes which values will be consumed by executing this skill.
    /// </summary>
    public List<AttributeRequirement> ConsumeRequirements;

    /// <summary>
    /// Gets or sets the maximum range between executor of the skill and the target object.
    /// </summary>
    public short range;

    /// <summary>
    /// Gets or sets the type of the damage.
    /// </summary>
    public DamageType damageType;

    /// <summary>
    /// Gets or sets the type of the skill.
    /// </summary>
    public SkillType skillType;

    /// <summary>
    /// Gets or sets the <see cref="SkillTarget"/> which defines how the target(s) of a skill are determined.
    /// </summary>
    public SkillTarget target;

    /// <summary>
    /// Gets or sets the range for automatic targeting of additional target.
    /// Has only effect if greater than <c>0</c>.
    /// </summary>
    /// <remarks>
    /// Possible use cases: Additional hits for the "Fireburst" or "Deathstab" skills. They use direct targeting, but also hit nearby enemies.
    /// </remarks>
    public short implicitTargetRange;

    /// <summary>
    /// Gets or sets the target restriction.
    /// </summary>
    public SkillTargetRestriction TargetRestriction;

    /// <summary>
    /// Gets or sets a value indicating whether the skill moves the attacker to the target.
    /// </summary>
    /// <remarks>Used by dark knight weapon skills, e.g. Slash.</remarks>
    public boolean MovesToTarget;

    /// <summary>
    /// Gets or sets a value indicating whether the skill moves the target.
    /// </summary>
    /// <remarks>
    /// Used by dark knight weapon physical attack skills, e.g. Slash. The target gets pushed around randomly.
    /// This is not a use case for the lightning skill, since resistances play a role there.
    /// </remarks>
    public boolean MovesTarget;

    /// <summary>
    /// Gets or sets the elemental modifier target attribute.
    /// If this is set, hitting the target (successfully or not) may apply additional effects.
    /// A value of <c>1.0f</c> means, the target is immune to effects of this element.
    /// </summary>
    public AttributeDefinition ElementalModifierTarget;

    /// <summary>
    /// Gets or sets the magic effect definition. It will be applied for buff skills.
    /// </summary>
    public MagicEffectDefinition MagicEffectDef;

    /// <summary>
    /// Gets or sets the character classes which are qualified to learn and use this skill.
    /// </summary>
    public List<CharacterClass> QualifiedCharacters;

    /// <summary>
    /// Gets or sets the master skill definition. Only relevant for master skills.
    /// </summary>
    public MasterSkillDefinition MasterDefinition;

    /// <summary>
    /// Gets or sets the attack damage. Only relevant for attack skills.
    /// </summary>
    public int AttackDamage;


    /// <summary>
    /// Defines how a skill is restricted to specific targets.
    /// </summary>
    public enum SkillTargetRestriction {
        /// <summary>
        /// Undefined restriction. Skill can be applied to all possible entities (players, NPCs, etc.).
        /// </summary>
        Undefined,

        /// <summary>
        /// The skill can only be applied to the executor.
        /// </summary>
        Self,

        /// <summary>
        /// The skill can only be applied to the executor or its party members.
        /// </summary>
        Party,

        /// <summary>
        /// The skill can only be applied to players (and summoned monsters of a player).
        /// </summary>
        Player,
    }


    /// <summary>
    /// Defines the damage type of a skill.
    /// </summary>
    public static enum DamageType {
        /// <summary>
        /// No damage type.
        /// </summary>
        None,

        /// <summary>
        /// The physical damage type.
        /// </summary>
        Physical,

        /// <summary>
        /// The wizardry damage type.
        /// </summary>
        Wizardry,

        /// <summary>
        /// The curse damage type.
        /// </summary>
        Curse,

        /// <summary>
        /// The summoned monster damage type.
        /// </summary>
        SummonedMonster,

        /// <summary>
        /// All damage types.
        /// </summary>
        All
    }

    /// <summary>
    /// The skill types.
    /// </summary>
    public static enum SkillType {
        /// <summary>
        /// The skill hit its target directly.
        /// </summary>
        DirectHit,

        /// <summary>
        /// The castle siege special skill.
        /// </summary>
        CastleSiegeSpecial,

        /// <summary>
        /// Same as <see cref="DirectHit"/> but only applyable during castle siege event.
        /// </summary>
        CastleSiegeSkill,

        /// <summary>
        /// Area skill damage, which automatically hit all targets in its target area. No declaration of hits by the client.
        /// </summary>
        AreaSkillAutomaticHits,

        /// <summary>
        /// Area skill damage, but the hits have to be declared by the client.
        /// </summary>
        AreaSkillExplicitHits,

        /// <summary>
        /// The buff skill type. Applies magic effects on players.
        /// </summary>
        Buff,

        /// <summary>
        /// The regeneration skill type. Regenerates the target attribute of the defined effect.
        /// </summary>
        Regeneration,

        /// <summary>
        /// The passive boost skill type. Applies boosts to the player who has learned this skill, without the need to be casted.
        /// </summary>
        PassiveBoost,

        /// <summary>
        /// Other skill type.
        /// </summary>
        Other
    }

    /// <summary>
    /// Defines how the target(s) of a skill are determined.
    /// </summary>
    public static enum SkillTarget {
        /// <summary>
        /// The target selection is undefined.
        /// </summary>
        Undefined,

        /// <summary>
        /// The skill target is stated explicitly.
        /// </summary>
        Explicit,

        /// <summary>
        /// The targets are implicitly all party member which are in view range of the attacker.
        /// </summary>
        ImplicitParty,

        /// <summary>
        /// The targets are implicitly all players which are in <see cref="Skill.ImplicitTargetRange"/> of the attacker.
        /// </summary>
        ImplicitPlayersInRange,

        /// <summary>
        /// The targets are implicitly all non-player-characters in <see cref="Skill.ImplicitTargetRange"/> of the attacker.
        /// </summary>
        ImplicitNpcsInRange,

        /// <summary>
        /// The targets are implicitly all objects in <see cref="Skill.ImplicitTargetRange"/> of the attacker.
        /// </summary>
        ImplicitAllInRange,

        /// <summary>
        /// The primary target is stated explicitly, additional targets are all objects in the <see cref="Skill.ImplicitTargetRange"/> of the primary target.
        /// </summary>
        ExplicitWithImplicitInRange
    }

}

