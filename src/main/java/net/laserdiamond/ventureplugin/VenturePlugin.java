package net.laserdiamond.ventureplugin;

import net.laserdiamond.ventureplugin.commands.Effects.EffectsCommand;
import net.laserdiamond.ventureplugin.commands.Enchant.EnchantCommand;
import net.laserdiamond.ventureplugin.commands.Items.GiveItemsCommand;
import net.laserdiamond.ventureplugin.commands.Items.StarItemCommand;
import net.laserdiamond.ventureplugin.commands.ViewProfiles.TuningMenu;
import net.laserdiamond.ventureplugin.commands.ViewProfiles.ViewStats;
import net.laserdiamond.ventureplugin.commands.fillMana;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantListeners;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantPlayerHeadHelmets;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.events.CancelInventoryMovementMenus;
import net.laserdiamond.ventureplugin.events.abilities.*;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.AssassinCloakCooldown;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.SniperCooldown;
import net.laserdiamond.ventureplugin.events.damage.DamageEvent;
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
import net.laserdiamond.ventureplugin.items.armor.util.ArmorEquipStats;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.EyeOfStormCooldown;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.TrimMaterialListeners;
import net.laserdiamond.ventureplugin.items.crafting.SmithingTable.SmithingTableCrafting;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.entities.healthDisplay.mobHealthDisplay;
import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private final HashMap<String, ItemForger> itemRegistryMap = new HashMap<>();
    private final HashMap<String, ItemForger> unobtainableItemRegistryMap = new HashMap<>();
    private final List<VentureArmorSet> playerArmorItemMap = new ArrayList<>();
    private final List<VentureMenuItem> ventureMenuItems = new ArrayList<>();

    private NetheriteArmor netheriteArmor;
    private BlazeArmor blazeArmor;
    private SoulFireBlazeArmor soulFireBlazeArmor;
    private StormLordArmor stormLordArmor;
    private AssassinArmor assassinArmor;
    private ReinforcedDiamondArmor reinforcedDiamondArmor;
    private PrismariteArmor prismariteArmor;
    private FleshRevenantArmor fleshRevenantArmor;
    private BoneTerrorArmor boneTerrorArmor;


    private final List<ArmorConfig> armorConfigs = new ArrayList<>();

    private final ArmorConfig

            armorTrimConfig = new ArmorConfig(this, "trims"),
            netheriteArmorConfig = new ArmorConfig(this, "netherite"),
            blazeArmorConfig = new ArmorConfig(this,"blaze"),
            soulFireBlazeArmorConfig = new ArmorConfig(this, "soul_fire_blaze"),
            stormLordArmorConfig = new ArmorConfig(this,"storm_lord"),
            assassinArmorConfig = new ArmorConfig(this, "assassin"),
            reinforcedDiamondArmorConfig = new ArmorConfig(this, "reinforced_diamond"),
            prismariteArmorConfig = new ArmorConfig(this, "prismarite"),
            fleshRevenantArmorConfig = new ArmorConfig(this, "flesh_revenant"),
            boneTerrorArmorConfig = new ArmorConfig(this, "bone_terror");

    private final List<AbilityListener> abilityListeners = new ArrayList<>();
    private final HashMap<AbilityListener, Method> rightClickAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> leftClickAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> dropItemAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> attackEntityAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> runnableAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> onKillAbilities = new HashMap<>();
    private final HashMap<AbilityListener, Method> toggleSneakAbilities = new HashMap<>();
    private final Abilities abilities = new Abilities(rightClickAbilities, leftClickAbilities, dropItemAbilities,runnableAbilities, attackEntityAbilities, onKillAbilities, toggleSneakAbilities);

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
        getServer().getPluginManager().registerEvents(new DamageEvent(this),this);
        getServer().getPluginManager().registerEvents(new PlayerDmg(),this);

        // Effect events
        getServer().getPluginManager().registerEvents(new EffectEvents(this),this);

        // Armor stats
        getServer().getPluginManager().registerEvents(new ArmorEquipStats(this),this);
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

        // Mob names
        getServer().getPluginManager().registerEvents(new mobHealthDisplay(this),this);

        // Skill Exp Listeners
        getServer().getPluginManager().registerEvents(new SkillsExpGainListener(), this);



        // Register Commands
        getCommand("plugineffect").setExecutor(new EffectsCommand(this));
        getCommand("plugingive").setExecutor(new GiveItemsCommand(this));
        getCommand("pluginenchant").setExecutor(new EnchantCommand());
        ViewStats viewStats = new ViewStats(this);
        TuningMenu tuningMenu = new TuningMenu(this);
        getCommand("refillmana").setExecutor(new fillMana());
        getCommand("staritem").setExecutor(new StarItemCommand());
        // TODO: Summon cmd for mobs

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
    public NetheriteArmor getNetheriteArmorManager() {
        return netheriteArmor;
    }
    public BlazeArmor getBlazeArmorManager()
    {
        return blazeArmor;
    }
    public SoulFireBlazeArmor getSoulFireBlazeArmor()
    {
        return soulFireBlazeArmor;
    }
    public StormLordArmor getStormArmorManager() {
        return stormLordArmor;
    }
    public AssassinArmor getAssassinArmor()
    {
        return assassinArmor;
    }
    public ReinforcedDiamondArmor getReinforcedDiamondArmor()
    {
        return reinforcedDiamondArmor;
    }
    public PrismariteArmor getPrismariteArmor()
    {
        return prismariteArmor;
    }
    public FleshRevenantArmor getFleshRevenantArmor()
    {
        return fleshRevenantArmor;
    }
    public BoneTerrorArmor getBoneTerrorArmor()
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

    public HashMap<String, ItemForger> getItemRegistryMap() {
        return itemRegistryMap;
    }

    public List<VentureArmorSet> getPlayerArmorItemMap() {
        return playerArmorItemMap;
    }

    public List<ArmorConfig> getArmorConfigs() {
        return armorConfigs;
    }

    public ArmorConfig getArmorTrimConfig() {
        return armorTrimConfig;
    }

    public ArmorConfig getNetheriteArmorConfig() {
        return netheriteArmorConfig;
    }

    public ArmorConfig getBlazeArmorConfig() {
        return blazeArmorConfig;
    }

    public ArmorConfig getSoulFireBlazeArmorConfig() {
        return soulFireBlazeArmorConfig;
    }

    public ArmorConfig getStormLordArmorConfig() {
        return stormLordArmorConfig;
    }

    public ArmorConfig getAssassinArmorConfig() {
        return assassinArmorConfig;
    }

    public ArmorConfig getReinforcedDiamondArmorConfig() {
        return reinforcedDiamondArmorConfig;
    }

    public ArmorConfig getPrismariteArmorConfig() {
        return prismariteArmorConfig;
    }

    public ArmorConfig getFleshRevenantArmorConfig() {
        return fleshRevenantArmorConfig;
    }

    public ArmorConfig getBoneTerrorArmorConfig() {
        return boneTerrorArmorConfig;
    }

    public HashMap<String, ItemForger> getUnobtainableItemRegistryMap() {
        return unobtainableItemRegistryMap;
    }

    public List<VentureMenuItem> getVentureMenuItems() {
        return ventureMenuItems;
    }

    public PlayerSaveConfig getSkillsConfig() {
        return skillsConfig;
    }

    public SkillsProfileManager getSkillsProfileManager() {
        return skillsProfileManager;
    }
}
