package net.laserdiamond.ventureplugin.entities.player;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.tuning.Components.TuningPoints;
import net.laserdiamond.ventureplugin.tuning.Components.TuningStats;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import org.bukkit.entity.Player;

public class StatPlayer {

    private final Player player;
    private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final StatProfileManager statProfileManager = plugin.getStatProfileManager();
    private final PlayerConfig baseStatsConfig = plugin.getBaseStatsConfig();

    public StatPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the player's stat profile
     * @return The player's stat profile
     */
    public StatProfile getStatProfile() {
        return statProfileManager.getStatProfile(player.getUniqueId());
    }

    /**
     * Gets the player's basic venture stats
     * @return The player's stats
     */
    public Stats getStats() {
        return getStatProfile().stats();
    }

    /**
     * Gets the player's armor stats
     * @return The player's armor stats
     */
    public ArmorStats getArmorStats()
    {
        return getStatProfile().armorStats();
    }

    /**
     * Gets the player's health without including modifiers from either items in their hands
     * @return The health of the player not including items in either hand
     */
    public double getHealthWithoutItemHand()
    {
        double baseHealth = this.baseStatsConfig.getDouble("baseHealth");
        double armorHealth = this.getArmorStats().getHealth();
        double enchantHealth = this.getEnchantStats().getHealth();
        double tuningHealth = this.getTuningStats().getHealth();
        double armorTrimHealth = this.getArmorTrimStats().armorTrimMaterialStats().getIronHealthBoost();

        return baseHealth + armorHealth + enchantHealth + tuningHealth + armorTrimHealth;
    }

    /**
     * Gets the player's defense without including modifiers from either items in their hands
     * @return The defense of the player not including items in either hand
     */
    public double getDefenseWithoutItemHand()
    {
        double baseDefense = this.baseStatsConfig.getDouble("baseDefense");
        double armorDefense = this.getArmorStats().getDefense();
        double enchantDefense = this.getEnchantStats().getDefense();
        double tuningDefense = this.getTuningStats().getDefense();
        double armorTrimDefense = this.getArmorTrimStats().armorTrimMaterialStats().getNetheriteBonusDefense();
        double skillDefense = this.getSkillsReward().getMiningDefenseBonus();

        return baseDefense + armorDefense + enchantDefense + tuningDefense + armorTrimDefense + skillDefense;
    }

    /**
     * Gets the player's fire defense without including modifiers from either items in their hands
     * @return The fire defense of the player not including items in either hand
     */
    public double getFireDefenseWithoutItemHand()
    {
        double baseFireDefense = this.baseStatsConfig.getDouble("baseFireDefense");
        double armorFireDefense = this.getArmorStats().getFireDefense();
        double enchantFireDefense = this.getEnchantStats().getFireDefense();
        double armorTrimFireDefense = this.getArmorTrimStats().armorTrimMaterialStats().getNetheriteBonusFireDefense();

        return baseFireDefense + armorFireDefense + enchantFireDefense + armorFireDefense;
    }

    /**
     * Gets the player's explosion defense without including modifiers from either items in their hands
     * @return The explosion defense of the player not including items in either hand
     */
    public double getExplosionDefenseWithoutItemHand()
    {
        double baseExplosionDefense = this.baseStatsConfig.getDouble("baseExplosionDefense");
        double armorExplosionDefense = this.getArmorStats().getExplosionDefense();
        double enchantExplosionDefense = this.getEnchantStats().getExplosionDefense();

        return baseExplosionDefense + armorExplosionDefense + enchantExplosionDefense;
    }

    /**
     * Gets the player's projectile defense without including modifiers from either items in their hands
     * @return The projectile defense of the player not including items in either hand
     */
    public double getProjectileDefenseWithoutItemHand()
    {
        double baseProjectileDefense = this.baseStatsConfig.getDouble("baseProjectileDefense");
        double armorProjectileDefense = this.getArmorStats().getProjectileDefense();
        double enchantProjectileDefense = this.getEnchantStats().getProjectileDefense();

        return baseProjectileDefense + armorProjectileDefense + enchantProjectileDefense;
    }

    /**
     * Gets the player's magic defense without including modifiers from either items in their hands
     * @return The magic defense of the player not including items in either hand
     */
    public double getMagicDefenseWithoutItemHand()
    {
        double baseMagicDefense = this.baseStatsConfig.getDouble("baseMagicDefense");
        double armorMagicDefense = this.getArmorStats().getMagicDefense();
        double enchantMagicDefense = this.getEnchantStats().getMagicDefense();

        return baseMagicDefense + armorMagicDefense + enchantMagicDefense;
    }

    /**
     * Gets the player's toughness without including modifiers from either items in their hands
     * @return The toughness of the player not including items in either hand
     */
    public double getToughnessWithoutItemHand()
    {
        double baseToughness = this.baseStatsConfig.getDouble("baseToughness");
        double armorToughness = this.getArmorStats().getToughness();
        double enchantToughness = this.getEnchantStats().getToughness();

        return baseToughness + armorToughness + enchantToughness;
    }

    /**
     * Gets the player's fortitude without including modifiers from either items in their hands
     * @return The fortitude of the player not including items in either hand
     */
    public double getFortitudeWithoutItemHand()
    {
        double armorFortitude = this.getArmorStats().getFortitude();

        return armorFortitude;
    }

    /**
     * Gets the player's speed without including modifiers from either items in their hands
     * @return The speed of the player not including items in either hand
     */
    public double getSpeedWithoutItemHand()
    {
        double baseSpeed = this.baseStatsConfig.getDouble("baseSpeedPoints");
        double armorSpeed = this.getArmorStats().getSpeed();
        double enchantSpeed = this.getEnchantStats().getSpeed();
        double tuningSpeed = this.getTuningStats().getSpeed();
        double armorTrimSpeed = this.getArmorTrimStats().armorTrimMaterialStats().getCopperSpeed();

        return baseSpeed + armorSpeed + enchantSpeed + tuningSpeed + armorTrimSpeed;
    }

    /**
     * Gets the player's max mana without including modifiers from either items in their hands
     * @return The mana of the player not including items in either hand
     */
    public double getManaWithoutItemHand()
    {
        double baseMana = this.baseStatsConfig.getDouble("baseMana");
        double armorMana = this.getArmorStats().getMana();
        double enchantMana = this.getEnchantStats().getMana();
        double tuningMana = this.getTuningStats().getMana();
        double armorTrimMana = this.getArmorTrimStats().armorTrimMaterialStats().getDiamondBonusMana();
        double skillMana = this.getSkillsReward().getEnchantingManaBonus();

        return baseMana + armorMana + enchantMana + tuningMana + armorTrimMana + skillMana;
    }

    /**
     * Gets the player's percentage melee damage without including modifiers from either items in their hands
     * @return The percentage melee damage not including items in either hand
     */
    public double getPercentMeleeDamageWithoutItemHand()
    {
        double basePercentMelee = this.baseStatsConfig.getDouble("meleeDamage");
        double armorPercentMelee = this.getArmorStats().getPercentMeleeDamage();

        return basePercentMelee + armorPercentMelee;
    }

    /**
     * Gets the player's percentage magic damage without including modifiers from either items in their hands
     * @return The percentage magic damage not including items in either hand
     */
    public double getPercentMagicDamageWithoutItemHand()
    {
        double basePercentMagic = this.baseStatsConfig.getDouble("magicDamage");
        double armorPercentMagic = this.getArmorStats().getPercentMagicDamage();

        return basePercentMagic + armorPercentMagic;
    }

    /**
     * Gets the player's percentage range damage without including modifiers from either items in their hands
     * @return The percentage range damage not including items in either hand
     */
    public double getPercentRangeDamageWithoutItemHand()
    {
        double basePercentRange = this.baseStatsConfig.getDouble("rangeDamage");
        double armorPercentRange = this.getArmorStats().getPercentRangeDamage();

        return basePercentRange + armorPercentRange;
    }

    /**
     * Gets the player's base melee damage without including modifiers from either items in their hands
     * @return The base melee damage not including items in either hand
     */
    public double getBaseMeleeDamageWithoutItemHand()
    {
        double baseBaseMelee = this.baseStatsConfig.getDouble("baseMelee");
        double enchantBaseMelee = this.getEnchantStats().getBaseMelee();
        double tuningBaseMelee = this.getTuningStats().getMelee();

        return baseBaseMelee + enchantBaseMelee + tuningBaseMelee;
    }

    /**
     * Gets the player's base magic damage without including modifiers from either items in their hands
     * @return The base magic damage not including items in either hand
     */
    public double getBaseMagicDamageWithoutItemHand()
    {
        double baseBaseMagic = this.baseStatsConfig.getDouble("baseMagic");
        double enchantBaseMagic = this.getEnchantStats().getBaseMagic();
        double tuningBaseMagic = this.getTuningStats().getMagic();

        return baseBaseMagic + enchantBaseMagic + tuningBaseMagic;
    }

    /**
     * Gets the player's base range damage without including modifiers from either items in their hands
     * @return The base range damage not including items in either hand
     */
    public double getBaseRangeDamageWithoutItemHand()
    {
        double baseBaseRange = this.baseStatsConfig.getDouble("baseRange");
        double enchantBaseRange = this.getEnchantStats().getBaseRange();
        double tuningBaseRange = this.getTuningStats().getRange();

        return baseBaseRange + enchantBaseRange + tuningBaseRange;
    }

    /**
     * Gets the player's mob looting without including modifiers from either items in their hands
     * @return The mob looting of the player not including items in either hand
     */
    public double getMobLootingWithoutItemHand()
    {
        double armorMobLooting = this.getArmorStats().getMobFortune();
        double enchantMobLooting = this.getEnchantStats().getMobLoot();

        return armorMobLooting + enchantMobLooting;
    }

    /**
     * Gets the player's mining fortune without including modifiers from either items in their hands
     * @return The mining fortune of the player not including items in either hand
     */
    public double getMiningFortuneWithoutItemHand()
    {
        double armorMiningFortune = this.getArmorStats().getMiningFortune();
        double enchantMiningFortune = this.getEnchantStats().getMiningFortune();
        double skillMiningFortune = this.getSkillsReward().getMiningFortuneBonus();

        return armorMiningFortune + enchantMiningFortune + skillMiningFortune;
    }

    /**
     * Gets the player's foraging fortune without including modifiers from either items in their hands
     * @return The foraging fortune of the player not including items in either hand
     */
    public double getForagingFortuneWithoutItemHand()
    {
        double armorForagingFortune = this.getArmorStats().getForagingFortune();
        double enchantForagingFortune = this.getEnchantStats().getForagingFortune();
        double skillForagingFortune = this.getSkillsReward().getForagingFortuneBonus();

        return armorForagingFortune + enchantForagingFortune + skillForagingFortune;
    }

    /**
     * Gets the player's farming fortune without including modifiers from either items in their hands
     * @return The farming fortune of the player not including items in either hand
     */
    public double getFarmingFortuneWithoutItemHand()
    {
        double armorFarmingFortune = this.getArmorStats().getFarmingFortune();
        double enchantFarmingFortune = this.getEnchantStats().getFarmingFortune();
        double skillFarmingFortune = this.getSkillsReward().getFarmingFortuneBonus();

        return armorFarmingFortune + enchantFarmingFortune + skillFarmingFortune;
    }

    /**
     * Gets the player's fishing luck without including modifiers from either items in their hands
     * @return The fishing luck of the player not including items in either hand
     */
    public double getFishingLuckWithoutItemHand()
    {
        double armorFishingLuck = this.getArmorStats().getFishingLuck();
        double enchantFishingLuck = this.getEnchantStats().getFishingLuck();
        double skillFishingLuck = this.getSkillsReward().getFishingLuckBonus();

        return armorFishingLuck + enchantFishingLuck + skillFishingLuck;
    }

    /**
     * Gets the player's luck without including modifiers from either items in their hands
     * @return The luck of the player not including items in either hand
     */
    public double getLuckWithoutItemHand()
    {
        double baseLuck = this.baseStatsConfig.getDouble("baseLuck");
        double armorLuck = this.getArmorStats().getLuck();

        return baseLuck + armorLuck;
    }

    /**
     * Gets the player's longevity without including modifiers from either items in their hands
     * @return The longevity of the player not including items in either hand
     */
    public double getLongevityWithoutItemHand()
    {
        double armorLongevity = this.getArmorStats().getLongevity();
        double armorTrimLongevity = this.getArmorTrimStats().armorTrimMaterialStats().getRedstoneLongevity();
        double skillLongevity = this.getSkillsReward().getBrewingLongevity();

        return armorLongevity + armorTrimLongevity + skillLongevity;
    }

    /**
     * Gets the player's caffeination without including modifiers from either items in their hands
     * @return The caffeination of the player not including items in either hand
     */
    public double getCaffeinationWithoutItemHand()
    {
        double armorCaffeination = this.getArmorStats().getCaffeination();
        double skillCaffeination = this.getSkillsReward().getBrewingCaffeination();

        return armorCaffeination + skillCaffeination;
    }

    /**
     * Automatically adds/subtracts to the player's armor health and base health stat
     * @param value The amount of health to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorHealth(double value)
    {
        this.getArmorStats().setHealth(this.getArmorStats().getHealth() + value);
        this.getStats().setHealth(this.getStats().getHealth() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor defense and base defense stat
     * @param value The amount of defense to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorDefense(double value)
    {
        this.getArmorStats().setDefense(this.getArmorStats().getDefense() + value);
        this.getDefenseStats().setDefense(this.getDefenseStats().getDefense() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor fire defense and base fire defense stat
     * @param value The amount of fire defense to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorFireDefense(double value)
    {
        this.getArmorStats().setFireDefense(this.getArmorStats().getFireDefense() + value);
        this.getDefenseStats().setFireDefense(this.getDefenseStats().getFireDefense() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor explosion defense and base explosion defense stat
     * @param value The amount of explosion defense to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorExplosionDefense(double value)
    {
        this.getArmorStats().setExplosionDefense(this.getArmorStats().getExplosionDefense() + value);
        this.getDefenseStats().setExplosionDefense(this.getDefenseStats().getExplosionDefense() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor projectile defense and base projectile defense stat
     * @param value The amount of projectile defense to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorProjectileDefense(double value)
    {
        this.getArmorStats().setProjectileDefense(this.getArmorStats().getProjectileDefense() + value);
        this.getDefenseStats().setProjectileDefense(this.getDefenseStats().getProjectileDefense() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor magic defense and base magic defense stat
     * @param value The amount of magic defense to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorMagicDefense(double value)
    {
        this.getArmorStats().setMagicDefense(this.getArmorStats().getMagicDefense() + value);
        this.getDefenseStats().setMagicDefense(this.getDefenseStats().getMagicDefense() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor toughness and base toughness stat
     * @param value The amount of toughness to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorToughness(double value)
    {
        this.getArmorStats().setToughness(this.getArmorStats().getToughness() + value);
        this.getDefenseStats().setToughness(this.getDefenseStats().getToughness() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor fortitude and base fortitude stat
     * @param value The amount of fortitude to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorFortitude(double value)
    {
        this.getArmorStats().setFortitude(this.getArmorStats().getFortitude() + value);
        this.getDefenseStats().setFortitude(this.getDefenseStats().getFortitude() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor movement speed and base movement speed stat
     * @param value The amount of movement speed to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorSpeed(double value)
    {
        this.getArmorStats().setSpeed(this.getArmorStats().getSpeed() + value);
        this.getStats().setSpeed(this.getStats().getSpeed() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor mana and base mana stat
     * @param value The amount of mana to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorMana(double value)
    {
        this.getArmorStats().setMana(this.getArmorStats().getMana() + value);
        this.getStats().setMaxMana(this.getStats().getMaxMana() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor percentage melee damage and base percentage melee damage stat
     * @param value The amount of percentage melee damage to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorPercentMeleeDamage(double value)
    {
        this.getArmorStats().setPercentMeleeDamage(this.getArmorStats().getPercentMeleeDamage() + value);
        this.getDamageStats().setPercentMeleeDmg(this.getDamageStats().getPercentMelee() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor percentage magic damage and base percentage magic damage stat
     * @param value The amount of percentage magic damage to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorPercentMagicDamage(double value)
    {
        this.getArmorStats().setPercentMagicDamage(this.getArmorStats().getPercentMagicDamage() + value);
        this.getDamageStats().setPercentMagicDmg(this.getDamageStats().getPercentMagic() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor percentage range damage and base percentage range damage stat
     * @param value The amount of projectile defense to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorPercentRangeDamage(double value)
    {
        this.getArmorStats().setPercentRangeDamage(this.getArmorStats().getPercentRangeDamage() + value);
        this.getDamageStats().setPercentRangeDmg(this.getDamageStats().getPercentRange() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor mob looting and base mob looting stat
     * @param value The amount of mob looting to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorMobLooting(double value)
    {
        this.getArmorStats().setMobFortune(this.getArmorStats().getMobFortune() + value);
        this.getLootStats().setMobLooting(this.getLootStats().getMobLooting() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor mining fortune and base mining fortune stat
     * @param value The amount of mining fortune to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorMiningFortune(double value)
    {
        this.getArmorStats().setMiningFortune(this.getArmorStats().getMiningFortune() + value);
        this.getLootStats().setMiningFortune(this.getLootStats().getMiningFortune() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor foraging fortune and base foraging fortune stat
     * @param value The amount of foraging fortune to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorForagingFortune(double value)
    {
        this.getArmorStats().setForagingFortune(this.getArmorStats().getForagingFortune() + value);
        this.getLootStats().setForagingFortune(this.getLootStats().getForagingFortune() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor farming fortune and base farming fortune stat
     * @param value The amount of farming fortune to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorFarmingFortune(double value)
    {
        this.getArmorStats().setFarmingFortune(this.getArmorStats().getFarmingFortune() + value);
        this.getLootStats().setFarmingFortune(this.getLootStats().getFarmingFortune() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor fishing luck and base fishing luck stat
     * @param value The amount of fishing luck to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorFishingLuck(double value)
    {
        this.getArmorStats().setFishingLuck(this.getArmorStats().getFishingLuck() + value);
        this.getLootStats().setFishingLuck(this.getLootStats().getFishingLuck() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor luck and base luck stat
     * @param value The amount of luck to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorLuck(double value)
    {
        this.getArmorStats().setLuck(this.getArmorStats().getLuck() + value);
        this.getStats().setLuck(this.getStats().getLuck() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor longevity and base longevity stat
     * @param value The amount of longevity to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorLongevity(double value)
    {
        this.getArmorStats().setLongevity(this.getArmorStats().getLongevity() + value);
        this.getPotionStats().setLongevity(this.getPotionStats().getLongevity() + value);
        return this;
    }

    /**
     * Automatically adds/subtracts to the player's armor caffeination and base caffeination stat
     * @param value The amount of caffeination to add/subtract. Use negative values to remove
     * @return {@link StatPlayer}
     */
    public StatPlayer modifyArmorCaffeination(double value)
    {
        this.getArmorStats().setCaffeination(this.getArmorStats().getCaffeination() + value);
        this.getPotionStats().setCaffeination(this.getPotionStats().getCaffeination() + value);
        return this;
    }

    /**
     * Gets the player's damage stats
     * @return The player's damage stats
     */
    public DamageStats getDamageStats() {
        return getStatProfile().damageStats();
    }

    /**
     * Gets the player's defense stats
     * @return The player's defense stats
     */
    public DefenseStats getDefenseStats() {
        return getStatProfile().defenseStats();
    }

    /**
     * Gets the player's looting, luck and fortune stats
     * @return The player's looting, luck and fortune stats
     */
    public LootStats getLootStats() {
        return getStatProfile().lootStats();
    }

    /**
     * Gets the player's enchantment stats
     * @return The player's enchantment stats
     */
    public EnchantStats getEnchantStats() {
        return getStatProfile().enchantStats();
    }



    /**
     * Gets the player's armor trim stats
     * @return The player's armor trim stats
     */
    public ArmorTrimStats getArmorTrimStats() {
        return getStatProfile().armorTrimStats();
    }

    /**
     * Gets the player's potion-related stats
     * @return The player's potion-related stats
     */
    public PotionStats getPotionStats()
    {
        return getStatProfile().potionStats();
    }

    /**
     * Gets the player's tuning point stats
     * @return The player's tuning point stats
     */
    public TuningPoints getTuningPointStats()
    {
        return getStatProfile().tuningProfile().tuningPoints();
    }

    /**
     * Gets the player's tuning stats
     * @return The player's tuning stats
     */
    public TuningStats getTuningStats()
    {
        return getStatProfile().tuningProfile().tuningStats();
    }

    /**
     * Gets the player's skill profile
     * @return The player's skill profile
     */
    public SkillsProfile getSkillsProfile()
    {
        return getStatProfile().skillsProfile();
    }

    /**
     * Gets the player's skill experience
     * @return The player's skill experience
     */
    public SkillsEXP getSkillsEXP()
    {
        return getSkillsProfile().skillsEXP();
    }

    /**
     * Gets the player's skill levels
     * @return The player's skill levels
     */
    public SkillsLevel getSkillsLevel()
    {
        return getSkillsProfile().skillsLevel();
    }

    /**
     * Gets the player's skill reward bonuses
     * @return The player's skill reward bonuses
     */
    public SkillsReward getSkillsReward()
    {
        return getSkillsProfile().skillsReward();
    }

    /**
     * Gets the player's main-hand stats
     * @return The player's main-hand stats
     */
    public MainhandStats getHandheldStats()
    {
        return getStatProfile().mainhandStats();
    }



}
