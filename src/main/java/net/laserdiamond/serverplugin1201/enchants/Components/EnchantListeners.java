package net.laserdiamond.serverplugin1201.enchants.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Config.EnchantConfig;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnchantListeners implements Listener {

    private ServerPlugin1201 plugin;
    private StatProfileManager statProfileManager;
    private EnchantConfig enchantConfig;
    private final Map<UUID, Integer> ThunderStrikeProfiles = new HashMap<>();

    public EnchantListeners(ServerPlugin1201 plugin) {
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

        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).getStats();
        EnchantStats enchantStats = statProfileManager.getStatProfile(player.getUniqueId()).getEnchantStats();

        if (newMeta != null) {

            addProtection(stats, enchantStats, newMeta, protectionArmor, fireProtectionArmor, explosionProtectionArmor, blastProtectionArmor, magicProtectionArmor, toughnessArmor);
            addManaPool(stats, enchantStats, newMeta, manaPool);
            addDamage(stats, enchantStats, newMeta, strength, arcane, archer);

        }

        if (oldMeta != null) {

            removeProtection(stats, enchantStats, oldMeta, protectionArmor, fireProtectionArmor, explosionProtectionArmor, blastProtectionArmor, magicProtectionArmor, toughnessArmor);
            removeManaPool(stats, enchantStats, oldMeta, manaPool);
            removeDamage(stats, enchantStats, oldMeta, strength, arcane, archer);

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

                    if (mainHandMeta.hasEnchant(EnchantsClass.VENOMOUS_ASPECT)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(EnchantsClass.VENOMOUS_ASPECT);
                        int finalVenomDuration = venomDuration * enchantLvl;

                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, finalVenomDuration * 20, venomLvl));
                    }
                    if (mainHandMeta.hasEnchant(EnchantsClass.DECAY)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(EnchantsClass.DECAY);
                        int finalDecayDuration = decayDuration * enchantLvl;

                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, finalDecayDuration, decayWitherLvl));
                    }
                    if (mainHandMeta.hasEnchant(EnchantsClass.THUNDER_STRIKE)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(EnchantsClass.THUNDER_STRIKE);
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
                    if (mainHandMeta.hasEnchant(EnchantsClass.GLOW)) {
                        int enchantLvl = mainHandMeta.getEnchantLevel(EnchantsClass.GLOW);
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

    private void addProtection(Stats stats, EnchantStats enchantStats, ItemMeta itemMeta, Double armor, Double fire_armor, Double explosion_armor, Double projectile_armor, Double magic_armor, Double toughness) {

        Double currentArmor = stats.getDefense();
        Double currentFireArmor = stats.getFire_defense();
        Double currentExplosionArmor = stats.getExplosion_defense();
        Double currentProjectileArmor = stats.getProjectile_defense();
        Double currentMagicArmor = stats.getMagic_defense();
        Double currentToughness = stats.getToughness();

        Double currentEnchantArmor = enchantStats.getEnchant_armor();
        Double currentEnchantFireArmor = enchantStats.getEnchant_fire_armor();
        Double currentEnchantExplosionArmor = enchantStats.getEnchant_explosion_armor();
        Double currentEnchantProjectileArmor = enchantStats.getEnchant_projectile_armor();
        Double currentEnchantMagicArmor = enchantStats.getEnchant_magic_armor();
        Double currentEnchantToughness = enchantStats.getEnchant_toughness();

        if (itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            Double finalProt = armor * protectionLvl;
            stats.setDefense(currentArmor + finalProt);
            enchantStats.setEnchant_armor(currentEnchantArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FIRE);
            Double finalProt = fire_armor * protectionLvl;
            stats.setFire_defense(currentFireArmor + finalProt);
            enchantStats.setEnchant_fire_armor(currentEnchantFireArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
            Double finalProt = explosion_armor * protectionLvl;
            stats.setExplosion_defense(currentExplosionArmor + finalProt);
            enchantStats.setEnchant_explosion_armor(currentEnchantExplosionArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            Double finalProt = projectile_armor * protectionLvl;
            stats.setProjectile_defense(currentProjectileArmor + finalProt);
            enchantStats.setEnchant_projectile_armor(currentEnchantProjectileArmor + finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_PROTECTION)) {
            int protectionLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_PROTECTION);
            Double finalProt = magic_armor * protectionLvl;
            stats.setMagic_defense(currentMagicArmor + finalProt);
            enchantStats.setEnchant_magic_armor(currentEnchantMagicArmor + finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.TOUGHNESS)) {
            int toughnessLvl = itemMeta.getEnchantLevel(EnchantsClass.TOUGHNESS);
            Double finalToughness = toughness * toughnessLvl;
            stats.setToughness(currentToughness + finalToughness);
            enchantStats.setEnchant_toughness(currentEnchantToughness + finalToughness);
        }

    }

    private void removeProtection(Stats stats, EnchantStats enchantStats, ItemMeta itemMeta, Double armor, Double fire_armor, Double explosion_armor, Double projectile_armor, Double magic_armor, Double toughness) {

        Double currentArmor = stats.getDefense();
        Double currentFireArmor = stats.getFire_defense();
        Double currentExplosionArmor = stats.getExplosion_defense();
        Double currentProjectileArmor = stats.getProjectile_defense();
        Double currentMagicArmor = stats.getMagic_defense();
        Double currentToughness = stats.getToughness();

        Double currentEnchantArmor = enchantStats.getEnchant_armor();
        Double currentEnchantFireArmor = enchantStats.getEnchant_fire_armor();
        Double currentEnchantExplosionArmor = enchantStats.getEnchant_explosion_armor();
        Double currentEnchantProjectileArmor = enchantStats.getEnchant_projectile_armor();
        Double currentEnchantMagicArmor = enchantStats.getEnchant_magic_armor();
        Double currentEnchantToughness = enchantStats.getEnchant_toughness();

        if (itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            Double finalProt = armor * protectionLvl;
            stats.setDefense(currentArmor - finalProt);
            enchantStats.setEnchant_armor(currentEnchantArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FIRE);
            Double finalProt = fire_armor * protectionLvl;
            stats.setFire_defense(currentFireArmor - finalProt);
            enchantStats.setEnchant_fire_armor(currentEnchantFireArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
            Double finalProt = explosion_armor * protectionLvl;
            stats.setExplosion_defense(currentExplosionArmor - finalProt);
            enchantStats.setEnchant_explosion_armor(currentEnchantExplosionArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            Double finalProt = projectile_armor * protectionLvl;
            stats.setProjectile_defense(currentProjectileArmor - finalProt);
            enchantStats.setEnchant_projectile_armor(currentEnchantProjectileArmor - finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_PROTECTION)) {
            int protectionLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_PROTECTION);
            Double finalProt = magic_armor * protectionLvl;
            stats.setMagic_defense(currentMagicArmor - finalProt);
            enchantStats.setEnchant_magic_armor(currentEnchantMagicArmor - finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.TOUGHNESS)) {
            int toughnessLvl = itemMeta.getEnchantLevel(EnchantsClass.TOUGHNESS);
            Double finalToughness = toughness * toughnessLvl;
            stats.setToughness(currentToughness - finalToughness);
            enchantStats.setEnchant_toughness(currentEnchantToughness - finalToughness);
        }

    }

    private void addManaPool(Stats stats, EnchantStats enchantStats, ItemMeta itemMeta, Double mana) {

        Double currentMaxMana = stats.getMaxMana();
        Double currentEnchantMana = enchantStats.getEnchant_mana();

        if (itemMeta.hasEnchant(EnchantsClass.MANA_POOL)) {
            int manaLvl = itemMeta.getEnchantLevel(EnchantsClass.MANA_POOL);
            Double finalMana = mana * manaLvl;
            stats.setMaxMana(currentMaxMana + finalMana);
            enchantStats.setEnchant_mana(currentEnchantMana + finalMana);
        }
    }

    private void removeManaPool(Stats stats, EnchantStats enchantStats, ItemMeta itemMeta, Double mana) {

        Double currentMaxMana = stats.getMaxMana();
        Double currentEnchantMana = enchantStats.getEnchant_mana();

        if (itemMeta.hasEnchant(EnchantsClass.MANA_POOL)) {
            int manaLvl = itemMeta.getEnchantLevel(EnchantsClass.MANA_POOL);
            Double finalMana = mana * manaLvl;
            stats.setMaxMana(currentMaxMana - finalMana);
            enchantStats.setEnchant_mana(currentEnchantMana - finalMana);
        }
    }

    private void addDamage(Stats stats, EnchantStats enchantStats, ItemMeta itemMeta, Double strength, Double arcane, Double archer) {

        Double currentBaseMelee = stats.getBaseMeleeDamage();
        Double currentBaseMagic = stats.getBaseMagicDamage();
        Double currentBaseRange = stats.getBaseRangeDamage();

        Double currentEnchantMelee = enchantStats.getEnchant_base_melee();
        Double currentEnchantMagic = enchantStats.getEnchant_base_magic();
        Double currentEnchantRange = enchantStats.getEnchant_base_range();

        if (itemMeta.hasEnchant(EnchantsClass.MELEE_DAMAGE)) {
            int meleeLvl = itemMeta.getEnchantLevel(EnchantsClass.MELEE_DAMAGE);
            Double finalStrength = meleeLvl * strength;
            stats.setBaseMeleeDamage(currentBaseMelee + finalStrength);
            enchantStats.setEnchant_base_melee(currentEnchantMelee + finalStrength);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_DAMAGE)) {
            int magicLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_DAMAGE);
            Double finalMagic = magicLvl * arcane;
            stats.setBaseMagicDamage(currentBaseMagic + finalMagic);
            enchantStats.setEnchant_base_magic(currentEnchantMagic + finalMagic);
        }
        if (itemMeta.hasEnchant(EnchantsClass.RANGE_DAMAGE)) {
            int rangeLvl = itemMeta.getEnchantLevel(EnchantsClass.RANGE_DAMAGE);
            Double finalRange = rangeLvl * archer;
            stats.setBaseRangeDamage(currentBaseRange + finalRange);
            enchantStats.setEnchant_base_range(currentEnchantRange + finalRange);
        }
    }

    private void removeDamage(Stats stats, EnchantStats enchantStats, ItemMeta itemMeta, Double strength, Double arcane, Double archer) {

        Double currentBaseMelee = stats.getBaseMeleeDamage();
        Double currentBaseMagic = stats.getBaseMagicDamage();
        Double currentBaseRange = stats.getBaseRangeDamage();

        Double currentEnchantMelee = enchantStats.getEnchant_base_melee();
        Double currentEnchantMagic = enchantStats.getEnchant_base_magic();
        Double currentEnchantRange = enchantStats.getEnchant_base_range();

        if (itemMeta.hasEnchant(EnchantsClass.MELEE_DAMAGE)) {
            int meleeLvl = itemMeta.getEnchantLevel(EnchantsClass.MELEE_DAMAGE);
            Double finalStrength = meleeLvl * strength;
            stats.setBaseMeleeDamage(currentBaseMelee - finalStrength);
            enchantStats.setEnchant_base_melee(currentEnchantMelee - finalStrength);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_DAMAGE)) {
            int magicLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_DAMAGE);
            Double finalMagic = magicLvl * arcane;
            stats.setBaseMagicDamage(currentBaseMagic - finalMagic);
            enchantStats.setEnchant_base_magic(currentEnchantMagic - finalMagic);
        }
        if (itemMeta.hasEnchant(EnchantsClass.RANGE_DAMAGE)) {
            int rangeLvl = itemMeta.getEnchantLevel(EnchantsClass.RANGE_DAMAGE);
            Double finalRange = rangeLvl * archer;
            stats.setBaseRangeDamage(currentBaseRange - finalRange);
            enchantStats.setEnchant_base_range(currentEnchantRange - finalRange);
        }
    }
}
