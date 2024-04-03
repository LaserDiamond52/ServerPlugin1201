package net.laserdiamond.ventureplugin.enchants.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Config.EnchantConfig;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.abilities.AbilityListener;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.stats.Components.PlayerStatKeys.PlayerEnchantStatKeys;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.util.Config.MiscConfig;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnchantListeners implements Listener, AbilityListener, AbilityCasting.RunnableAbility, AbilityCasting.LeftClickAbility {

    private final VenturePlugin plugin;
    private final StatProfileManager statProfileManager;
    private final MiscConfig enchantConfig;
    private final Map<UUID, Integer> ThunderStrikeProfiles = new HashMap<>();

    public EnchantListeners(VenturePlugin plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
        enchantConfig = plugin.getEnchantConfig();
    }

    @EventHandler
    public void protectionEnchantmentArmor(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        double protectionArmor = enchantConfig.getDouble("protectionArmor");
        double fireProtectionArmor = enchantConfig.getDouble("fireProtectionArmor");
        double explosionProtectionArmor = enchantConfig.getDouble("explosionProtectionArmor");
        double blastProtectionArmor = enchantConfig.getDouble("blastProtectionArmor");
        double magicProtectionArmor = enchantConfig.getDouble("magicProtectionArmor");
        double toughnessArmor = enchantConfig.getDouble("toughnessArmor");

        double manaPool = enchantConfig.getDouble("manaPool");
        double strength = enchantConfig.getDouble("strength");
        double arcane = enchantConfig.getDouble("arcane");
        double archer = enchantConfig.getDouble("archer");

        double speed = enchantConfig.getDouble("speed");
        double minerFortune = enchantConfig.getDouble("minerFortune");
        double foragerFortune = enchantConfig.getDouble("foragerFortune");
        double fishermanFortune = enchantConfig.getDouble("fishermanFortune");

        StatProfile statProfile = statProfileManager.getStatProfile(player.getUniqueId());

        if (newMeta != null) {

            addProtection(statProfile, newMeta, protectionArmor, fireProtectionArmor, explosionProtectionArmor, blastProtectionArmor, magicProtectionArmor, toughnessArmor);
            addManaPool(statProfile, newMeta, manaPool);
            addDamage(statProfile, newMeta, strength, arcane, archer);
            addSpeed(statProfile, newMeta, speed);
            addFortunes(statProfile, newMeta, minerFortune, foragerFortune, fishermanFortune);
        }

        if (oldMeta != null) {

            removeProtection(statProfile, oldMeta, protectionArmor, fireProtectionArmor, explosionProtectionArmor, blastProtectionArmor, magicProtectionArmor, toughnessArmor);
            removeManaPool(statProfile, oldMeta, manaPool);
            removeDamage(statProfile, oldMeta, strength, arcane, archer);
            removeSpeed(statProfile, oldMeta, speed);
            removeFortunes(statProfile, oldMeta, minerFortune, foragerFortune, fishermanFortune);
        }
    }

    @EventHandler
    public void setThunderStrikeProfile(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        ThunderStrikeProfiles.put(player.getUniqueId(), 0);
    }

    @EventHandler
    public void swordEnchantments(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player player) {

            UUID playerUUID = player.getUniqueId();
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            ItemMeta mainHandMeta = mainHand.getItemMeta();

            int venomDuration = enchantConfig.getInt("venomousAspectPoisonDurationPerLvl");
            int venomLvl = enchantConfig.getInt("venomousAspectPoisonLvl") - 1;

            int decayDuration = enchantConfig.getInt("decayWitherDurationPerLvl");
            int decayWitherLvl = enchantConfig.getInt("decayWitherLvl") - 1;

            int thunderStrikeHitActivate = enchantConfig.getInt("thunderStrikeHits");
            double thunderStrikeDamageIncrease = enchantConfig.getDouble("thunderStrikeDamagePerLevel");
            double eventDamage = event.getDamage();

            int glowDuration = enchantConfig.getInt("glowDurationPerLvl");

            if (mainHandMeta != null) {
                if (event.getEntity() instanceof LivingEntity livingEntity) {
                    // TODO: Living Entity is hit

                    if (mainHandMeta.hasEnchant(VentureEnchants.VENOMOUS_ASPECT)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(VentureEnchants.VENOMOUS_ASPECT);
                        int finalVenomDuration = venomDuration * enchantLvl;

                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, finalVenomDuration * 20, venomLvl));
                    }
                    if (mainHandMeta.hasEnchant(VentureEnchants.DECAY)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(VentureEnchants.DECAY);
                        int finalDecayDuration = decayDuration * enchantLvl;

                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, finalDecayDuration, decayWitherLvl));
                    }
                    if (mainHandMeta.hasEnchant(VentureEnchants.THUNDER_STRIKE)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(VentureEnchants.THUNDER_STRIKE);
                        double finalDamageIncrease = 1 + thunderStrikeDamageIncrease * enchantLvl;
                        double finalDamage = eventDamage * finalDamageIncrease;

                        Integer thunderStrikeHits = ThunderStrikeProfiles.get(playerUUID);
                        ThunderStrikeProfiles.put(playerUUID, thunderStrikeHits + 1);
                        if (thunderStrikeHits > thunderStrikeHitActivate) {
                            event.setDamage(finalDamage);
                            livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
                            ThunderStrikeProfiles.put(playerUUID, 0);
                        }
                    }
                    if (mainHandMeta.hasEnchant(VentureEnchants.GLOW)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(VentureEnchants.GLOW);
                        int finalGlowDuration = glowDuration * enchantLvl;

                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, finalGlowDuration, 0));
                    }

                }

                /*
                else if (event.getEntity() instanceof Player damagedPlayer) {
                    // TODO: Player is hit

                    Stats stats = statProfileManager.getStatProfile(damagedPlayer.getUniqueId()).getStats();
                    double armor = stats.getArmor();
                    double fire_armor = stats.getFire_armor();
                    double explosion_armor = stats.getExplosion_armor();
                    double projectile_armor = stats.getProjectile_armor();
                    double magic_armor = stats.getMagic_armor();
                    double toughness = stats.getToughness();



                }

                 */
            }
        }
    }

    private void addProtection(StatProfile statProfile, ItemMeta itemMeta, Double armor, Double fire_armor, Double explosion_armor, Double projectile_armor, Double magic_armor, Double toughness) {

        DefenseStats defenseStats = statProfile.defenseStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        double currentArmor = defenseStats.getDefense();
        double currentFireArmor = defenseStats.getFireDefense();
        double currentExplosionArmor = defenseStats.getExplosionDefense();
        double currentProjectileArmor = defenseStats.getProjectileDefense();
        double currentMagicArmor = defenseStats.getMagicDefense();
        double currentToughness = defenseStats.getToughness();

        double currentEnchantArmor = enchantStats.getDefense();
        double currentEnchantFireArmor = enchantStats.getFireDefense();
        double currentEnchantExplosionArmor = enchantStats.getExplosionDefense();
        double currentEnchantProjectileArmor = enchantStats.getProjectileDefense();
        double currentEnchantMagicArmor = enchantStats.getMagicDefense();
        double currentEnchantToughness = enchantStats.getToughness();

        if (itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            double finalProt = armor * protectionLvl;
            PlayerEnchantStatKeys.add(statProfile, PlayerEnchantStatKeys.DEFENSE, finalProt);
            //defenseStats.setDefense(currentArmor + finalProt);
            //enchantStats.setDefense(currentEnchantArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FIRE);
            double finalProt = fire_armor * protectionLvl;
            PlayerEnchantStatKeys.add(statProfile, PlayerEnchantStatKeys.FIRE_DEFENSE, finalProt);

            //defenseStats.setFireDefense(currentFireArmor + finalProt);
            //enchantStats.setFireDefense(currentEnchantFireArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
            double finalProt = explosion_armor * protectionLvl;
            PlayerEnchantStatKeys.add(statProfile, PlayerEnchantStatKeys.EXPLOSION_DEFENSE, finalProt);

            //defenseStats.setExplosionDefense(currentExplosionArmor + finalProt);
            //enchantStats.setExplosionDefense(currentEnchantExplosionArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            double finalProt = projectile_armor * protectionLvl;
            PlayerEnchantStatKeys.add(statProfile, PlayerEnchantStatKeys.PROJECTILE_DEFENSE, finalProt);

            //defenseStats.setProjectileDefense(currentProjectileArmor + finalProt);
            //enchantStats.setProjectileDefense(currentEnchantProjectileArmor + finalProt);
        }
        if (itemMeta.hasEnchant(VentureEnchants.MAGIC_PROTECTION)) {
            int protectionLvl = itemMeta.getEnchantLevel(VentureEnchants.MAGIC_PROTECTION);
            double finalProt = magic_armor * protectionLvl;
            PlayerEnchantStatKeys.add(statProfile, PlayerEnchantStatKeys.MAGIC_DEFENSE, finalProt);

            //defenseStats.setMagicDefense(currentMagicArmor + finalProt);
            //enchantStats.setMagicDefense(currentEnchantMagicArmor + finalProt);
        }
        if (itemMeta.hasEnchant(VentureEnchants.TOUGHNESS)) {
            int toughnessLvl = itemMeta.getEnchantLevel(VentureEnchants.TOUGHNESS);
            double finalToughness = toughness * toughnessLvl;
            PlayerEnchantStatKeys.add(statProfile, PlayerEnchantStatKeys.TOUGHNESS, finalToughness);

            //defenseStats.setToughness(currentToughness + finalToughness);
            //enchantStats.setToughness(currentEnchantToughness + finalToughness);
        }

    }

    private void removeProtection(StatProfile statProfile, ItemMeta itemMeta, Double armor, Double fire_armor, Double explosion_armor, Double projectile_armor, Double magic_armor, Double toughness) {

        DefenseStats defenseStats = statProfile.defenseStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        double currentArmor = defenseStats.getDefense();
        double currentFireArmor = defenseStats.getFireDefense();
        double currentExplosionArmor = defenseStats.getExplosionDefense();
        double currentProjectileArmor = defenseStats.getProjectileDefense();
        double currentMagicArmor = defenseStats.getMagicDefense();
        double currentToughness = defenseStats.getToughness();

        double currentEnchantArmor = enchantStats.getDefense();
        double currentEnchantFireArmor = enchantStats.getFireDefense();
        double currentEnchantExplosionArmor = enchantStats.getExplosionDefense();
        double currentEnchantProjectileArmor = enchantStats.getProjectileDefense();
        double currentEnchantMagicArmor = enchantStats.getMagicDefense();
        double currentEnchantToughness = enchantStats.getToughness();

        if (itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            double finalProt = armor * protectionLvl;
            //defenseStats.setDefense(currentArmor - finalProt);
            //enchantStats.setDefense(currentEnchantArmor - finalProt);
            PlayerEnchantStatKeys.remove(statProfile, PlayerEnchantStatKeys.DEFENSE, finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FIRE);
            double finalProt = fire_armor * protectionLvl;
            PlayerEnchantStatKeys.remove(statProfile, PlayerEnchantStatKeys.FIRE_DEFENSE, finalProt);
            //defenseStats.setFireDefense(currentFireArmor - finalProt);
            //enchantStats.setFireDefense(currentEnchantFireArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
            double finalProt = explosion_armor * protectionLvl;
            PlayerEnchantStatKeys.remove(statProfile, PlayerEnchantStatKeys.EXPLOSION_DEFENSE, finalProt);
            //defenseStats.setExplosionDefense(currentExplosionArmor - finalProt);
            //enchantStats.setExplosionDefense(currentEnchantExplosionArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            double finalProt = projectile_armor * protectionLvl;
            PlayerEnchantStatKeys.remove(statProfile, PlayerEnchantStatKeys.PROJECTILE_DEFENSE, finalProt);
            //defenseStats.setProjectileDefense(currentProjectileArmor - finalProt);
            //enchantStats.setProjectileDefense(currentEnchantProjectileArmor - finalProt);
        }
        if (itemMeta.hasEnchant(VentureEnchants.MAGIC_PROTECTION)) {
            int protectionLvl = itemMeta.getEnchantLevel(VentureEnchants.MAGIC_PROTECTION);
            double finalProt = magic_armor * protectionLvl;
            PlayerEnchantStatKeys.remove(statProfile, PlayerEnchantStatKeys.MAGIC_DEFENSE, finalProt);
            //defenseStats.setMagicDefense(currentMagicArmor - finalProt);
            //enchantStats.setMagicDefense(currentEnchantMagicArmor - finalProt);
        }
        if (itemMeta.hasEnchant(VentureEnchants.TOUGHNESS)) {
            int toughnessLvl = itemMeta.getEnchantLevel(VentureEnchants.TOUGHNESS);
            double finalToughness = toughness * toughnessLvl;
            PlayerEnchantStatKeys.remove(statProfile, PlayerEnchantStatKeys.TOUGHNESS, finalToughness);
            //defenseStats.setToughness(currentToughness - finalToughness);
            //enchantStats.setToughness(currentEnchantToughness - finalToughness);
        }

    }

    private void addManaPool(StatProfile statProfile, ItemMeta itemMeta, Double mana) {

        Stats stats = statProfile.stats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentMaxMana = stats.getMaxMana();
        Double currentEnchantMana = enchantStats.getMana();

        if (itemMeta.hasEnchant(VentureEnchants.MANA_POOL)) {
            int manaLvl = itemMeta.getEnchantLevel(VentureEnchants.MANA_POOL);
            Double finalMana = mana * manaLvl;
            stats.setMaxMana(currentMaxMana + finalMana);
            enchantStats.setMana(currentEnchantMana + finalMana);
        }
    }

    private void removeManaPool(StatProfile statProfile, ItemMeta itemMeta, Double mana) {

        Stats stats = statProfile.stats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentMaxMana = stats.getMaxMana();
        Double currentEnchantMana = enchantStats.getMana();

        if (itemMeta.hasEnchant(VentureEnchants.MANA_POOL)) {
            int manaLvl = itemMeta.getEnchantLevel(VentureEnchants.MANA_POOL);
            Double finalMana = mana * manaLvl;
            stats.setMaxMana(currentMaxMana - finalMana);
            enchantStats.setMana(currentEnchantMana - finalMana);
        }
    }

    private void addDamage(StatProfile statProfile, ItemMeta itemMeta, Double strength, Double arcane, Double archer) {

        DamageStats damageStats = statProfile.damageStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentBaseMelee = damageStats.getBaseMelee();
        Double currentBaseMagic = damageStats.getBaseMagic();
        Double currentBaseRange = damageStats.getBaseRange();

        Double currentEnchantMelee = enchantStats.getBaseMelee();
        Double currentEnchantMagic = enchantStats.getBaseMagic();
        Double currentEnchantRange = enchantStats.getBaseRange();

        if (itemMeta.hasEnchant(VentureEnchants.MELEE_DAMAGE)) {
            int meleeLvl = itemMeta.getEnchantLevel(VentureEnchants.MELEE_DAMAGE);
            Double finalStrength = meleeLvl * strength;
            damageStats.setbMeleeDmg(currentBaseMelee + finalStrength);
            enchantStats.setBaseMelee(currentEnchantMelee + finalStrength);
        }
        if (itemMeta.hasEnchant(VentureEnchants.MAGIC_DAMAGE)) {
            int magicLvl = itemMeta.getEnchantLevel(VentureEnchants.MAGIC_DAMAGE);
            Double finalMagic = magicLvl * arcane;
            damageStats.setbMagicDmg(currentBaseMagic + finalMagic);
            enchantStats.setBaseMagic(currentEnchantMagic + finalMagic);
        }
        if (itemMeta.hasEnchant(VentureEnchants.RANGE_DAMAGE)) {
            int rangeLvl = itemMeta.getEnchantLevel(VentureEnchants.RANGE_DAMAGE);
            Double finalRange = rangeLvl * archer;
            damageStats.setbRangeDmg(currentBaseRange + finalRange);
            enchantStats.setBaseRange(currentEnchantRange + finalRange);
        }
    }

    private void removeDamage(StatProfile statProfile, ItemMeta itemMeta, Double strength, Double arcane, Double archer) {

        DamageStats damageStats = statProfile.damageStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentBaseMelee = damageStats.getBaseMelee();
        Double currentBaseMagic = damageStats.getBaseMagic();
        Double currentBaseRange = damageStats.getBaseRange();

        Double currentEnchantMelee = enchantStats.getBaseMelee();
        Double currentEnchantMagic = enchantStats.getBaseMagic();
        Double currentEnchantRange = enchantStats.getBaseRange();

        if (itemMeta.hasEnchant(VentureEnchants.MELEE_DAMAGE)) {
            int meleeLvl = itemMeta.getEnchantLevel(VentureEnchants.MELEE_DAMAGE);
            Double finalStrength = meleeLvl * strength;
            damageStats.setbMeleeDmg(currentBaseMelee - finalStrength);
            enchantStats.setBaseMelee(currentEnchantMelee - finalStrength);
        }
        if (itemMeta.hasEnchant(VentureEnchants.MAGIC_DAMAGE)) {
            int magicLvl = itemMeta.getEnchantLevel(VentureEnchants.MAGIC_DAMAGE);
            Double finalMagic = magicLvl * arcane;
            damageStats.setbMagicDmg(currentBaseMagic - finalMagic);
            enchantStats.setBaseMagic(currentEnchantMagic - finalMagic);
        }
        if (itemMeta.hasEnchant(VentureEnchants.RANGE_DAMAGE)) {
            int rangeLvl = itemMeta.getEnchantLevel(VentureEnchants.RANGE_DAMAGE);
            Double finalRange = rangeLvl * archer;
            damageStats.setbRangeDmg(currentBaseRange - finalRange);
            enchantStats.setBaseRange(currentEnchantRange - finalRange);
        }
    }

    private void addSpeed(StatProfile statProfile, ItemMeta itemMeta, Double speed)
    {
        Stats stats = statProfile.stats();

        if (itemMeta.hasEnchant(VentureEnchants.SPEED))
        {
            stats.setSpeed(stats.getSpeed() + speed);
        }
    }

    private void removeSpeed(StatProfile statProfile, ItemMeta itemMeta, Double speed)
    {
        Stats stats = statProfile.stats();

        if (itemMeta.hasEnchant(VentureEnchants.SPEED))
        {
            stats.setSpeed(stats.getSpeed() - speed);
        }
    }

    private void addFortunes(StatProfile statProfile, ItemMeta itemMeta, Double minerFortune, Double foragerFortune, Double fishermanFortune)
    {
        LootStats lootStats = statProfile.lootStats();

        if (itemMeta.hasEnchant(VentureEnchants.ARMOR_MINING_FORTUNE))
        {
            lootStats.setBonusOreLoot(lootStats.getBonusOreLoot() + minerFortune);
        }
        if (itemMeta.hasEnchant(VentureEnchants.ARMOR_FORAGING_FORTUNE))
        {
            lootStats.setBonusWoodLoot(lootStats.getBonusWoodLoot() + foragerFortune);
        }
        if (itemMeta.hasEnchant(VentureEnchants.ARMOR_FISHING_FORTUNE))
        {
            lootStats.setFishingLoot(lootStats.getFishingLoot() + fishermanFortune);
        }
    }
    private void removeFortunes(StatProfile statProfile, ItemMeta itemMeta, Double minerFortune, Double foragerFortune, Double fishermanFortune)
    {
        LootStats lootStats = statProfile.lootStats();

        if (itemMeta.hasEnchant(VentureEnchants.ARMOR_MINING_FORTUNE))
        {
            lootStats.setBonusOreLoot(lootStats.getBonusOreLoot() - minerFortune);
        }
        if (itemMeta.hasEnchant(VentureEnchants.ARMOR_FORAGING_FORTUNE))
        {
            lootStats.setBonusWoodLoot(lootStats.getBonusWoodLoot() - foragerFortune);
        }
        if (itemMeta.hasEnchant(VentureEnchants.ARMOR_FISHING_FORTUNE))
        {
            lootStats.setFishingLoot(lootStats.getFishingLoot() - fishermanFortune);
        }
    }

    /**
     * SpellCastHandler that grants enchantments with passive abilities their ability
     * @param player The player that is casting the ability
     */
    @AbilityHandler(abilityCastType = AbilityCastType.RUNNABLE)
    @Override
    public void onActivate(Player player)
    {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack helmet = playerInventory.getHelmet();
        if (helmet != null)
        {
            ItemMeta helmetMeta = helmet.getItemMeta();
            if (helmetMeta != null && helmetMeta.hasEnchants())
            {
                if (helmetMeta.hasEnchant(VentureEnchants.NIGHT_VISION))
                {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 0));
                }
            }
        }
    }

    /**
     * AbilityHandler that grants enchantments with left-click abilities their abilities
     * @param event The event that is being passed through
     */
    @AbilityHandler(abilityCastType = AbilityCastType.LEFT_CLICK)
    @Override
    public void onLeftClickCast(PlayerInteractEvent event)
    {

    }
}
