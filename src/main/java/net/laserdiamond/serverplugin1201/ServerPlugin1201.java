package net.laserdiamond.serverplugin1201;

import net.laserdiamond.serverplugin1201.commands.Effects.EffectsCommand;
import net.laserdiamond.serverplugin1201.commands.Enchant.EnchantCommand;
import net.laserdiamond.serverplugin1201.commands.GiveItems.GiveItemsCommand;
import net.laserdiamond.serverplugin1201.commands.ViewProfiles.ViewStats;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantListeners;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantPlayerHeadHelmets;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.enchants.Config.EnchantConfig;
import net.laserdiamond.serverplugin1201.enchants.anvil.AnvilInvetoryGUI;
import net.laserdiamond.serverplugin1201.events.CancelInventoryMovementMenus;
import net.laserdiamond.serverplugin1201.events.Stats.DamageEvent;
import net.laserdiamond.serverplugin1201.events.Stats.DefenseEvent;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.NecrosisTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ParalyzeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.VulnerableTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.EffectEvents;
import net.laserdiamond.serverplugin1201.events.HUD.HUD;
import net.laserdiamond.serverplugin1201.events.Stats.ManaRegen;
import net.laserdiamond.serverplugin1201.events.PlayerJoinServer;
import net.laserdiamond.serverplugin1201.events.effects.Components.EffectTimer;
import net.laserdiamond.serverplugin1201.events.effects.Config.EffectProfileConfig;
import net.laserdiamond.serverplugin1201.events.effects.Managers.EffectManager;
import net.laserdiamond.serverplugin1201.items.armor.ArmorEquipStats;
import net.laserdiamond.serverplugin1201.items.armor.Blaze.Config.BlazeArmorConfig;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Components.EyeOfStormCooldown;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Components.StormLordArmorListeners;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Components.StormLordArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Config.StormLordArmorConfig;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Config.ArmorTrimConfig;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.DiamondArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.serverplugin1201.items.armor.Vanilla.Config.VanillaArmorConfig;
import net.laserdiamond.serverplugin1201.items.crafting.SmithingTable.SmithingTableCrafting;
import net.laserdiamond.serverplugin1201.items.management.ItemMappings;
import net.laserdiamond.serverplugin1201.stats.Config.BaseStatsConfig;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ServerPlugin1201 extends JavaPlugin {

    private static ServerPlugin1201 plugin;
    private StatProfileManager statProfileManager;
    private ArmorTrimMaterialStats armorTrimMaterialStats;
    private ArmorTrimPatternStats armorTrimPatternStats;
    private EffectManager effectManager;
    private EffectProfileConfig effectProfileConfig;
    private BukkitTask displayHUDTimer;
    private BukkitTask manaRegen;
    private BukkitTask effectTimer;

    private BaseStatsConfig baseStatsConfig;
    private EnchantConfig enchantConfig;
    private ArmorTrimConfig armorTrimConfig;
    private DiamondArmorManager diamondArmorManager;
    private NetheriteArmorManager netheriteArmorManager;
    private VanillaArmorConfig vanillaArmorConfig;
    private BlazeArmorConfig blazeArmorConfig;

    private StormLordArmorConfig stormLordArmorConfig;
    private StormLordArmorManager stormLordArmorManager;


    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling Plugin...");
        plugin = this;

        // Create Stat Profiles for players
        createConfigs();
        createItemConfigs();
        createManagers();
        createItemManagers();
        EnchantsClass.register();
        createTimers();
        setUpCooldowns();
        getServer().getPluginManager().registerEvents(new PlayerJoinServer(this), this);

        // Register timers
        createTasks();

        // Refresh Items
        getServer().getPluginManager().registerEvents(new ItemMappings(this),this);

        // Effect events
        getServer().getPluginManager().registerEvents(new EffectEvents(this),this);

        // Damage
        getServer().getPluginManager().registerEvents(new DamageEvent(this),this);

        // Armor stats
        getServer().getPluginManager().registerEvents(new ArmorEquipStats(this),this);
        getServer().getPluginManager().registerEvents(new DefenseEvent(this),this);
        registerStormArmorListeners();

        // Enchants
        getServer().getPluginManager().registerEvents(new EnchantListeners(this),this);
        getServer().getPluginManager().registerEvents(new EnchantPlayerHeadHelmets(this),this);

        // Cancel Menu Inventory Manipulation
        getServer().getPluginManager().registerEvents(new CancelInventoryMovementMenus(),this);

        // Smithing Table Inventory
        getServer().getPluginManager().registerEvents(new SmithingTableCrafting(this),this);
        //SmithingTableCrafting.init();

        // Anvil Inventory GUI
        getServer().getPluginManager().registerEvents(new AnvilInvetoryGUI(this),this);

        // Register Commands
        getCommand("plugineffect").setExecutor(new EffectsCommand(this));
        getCommand("plugingive").setExecutor(new GiveItemsCommand(this));
        getCommand("pluginenchant").setExecutor(new EnchantCommand());
        getCommand("stats").setExecutor(new ViewStats());

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
    }

    public static ServerPlugin1201 getInstance() {
        return plugin;
    }

    public StatProfileManager getStatProfileManager() {
        return statProfileManager;
    }
    public ArmorTrimMaterialStats getArmorTrimMaterialStats() {
        return armorTrimMaterialStats;
    }
    public ArmorTrimPatternStats getArmorTrimPatternStats() {
        return armorTrimPatternStats;
    }
    public EffectManager getEffectManager() {
        return effectManager;
    }
    public DiamondArmorManager getDiamondArmorManager() {
        return diamondArmorManager;
    }
    public NetheriteArmorManager getNetheriteArmorManager() {
        return netheriteArmorManager;
    }
    public StormLordArmorManager getStormArmorManager() {
        return stormLordArmorManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Disabling Plugin...");

        // REMEMBER TO SAVE PROFILES TO CONFIGS FIRST!!!!
        saveProfilesToConfigs();
        saveConfigs();

        // Cancel repeating tasks
        cancelTasks();

        // Unregister enchantments
        EnchantsClass.unregisterEnchantments();
    }

    public BaseStatsConfig getBaseStatsConfig() {
        return baseStatsConfig;
    }
    public EffectProfileConfig getEffectProfileConfig() {
        return effectProfileConfig;
    }
    public EnchantConfig getEnchantConfig() {
        return enchantConfig;
    }
    public ArmorTrimConfig getArmorTrimConfig() {
        return armorTrimConfig;
    }
    public VanillaArmorConfig getVanillaArmorConfig() {
        return vanillaArmorConfig;
    }
    public BlazeArmorConfig getBlazeArmorConfig() {
        return blazeArmorConfig;
    }
    public StormLordArmorConfig getStormLordArmorConfig() {
        return stormLordArmorConfig;
    }

    private void createConfigs() {
        baseStatsConfig = new BaseStatsConfig(this, "baseStats");
        baseStatsConfig.loadConfig();

        effectProfileConfig = new EffectProfileConfig(this, "effectDurations");
        effectProfileConfig.loadConfig();

        armorTrimConfig = new ArmorTrimConfig(this, "trims");
        armorTrimConfig.loadConfig();

        enchantConfig = new EnchantConfig(this, "enchants");
        enchantConfig.loadConfig();
    }

    private void createItemConfigs() {
        vanillaArmorConfig = new VanillaArmorConfig(this, "vanilla_armor");
        vanillaArmorConfig.loadConfig();

        blazeArmorConfig = new BlazeArmorConfig(this, "blaze");
        blazeArmorConfig.loadConfig();

        stormLordArmorConfig = new StormLordArmorConfig(this, "storm_lord");
        stormLordArmorConfig.loadConfig();
    }
    private void createManagers() {
        statProfileManager = new StatProfileManager(this);

        effectManager = new EffectManager(this);
        effectManager.loadProfilesFromConfig();
    }
    private void createItemManagers() {
        diamondArmorManager = new DiamondArmorManager(this);
        netheriteArmorManager = new NetheriteArmorManager(this);
        stormLordArmorManager = new StormLordArmorManager(this);
    }
    private void createTimers() {
        ManaFreezeTimer.setupTimer();
        NecrosisTimer.setupTimer();
        ParalyzeTimer.setupTimer();
        VulnerableTimer.setupTimer();
    }
    private void createTasks() {
        displayHUDTimer = new HUD(this).runTaskTimer(this, 0L, 1L);
        manaRegen = new ManaRegen(this).runTaskTimer(this, 0L, 20L);
        effectTimer = new EffectTimer(this).runTaskTimer(this, 0L, 20L);
    }
    private void setUpCooldowns() {
        EyeOfStormCooldown.setUpCooldown();
    }

    private void saveProfilesToConfigs() {
        effectManager.saveProfilesToConfig();
    }
    private void saveConfigs() {
        effectProfileConfig.saveConfig();
    }

    private void cancelTasks() {
        if (displayHUDTimer != null && !displayHUDTimer.isCancelled()) {
            displayHUDTimer.cancel();
        }
        if (manaRegen != null && !manaRegen.isCancelled()) {
            manaRegen.cancel();
        }
        if (effectTimer != null && !effectTimer.isCancelled()) {
            effectTimer.cancel();
        }
    }

    private void registerStormArmorListeners() {
        getServer().getPluginManager().registerEvents(new StormLordArmorListeners(this),this);
    }
}
