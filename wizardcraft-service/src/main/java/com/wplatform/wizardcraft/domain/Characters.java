package com.wplatform.wizardcraft.domain;

import com.wplatform.wizardcraft.attribute.StatAttribute;
import com.wplatform.wizardcraft.configuration.CharacterClass;
import com.wplatform.wizardcraft.configuration.DropItemGroup;
import com.wplatform.wizardcraft.configuration.GameMapDefinition;
import com.wplatform.wizardcraft.constants.InventoryConstants;
import com.wplatform.wizardcraft.items.ItemSetGroup;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// <summary>
/// The character of a player.
/// </summary>
@Data
@Entity
@NamedEntityGraph(name = "Characters.Graph", attributeNodes = {@NamedAttributeNode("inventoryItems")})
@Table(name = "characters")
public class Characters {
    /// <summary>
    /// Gets or sets the identifier.
    /// </summary>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /// <summary>
    /// Gets or sets the name.
    /// </summary>
    @Column(name = "name")
    private String name;


    @Column(name = "level")
    private int level;


    /// <summary>
    /// Gets or sets the account name.
    /// </summary>
    @Column(name = "account_name")
    private String accountName;


    @Column(name = "character_class")
    private int characterClass;
    /// <summary>
    /// Gets or sets the character slot in the account.
    /// </summary>
    @Column(name = "character_slot")
    public int characterSlot;

    /// <summary>
    /// Gets or sets the create date.
    /// </summary>
    @Column(name = "create_date")
    public Date createDate;

    /// <summary>
    /// Gets or sets the experience.
    /// </summary>
    @Column(name = "experience")
    public long experience;

    /// <summary>
    /// Gets or sets the master experience.
    /// </summary>
    @Column(name = "master_experience")
    public long masterExperience;

    /// <summary>
    /// Gets or sets the remaining level up points which can be spent on increasable stat attributes.
    /// </summary>
    @Column(name = "level_up_points")
    public int levelUpPoints;

    /// <summary>
    /// Gets or sets the master level up points which can be spent on master skills.
    /// </summary>
    @Column(name = "master_level_up_points")
    public int masterLevelUpPoints;

    /// <summary>
    /// Gets or sets the current game map.
    /// </summary>
    @Transient
    public GameMapDefinition currentMap;

    /// <summary>
    /// Gets or sets the x-coordinate of its map position.
    /// </summary>
    @Column(name = "position_x")
    public byte positionX;

    /// <summary>
    /// Gets or sets the y-coordinate of its map position.
    /// </summary>
    @Column(name = "position_y")
    public byte positionY;

    /// <summary>
    /// Gets or sets the player kill count.
    /// </summary>
    @Column(name = "player_kill_count")
    public int playerKillCount;

    /// <summary>
    /// Gets or sets the remaining seconds for the current hero state, when the player state is not normal.
    /// </summary>
    @Column(name = "state_pemaining_seconds")
    public int stateRemainingSeconds;

    /// <summary>
    /// Gets or sets the hero state.
    /// </summary>
    @Column(name = "hero_state")
    public HeroState state;

    /// <summary>
    /// Gets or sets the character status.
    /// </summary>
    @Column(name = "character_status")
    public CharacterStatus characterStatus = CharacterStatus.Normal;

    /// <summary>
    /// Gets or sets the value.
    /// </summary>
    @Column(name = "character_pose")
    public Pose pose = Pose.Standing;

    /// <summary>
    /// Gets or sets the quest info. Don't know yet what its content is.
    /// </summary>
    @Transient
    public byte[] questInfo;

    /// <summary>
    /// Gets or sets the used fruit points.
    /// </summary>
    @Column(name = "used_fruit_points")
    public int usedFruitPoints;

    /// <summary>
    /// Gets or sets the used negative fruit points.
    /// </summary>
    @Column(name = "used_neg_fruit_points")
    public int UsedNegFruitPoints;

    /// <summary>
    /// Gets or sets the number of inventory extensions.
    /// </summary>
    @Column(name = "inventory_extensions")
    public int inventoryExtensions;

    /// <summary>
    /// Gets or sets the key configuration, which is set by the client and just saved as is.
    /// </summary>
    @Column(name = "key_configuration")
    public byte[] keyConfiguration;


    /// <summary>
    /// Gets or sets the stat attributes.
    /// </summary>
    @Transient
    public List<StatAttribute> Attributes;

    /// <summary>
    /// Gets or sets the letters.
    /// </summary>
    //@Transient
    //public List<LetterHeader> Letters;


    /// <summary>
    /// Gets or sets the learned skills.
    /// </summary>
    @Transient
    public List<SkillEntry> learnedSkills;

    /// <summary>
    /// Gets or sets the inventory.
    /// </summary>
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    public List<Item> inventoryItems;

    @Column(name = "inventory_money")
    private BigDecimal inventoryMoney;

    /// <summary>
    /// Gets or sets the drop item groups.
    /// </summary>
    @Transient
    public List<DropItemGroup> dropItemGroups;


    public List<Item> getEquippedItems() {
        return this.inventoryItems.stream()
                .filter(e -> e.getItemSlot() <= InventoryConstants.LastEquippableItemSlotIndex)
                .collect(Collectors.toList());
    }

    public boolean hasFullAncientSetEquipped() {
        Stream<Item> itemStream = inventoryItems.stream()
                .filter(i -> i.getItemSlot() <= InventoryConstants.LastEquippableItemSlotIndex
                        && i.getItemSlot() >= InventoryConstants.FirstEquippableItemSlotIndex
                        && i.ItemSetGroups.stream().anyMatch(group -> group.AncientSetDiscriminator > 0));
        Set<ItemSetGroup> ancientSets = itemStream
                .flatMap(i -> i.ItemSetGroups.stream())
                .filter(s -> s.AncientSetDiscriminator > 0)
                .collect(Collectors.toSet());
        return ancientSets.stream()
                .anyMatch(e -> e.Items.stream()
                        .allMatch(item -> itemStream
                                .anyMatch(i -> i.itemDefinition == item.ItemDefinition && i.getItemSetGroups().contains(e))
                        )
                );

    }


    /// <summary>
    /// The hero state of a player. Given enough time, the state converges to <see cref="NORMAL"/>.
    /// </summary>
    public enum HeroState {
        /// <summary>
        /// The character is new.
        /// </summary>
        New,

        /// <summary>
        /// The character is a hero.
        /// </summary>
        Hero,

        /// <summary>
        /// The character is a hero, but the hero state is almost gone.
        /// </summary>
        LightHero,

        /// <summary>
        /// The normal state.
        /// </summary>
        Normal,

        /// <summary>
        /// The character killed another character, and has a kill warning.
        /// </summary>
        PlayerKillWarning,

        /// <summary>
        /// The character killed two characters, and has some restrictions.
        /// </summary>
        PlayerKiller1stStage,

        /// <summary>
        /// The character killed more than two characters, and has hard restrictions.
        /// </summary>
        PlayerKiller2ndStage
    }

    /// <summary>
    /// The Characters Status of a player.
    /// </summary>
    public enum CharacterStatus {
        /// <summary>
        /// The character is normal
        /// </summary>
        Normal(0),

        /// <summary>
        /// The character is banned
        /// </summary>
        Banned(1),

        /// <summary>
        /// The character is a GAME_MASTER (have mu logo on the head)
        /// </summary>
        GameMaster(32);

        public final int value;

        CharacterStatus(int value) {
            this.value = value;
        }
    }

    /// <summary>
    /// The character value.
    /// </summary>
    public enum Pose {
        /// <summary>
        /// The character is standing (normal).
        /// </summary>
        Standing(0),

        /// <summary>
        /// The character is sitting on an object.
        /// </summary>
        Sitting(2),

        /// <summary>
        /// The character is leaning towards something (wall etc).
        /// </summary>
        Leaning(3),

        /// <summary>
        /// The character is hanging on something.
        /// </summary>
        Hanging(4);

        public final int value;

        Pose(int pose) {
            this.value = pose;
        }

    }


}

