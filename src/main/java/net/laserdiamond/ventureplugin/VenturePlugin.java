package net.laserdiamond.ventureplugin;

import net.kyori.adventure.Adventure;
import net.kyori.adventure.audience.Audiences;
import net.laserdiamond.ventureplugin.commands.effects.EffectsCommand;
import net.laserdiamond.ventureplugin.commands.enchant.EnchantCommand;
import net.laserdiamond.ventureplugin.commands.items.GiveItemsCommand;
import net.laserdiamond.ventureplugin.commands.items.StarItemCommand;
import net.laserdiamond.ventureplugin.commands.skills_edit.SkillsExpCommand;
import net.laserdiamond.ventureplugin.commands.summon.MobSummonCommand;
import net.laserdiamond.ventureplugin.commands.view_profiles.TuningMenu;
import net.laserdiamond.ventureplugin.commands.view_profiles.ViewSkills;
import net.laserdiamond.ventureplugin.commands.view_profiles.ViewStats;
import net.laserdiamond.ventureplugin.commands.FillMana;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantListeners;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantPlayerHeadHelmets;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.entities.mobs.LootTableListener;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobs;
import net.laserdiamond.ventureplugin.entities.util.VentureMob;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import net.laserdiamond.ventureplugin.events.CancelInventoryMovementMenus;
import net.laserdiamond.ventureplugin.events.abilities.*;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.AssassinCloakCooldown;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.SniperCooldown;
import net.laserdiamond.ventureplugin.events.damage.DamageDisplays;
import net.laserdiamond.ventureplugin.events.damage.ApplyDefense;
import net.laserdiamond.ventureplugin.events.damage.inflict.PlayerDmg;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.NecrosisTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.ParalyzeTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.Timers.VulnerableTimer;
import net.laserdiamond.ventureplugin.events.effects.Components.EffectEvents;
import net.laserdiamond.ventureplugin.events.HUD.HUD;
import net.laserdiamond.ventureplugin.events.mana.ManaRegen;
import net.laserdiamond.ventureplugin.events.PlayerJoinServer;
import net.laserdiamond.ventureplugin.events.effects.Components.EffectTimer;
import net.laserdiamond.ventureplugin.events.effects.Config.EffectProfileConfig;
import net.laserdiamond.ventureplugin.events.effects.Managers.EffectManager;
import net.laserdiamond.ventureplugin.items.armor.armor_sets.*;
import net.laserdiamond.ventureplugin.items.armor.ArmorEquipStats;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.EyeOfStormCooldown;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.TrimMaterialListeners;
import net.laserdiamond.ventureplugin.items.crafting.SmithingTable.SmithingTableCrafting;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureSkillProgressItem;
import net.laserdiamond.ventureplugin.items.misc.MiscItemsManager;
import net.laserdiamond.ventureplugin.items.misc.VentureMiscItem;
import net.laserdiamond.ventureplugin.items.tools.ToolEquipStats;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureMenuItem;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.SkillsExpGainListener;
import net.laserdiamond.ventureplugin.skills.Manager.SkillsProfileManager;
import net.laserdiamond.ventureplugin.util.Config.MiscConfig;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.Config.PlayerSaveConfig;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.items.util.ItemRegistry;
import net.laserdiamond.ventureplugin.util.RegisterAbilityCaster;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.tuning.Manager.TuningProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.*;

public final class VenturePlugin extends JavaPlugin {

    private static VenturePlugin plugin;
    private StatProfileManager statProfileManager;
    private PlayerSaveConfig skillsConfig;
    private PlayerSaveConfig tuningConfig;
    private SkillsProfileManager skillsProfileManager;
    private TuningProfileManager tuningProfileManager;
    private EffectManager effectManager;
    private EffectProfileConfig effectProfileConfig;
    private BukkitTask displayHUDTimer;
    private BukkitTask manaRegen;
    private BukkitTask effectTimer;
    private BukkitTask abilityTimer;

    private PlayerConfig baseStatsConfig;
    private MiscConfig enchantConfig;

    private final HashMap<String, VentureItemBuilder> itemRegistryMap = new HashMap<>();
    private final HashMap<String, VentureMiscItem> miscItemMap = new HashMap<>();
    private final HashMap<String, VentureArmorSet> armorSetItemMap = new HashMap<>();
    private final HashMap<String, VentureMenuItem> ventureMenuItems = new HashMap<>();
    private final HashMap<String, VentureSkillProgressItem> ventureSkillProgressItems = new HashMap<>();

    private VentureArmorSet netheriteArmor;
    private VentureArmorSet blazeArmor;
    private VentureArmorSet soulFireBlazeArmor;
    private VentureArmorSet stormLordArmor;
    private VentureArmorSet assassinArmor;
    private VentureArmorSet reinforcedDiamondArmor;
    private VentureArmorSet prismariteArmor;
    private VentureArmorSet fleshRevenantArmor;
    private VentureArmorSet boneTerrorArmor;

    private final List<ArmorConfig> armorConfigs = new ArrayList<>();

    private final ArmorConfig armorTrimConfig = new ArmorConfig(this, "trims");


    private final List<AbilityListener> abilityListeners = new ArrayList<>();
    private final HashMap<AbilityListener, Method> rightClickAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> leftClickAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> dropItemAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> attackEntityAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> runnableAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> onKillAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> toggleSneakAbilities = new HashMap<>();
    private final Abilities abilities = new Abilities(rightClickAbilities, leftClickAbilities, dropItemAbilities,runnableAbilities, attackEntityAbilities, onKillAbilities, toggleSneakAbilities);

    private final HashMap<String, VentureMob<?>> ventureMobMap = new HashMap<>();
    private final HashMap<VentureMobType, List<VentureLootTableEntry>> ventureMobLootTables = new HashMap<>();

    public static final String PLUGIN_ID = "venture";

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling Plugin...");
        plugin = this;

        // Create Stat Profiles for players
        createConfigs();
        createItemConfigs();
        createManagers();
        MiscItemsManager.registerItems();
        createItemManagers();
        VentureEnchants.register();
        createTimers();
        setUpCooldowns();
        getServer().getPluginManager().registerEvents(new PlayerJoinServer(this), this);

        // Spell Casts
        getServer().getPluginManager().registerEvents(new AbilityListeners(this),this);

        // Register timers
        createTasks();

        // Mana Regen
        getServer().getPluginManager().registerEvents(new ManaRegen(this),this);

        // Refresh Items
        //getServer().getPluginManager().registerEvents(new ItemForgerRegistry(this),this);
        getServer().getPluginManager().registerEvents(new ItemRegistry(),this);

        // Damage
        getServer().getPluginManager().registerEvents(new DamageDisplays(this),this);
        getServer().getPluginManager().registerEvents(new PlayerDmg(),this);

        // Effect events
        getServer().getPluginManager().registerEvents(new EffectEvents(this),this);

        // Armor stats + Tool stats
        getServer().getPluginManager().registerEvents(new ArmorEquipStats(this),this);
        getServer().getPluginManager().registerEvents(new ToolEquipStats(this), this);
        getServer().getPluginManager().registerEvents(new ApplyDefense(this),this);
        getServer().getPluginManager().registerEvents(new TrimMaterialListeners(this),this);

        // Enchants
        getServer().getPluginManager().registerEvents(new EnchantListeners(this),this);
        getServer().getPluginManager().registerEvents(new EnchantPlayerHeadHelmets(this),this);

        // Menu Inventory Manipulation
        getServer().getPluginManager().registerEvents(new CancelInventoryMovementMenus(),this);
        MiscMenuItems menuItems = new MiscMenuItems(this);

        // Smithing Table Inventory
        getServer().getPluginManager().registerEvents(new SmithingTableCrafting(this),this);
        //SmithingTableCrafting.init();

        // Anvil Inventory GUI
        //getServer().getPluginManager().registerEvents(new AnvilInvetoryGUI(this),this);

        // Skill Exp Listeners
        getServer().getPluginManager().registerEvents(new SkillsExpGainListener(), this);

        VentureMobs.register(this);
        new LootTableListener(this);

        // Register Commands
        getCommand("plugineffect").setExecutor(new EffectsCommand(this));
        getCommand("plugingive").setExecutor(new GiveItemsCommand(this));
        getCommand("pluginenchant").setExecutor(new EnchantCommand());
        ViewStats viewStats = new ViewStats(this);
        TuningMenu tuningMenu = new TuningMenu(this);
        ViewSkills viewSkills = new ViewSkills(this);
        getCommand("skillsexp").setExecutor(new SkillsExpCommand());
        getCommand("refillmana").setExecutor(new FillMana());
        getCommand("staritem").setExecutor(new StarItemCommand());
        // TODO: Summon cmd for mobs
        getCommand("summonventuremob").setExecutor(new MobSummonCommand());

        // TODO: Test Spell Casters
        addAbilities();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        // Kick all players upon loading the server

        // If any players somehow stay on the server after the plugin is disabled, they will be caught here

        // Prevent errors from occurring if a player was to stay on the server while the server re-enables itself
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.kickPlayer(ChatColor.RED + "Server reloading. Please rejoin in a moment!");
        }
    }

    public static VenturePlugin getInstance() {
        return plugin;
    }

    public StatProfileManager getStatProfileManager() {
        return statProfileManager;
    }
    public TuningProfileManager getTunementProfileManager() {
        return tuningProfileManager;
    }
    public EffectManager getEffectManager() {
        return effectManager;
    }
    public VentureArmorSet getNetheriteArmorManager() {
        return netheriteArmor;
    }
    public VentureArmorSet getBlazeArmorManager()
    {
        return blazeArmor;
    }
    public VentureArmorSet getSoulFireBlazeArmor()
    {
        return soulFireBlazeArmor;
    }
    public VentureArmorSet getStormArmorManager() {
        return stormLordArmor;
    }
    public VentureArmorSet getAssassinArmor()
    {
        return assassinArmor;
    }
    public VentureArmorSet getReinforcedDiamondArmor()
    {
        return reinforcedDiamondArmor;
    }
    public VentureArmorSet getPrismariteArmor()
    {
        return prismariteArmor;
    }
    public VentureArmorSet getFleshRevenantArmor()
    {
        return fleshRevenantArmor;
    }
    public VentureArmorSet getBoneTerrorArmor()
    {
        return boneTerrorArmor;
    }
    public List<AbilityListener> getAbilityListeners()
    {
        return abilityListeners;
    }
    public Abilities getAbilities()
    {
        return abilities;
    }

    // TODO: Make ability listeners register from the class they originate from

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Disabling Plugin...");

        // Automatically kick all players on the server when disabling the plugin

        // Prevent errors from occurring if a player was to stay on the server while the server re-enables itself
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.kickPlayer(ChatColor.RED + "Server reloading. Please rejoin in a moment!");
        }

        // REMEMBER TO SAVE PROFILES TO CONFIGS FIRST!!!!
        saveProfilesToConfigs();
        saveConfigs();

        // Cancel repeating tasks
        cancelTasks();

        // Unregister enchantments
        VentureEnchants.unregisterEnchantments();
    }

    public PlayerConfig getBaseStatsConfig() {
        return baseStatsConfig;
    }
    public PlayerSaveConfig getTuningConfig() {
        return tuningConfig;
    }
    public EffectProfileConfig getEffectProfileConfig() {
        return effectProfileConfig;
    }
    public MiscConfig getEnchantConfig() {
        return enchantConfig;
    }

    /*
    public ArmorTrimConfig getArmorTrimConfig() {
        return armorTrimConfig;
    }

     */

    private void createConfigs() {
        baseStatsConfig = new PlayerConfig(this, "baseStats");
        baseStatsConfig.loadConfig();

        skillsConfig = new PlayerSaveConfig(this, "skills");
        skillsConfig.loadConfig();

        tuningConfig = new PlayerSaveConfig(this, "tuning");
        tuningConfig.loadConfig();

        effectProfileConfig = new EffectProfileConfig(this, "effectDurations");
        effectProfileConfig.loadConfig();

        enchantConfig = new MiscConfig(this, "enchants", "enchants");
        enchantConfig.loadConfig();
    }

    private void createItemConfigs() {

        armorTrimConfig.loadConfig();

        for (ArmorConfig armorConfig : armorConfigs)
        {
            armorConfig.loadConfig();
        }



    }
    private void createManagers() {

        skillsProfileManager = new SkillsProfileManager(this);
        skillsProfileManager.loadFromConfig();

        tuningProfileManager = new TuningProfileManager(this);
        tuningProfileManager.loadFromConfig();

        statProfileManager = new StatProfileManager(this);

        effectManager = new EffectManager(this);
        effectManager.loadProfilesFromConfig();
    }

    private void createItemManagers() {

        netheriteArmor = new NetheriteArmor(this);
        blazeArmor = new BlazeArmor(this);
        soulFireBlazeArmor = new SoulFireBlazeArmor(this);
        stormLordArmor = new StormLordArmor(this);
        assassinArmor = new AssassinArmor(this);
        reinforcedDiamondArmor = new ReinforcedDiamondArmor(this);
        prismariteArmor = new PrismariteArmor(this);
        fleshRevenantArmor = new FleshRevenantArmor(this);
        boneTerrorArmor = new BoneTerrorArmor(this);
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
        abilityTimer = new AbilityListeners(this).runTaskTimer(this, 0L, 1L);
    }
    private void setUpCooldowns() {
        EyeOfStormCooldown.setUpCooldown();
        AssassinCloakCooldown.setUpCooldown();
        SniperCooldown.setUpCooldown();
    }
    private void addAbilities()
    {
        RegisterAbilityCaster.addListener(new EnchantListeners(this),this); // TODO: Convert this to new method

        for (AbilityListener listener : getAbilityListeners())
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(AbilityHandler.class))
                {
                    AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                    switch (annotation.abilityCastType())
                    {
                        case RIGHT_CLICK -> abilities.rightClickAbilities().put(listener, method);
                        case LEFT_CLICK -> abilities.leftClickAbilities().put(listener, method);
                        case DROP_ITEM -> abilities.dropItemAbilities().put(listener, method);
                        case RUNNABLE -> abilities.runnableAbilities().put(listener, method);
                        case ATTACK_ENTITY -> abilities.attackAbility().put(listener, method);
                        case ON_KILL -> abilities.onKillAbility().put(listener,method);
                        case TOGGLE_SNEAK -> abilities.toggleSneakAbility().put(listener,method);
                    }
                }
            }
        }
    }

    private void saveProfilesToConfigs() {

        skillsProfileManager.saveToConfig();

        tuningProfileManager.saveToConfig();

        effectManager.saveProfilesToConfig();
    }
    private void saveConfigs() {

        skillsConfig.saveConfig();

        tuningConfig.saveConfig();

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
        if (abilityTimer != null && !abilityTimer.isCancelled()) {
            abilityTimer.cancel();
        }
    }

    public HashMap<String, VentureItemBuilder> getItemRegistryMap() {
        return itemRegistryMap;
    }

    public ArmorConfig getArmorTrimConfig() {
        return armorTrimConfig;
    }

    public HashMap<String, VentureMenuItem> getVentureMenuItems() {
        return ventureMenuItems;
    }

    public PlayerSaveConfig getSkillsConfig() {
        return skillsConfig;
    }

    public SkillsProfileManager getSkillsProfileManager() {
        return skillsProfileManager;
    }

    public HashMap<String, VentureArmorSet> getArmorSetItemMap() {
        return armorSetItemMap;
    }

    public HashMap<String, VentureSkillProgressItem> getVentureSkillProgressItems() {
        return ventureSkillProgressItems;
    }

    public HashMap<String, VentureMob<?>> getVentureMobMap() {
        return ventureMobMap;
    }

    public HashMap<VentureMobType, List<VentureLootTableEntry>> getVentureMobLootTables() {
        return ventureMobLootTables;
    }

    public HashMap<String, VentureMiscItem> getMiscItemMap() {
        return miscItemMap;
    }
}
