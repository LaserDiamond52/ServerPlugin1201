package net.laserdiamond.serverplugin1201.enchants.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Config.EnchantConfig;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastHandler;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastListener;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastType;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCasting;
import net.laserdiamond.serverplugin1201.stats.Components.*;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
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

public class EnchantListeners implements Listener, SpellCastListener, SpellCasting.RunnableSpell, SpellCasting.LeftClickSpell {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;
    private final EnchantConfig enchantConfig;
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

    private void addProtection(StatProfile statProfile, ItemMeta itemMeta, Double armor, Double fire_armor, Double explosion_armor, Double projectile_armor, Double magic_armor, Double toughness) {

        DefenseStats defenseStats = statProfile.defenseStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentArmor = defenseStats.getDefense();
        Double currentFireArmor = defenseStats.getFireDefense();
        Double currentExplosionArmor = defenseStats.getExplosionDefense();
        Double currentProjectileArmor = defenseStats.getProjectileDefense();
        Double currentMagicArmor = defenseStats.getMagicDefense();
        Double currentToughness = defenseStats.getToughness();

        Double currentEnchantArmor = enchantStats.getDefense();
        Double currentEnchantFireArmor = enchantStats.getFireDefense();
        Double currentEnchantExplosionArmor = enchantStats.getExplosionDefense();
        Double currentEnchantProjectileArmor = enchantStats.getProjectileDefense();
        Double currentEnchantMagicArmor = enchantStats.getMagicDefense();
        Double currentEnchantToughness = enchantStats.getToughness();

        if (itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            Double finalProt = armor * protectionLvl;
            defenseStats.setDefense(currentArmor + finalProt);
            enchantStats.setDefense(currentEnchantArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FIRE);
            Double finalProt = fire_armor * protectionLvl;
            defenseStats.setFireDefense(currentFireArmor + finalProt);
            enchantStats.setFireDefense(currentEnchantFireArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
            Double finalProt = explosion_armor * protectionLvl;
            defenseStats.setExplosionDefense(currentExplosionArmor + finalProt);
            enchantStats.setExplosionDefense(currentEnchantExplosionArmor + finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            Double finalProt = projectile_armor * protectionLvl;
            defenseStats.setProjectileDefense(currentProjectileArmor + finalProt);
            enchantStats.setProjectileDefense(currentEnchantProjectileArmor + finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_PROTECTION)) {
            int protectionLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_PROTECTION);
            Double finalProt = magic_armor * protectionLvl;
            defenseStats.setMagicDefense(currentMagicArmor + finalProt);
            enchantStats.setMagicDefense(currentEnchantMagicArmor + finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.TOUGHNESS)) {
            int toughnessLvl = itemMeta.getEnchantLevel(EnchantsClass.TOUGHNESS);
            Double finalToughness = toughness * toughnessLvl;
            defenseStats.setToughness(currentToughness + finalToughness);
            enchantStats.setToughness(currentEnchantToughness + finalToughness);
        }

    }

    private void removeProtection(StatProfile statProfile, ItemMeta itemMeta, Double armor, Double fire_armor, Double explosion_armor, Double projectile_armor, Double magic_armor, Double toughness) {

        DefenseStats defenseStats = statProfile.defenseStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentArmor = defenseStats.getDefense();
        Double currentFireArmor = defenseStats.getFireDefense();
        Double currentExplosionArmor = defenseStats.getExplosionDefense();
        Double currentProjectileArmor = defenseStats.getProjectileDefense();
        Double currentMagicArmor = defenseStats.getMagicDefense();
        Double currentToughness = defenseStats.getToughness();

        Double currentEnchantArmor = enchantStats.getDefense();
        Double currentEnchantFireArmor = enchantStats.getFireDefense();
        Double currentEnchantExplosionArmor = enchantStats.getExplosionDefense();
        Double currentEnchantProjectileArmor = enchantStats.getProjectileDefense();
        Double currentEnchantMagicArmor = enchantStats.getMagicDefense();
        Double currentEnchantToughness = enchantStats.getToughness();

        if (itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            Double finalProt = armor * protectionLvl;
            defenseStats.setDefense(currentArmor - finalProt);
            enchantStats.setDefense(currentEnchantArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FIRE);
            Double finalProt = fire_armor * protectionLvl;
            defenseStats.setFireDefense(currentFireArmor - finalProt);
            enchantStats.setFireDefense(currentEnchantFireArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
            Double finalProt = explosion_armor * protectionLvl;
            defenseStats.setExplosionDefense(currentExplosionArmor - finalProt);
            enchantStats.setExplosionDefense(currentEnchantExplosionArmor - finalProt);
        }
        if (itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            int protectionLvl = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            Double finalProt = projectile_armor * protectionLvl;
            defenseStats.setProjectileDefense(currentProjectileArmor - finalProt);
            enchantStats.setProjectileDefense(currentEnchantProjectileArmor - finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_PROTECTION)) {
            int protectionLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_PROTECTION);
            Double finalProt = magic_armor * protectionLvl;
            defenseStats.setMagicDefense(currentMagicArmor - finalProt);
            enchantStats.setMagicDefense(currentEnchantMagicArmor - finalProt);
        }
        if (itemMeta.hasEnchant(EnchantsClass.TOUGHNESS)) {
            int toughnessLvl = itemMeta.getEnchantLevel(EnchantsClass.TOUGHNESS);
            Double finalToughness = toughness * toughnessLvl;
            defenseStats.setToughness(currentToughness - finalToughness);
            enchantStats.setToughness(currentEnchantToughness - finalToughness);
        }

    }

    private void addManaPool(StatProfile statProfile, ItemMeta itemMeta, Double mana) {

        Stats stats = statProfile.stats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentMaxMana = stats.getMaxMana();
        Double currentEnchantMana = enchantStats.getMana();

        if (itemMeta.hasEnchant(EnchantsClass.MANA_POOL)) {
            int manaLvl = itemMeta.getEnchantLevel(EnchantsClass.MANA_POOL);
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

        if (itemMeta.hasEnchant(EnchantsClass.MANA_POOL)) {
            int manaLvl = itemMeta.getEnchantLevel(EnchantsClass.MANA_POOL);
            Double finalMana = mana * manaLvl;
            stats.setMaxMana(currentMaxMana - finalMana);
            enchantStats.setMana(currentEnchantMana - finalMana);
        }
    }

    private void addDamage(StatProfile statProfile, ItemMeta itemMeta, Double strength, Double arcane, Double archer) {

        DamageStats damageStats = statProfile.damageStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentBaseMelee = damageStats.getbMeleeDmg();
        Double currentBaseMagic = damageStats.getbMagicDmg();
        Double currentBaseRange = damageStats.getbRangeDmg();

        Double currentEnchantMelee = enchantStats.getBaseMelee();
        Double currentEnchantMagic = enchantStats.getBaseMagic();
        Double currentEnchantRange = enchantStats.getBaseRange();

        if (itemMeta.hasEnchant(EnchantsClass.MELEE_DAMAGE)) {
            int meleeLvl = itemMeta.getEnchantLevel(EnchantsClass.MELEE_DAMAGE);
            Double finalStrength = meleeLvl * strength;
            damageStats.setbMeleeDmg(currentBaseMelee + finalStrength);
            enchantStats.setBaseMelee(currentEnchantMelee + finalStrength);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_DAMAGE)) {
            int magicLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_DAMAGE);
            Double finalMagic = magicLvl * arcane;
            damageStats.setbMagicDmg(currentBaseMagic + finalMagic);
            enchantStats.setBaseMagic(currentEnchantMagic + finalMagic);
        }
        if (itemMeta.hasEnchant(EnchantsClass.RANGE_DAMAGE)) {
            int rangeLvl = itemMeta.getEnchantLevel(EnchantsClass.RANGE_DAMAGE);
            Double finalRange = rangeLvl * archer;
            damageStats.setbRangeDmg(currentBaseRange + finalRange);
            enchantStats.setBaseRange(currentEnchantRange + finalRange);
        }
    }

    private void removeDamage(StatProfile statProfile, ItemMeta itemMeta, Double strength, Double arcane, Double archer) {

        DamageStats damageStats = statProfile.damageStats();
        EnchantStats enchantStats = statProfile.enchantStats();

        Double currentBaseMelee = damageStats.getbMeleeDmg();
        Double currentBaseMagic = damageStats.getbMagicDmg();
        Double currentBaseRange = damageStats.getbRangeDmg();

        Double currentEnchantMelee = enchantStats.getBaseMelee();
        Double currentEnchantMagic = enchantStats.getBaseMagic();
        Double currentEnchantRange = enchantStats.getBaseRange();

        if (itemMeta.hasEnchant(EnchantsClass.MELEE_DAMAGE)) {
            int meleeLvl = itemMeta.getEnchantLevel(EnchantsClass.MELEE_DAMAGE);
            Double finalStrength = meleeLvl * strength;
            damageStats.setbMeleeDmg(currentBaseMelee - finalStrength);
            enchantStats.setBaseMelee(currentEnchantMelee - finalStrength);
        }
        if (itemMeta.hasEnchant(EnchantsClass.MAGIC_DAMAGE)) {
            int magicLvl = itemMeta.getEnchantLevel(EnchantsClass.MAGIC_DAMAGE);
            Double finalMagic = magicLvl * arcane;
            damageStats.setbMagicDmg(currentBaseMagic - finalMagic);
            enchantStats.setBaseMagic(currentEnchantMagic - finalMagic);
        }
        if (itemMeta.hasEnchant(EnchantsClass.RANGE_DAMAGE)) {
            int rangeLvl = itemMeta.getEnchantLevel(EnchantsClass.RANGE_DAMAGE);
            Double finalRange = rangeLvl * archer;
            damageStats.setbRangeDmg(currentBaseRange - finalRange);
            enchantStats.setBaseRange(currentEnchantRange - finalRange);
        }
    }

    private void addSpeed(StatProfile statProfile, ItemMeta itemMeta, Double speed)
    {
        Stats stats = statProfile.stats();

        if (itemMeta.hasEnchant(EnchantsClass.SPEED))
        {
            stats.setSpeed(stats.getSpeed() + speed);
        }
    }

    private void removeSpeed(StatProfile statProfile, ItemMeta itemMeta, Double speed)
    {
        Stats stats = statProfile.stats();

        if (itemMeta.hasEnchant(EnchantsClass.SPEED))
        {
            stats.setSpeed(stats.getSpeed() - speed);
        }
    }

    private void addFortunes(StatProfile statProfile, ItemMeta itemMeta, Double minerFortune, Double foragerFortune, Double fishermanFortune)
    {
        LootStats lootStats = statProfile.lootStats();

        if (itemMeta.hasEnchant(EnchantsClass.ARMOR_MINING_FORTUNE))
        {
            lootStats.setBonusOreLoot(lootStats.getBonusOreLoot() + minerFortune);
        }
        if (itemMeta.hasEnchant(EnchantsClass.ARMOR_FORAGING_FORTUNE))
        {
            lootStats.setBonusWoodLoot(lootStats.getBonusWoodLoot() + foragerFortune);
        }
        if (itemMeta.hasEnchant(EnchantsClass.ARMOR_FISHING_FORTUNE))
        {
            lootStats.setFishingLoot(lootStats.getFishingLoot() + fishermanFortune);
        }
    }
    private void removeFortunes(StatProfile statProfile, ItemMeta itemMeta, Double minerFortune, Double foragerFortune, Double fishermanFortune)
    {
        LootStats lootStats = statProfile.lootStats();

        if (itemMeta.hasEnchant(EnchantsClass.ARMOR_MINING_FORTUNE))
        {
            lootStats.setBonusOreLoot(lootStats.getBonusOreLoot() - minerFortune);
        }
        if (itemMeta.hasEnchant(EnchantsClass.ARMOR_FORAGING_FORTUNE))
        {
            lootStats.setBonusWoodLoot(lootStats.getBonusWoodLoot() - foragerFortune);
        }
        if (itemMeta.hasEnchant(EnchantsClass.ARMOR_FISHING_FORTUNE))
        {
            lootStats.setFishingLoot(lootStats.getFishingLoot() - fishermanFortune);
        }
    }

    /**
     * SpellCastHandler that grants enchantments with passive abilities their ability
     * @param player The player that is casting the ability/spell
     * @param timer The time interval at which something should happen (max: 20 ticks)
     */
    @SpellCastHandler(spellCastType = SpellCastType.RUNNABLE)
    @Override
    public void onActivate(Player player, int timer)
    {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack helmet = playerInventory.getHelmet();
        if (helmet != null)
        {
            ItemMeta helmetMeta = helmet.getItemMeta();
            if (helmetMeta != null && helmetMeta.hasEnchants())
            {
                if (helmetMeta.hasEnchant(EnchantsClass.NIGH_VISION))
                {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 0));
                }
            }
        }
    }

    /**
     * SpellCastHandler that grants enchantments with left-click abilities their abilities
     * @param event The event that is being passed through
     */
    @SpellCastHandler(spellCastType = SpellCastType.LEFT_CLICK)
    @Override
    public void onLeftClickCast(PlayerInteractEvent event)
    {

    }
}
