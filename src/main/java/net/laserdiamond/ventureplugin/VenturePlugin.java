package net.laserdiamond.ventureplugin;

import net.laserdiamond.ventureplugin.commands.Effects.EffectsCommand;
import net.laserdiamond.ventureplugin.commands.Enchant.EnchantCommand;
import net.laserdiamond.ventureplugin.commands.GiveItems.GiveItemsCommand;
import net.laserdiamond.ventureplugin.commands.ViewProfiles.ViewStats;
import net.laserdiamond.ventureplugin.commands.fillMana;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantListeners;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantPlayerHeadHelmets;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantsClass;
import net.laserdiamond.ventureplugin.enchants.Config.EnchantConfig;
import net.laserdiamond.ventureplugin.enchants.anvil.AnvilInvetoryGUI;
import net.laserdiamond.ventureplugin.events.CancelInventoryMovementMenus;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityListeners;
import net.laserdiamond.ventureplugin.events.damage.DamageEvent;
import net.laserdiamond.ventureplugin.events.damage.ApplyDefense;
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
import net.laserdiamond.ventureplugin.items.armor.ArmorEquipStats;
import net.laserdiamond.ventureplugin.items.armor.Blaze.Config.BlazeArmorConfig;
import net.laserdiamond.ventureplugin.items.armor.StormLord.Components.EyeOfStormCooldown;
import net.laserdiamond.ventureplugin.items.armor.StormLord.Components.StormLordArmorManager;
import net.laserdiamond.ventureplugin.items.armor.StormLord.Config.StormLordArmorConfig;
import net.laserdiamond.ventureplugin.items.armor.Trims.Components.TrimMaterialListeners;
import net.laserdiamond.ventureplugin.items.armor.Trims.Config.ArmorTrimConfig;
import net.laserdiamond.ventureplugin.items.armor.Vanilla.Components.NetheriteArmorManager;
import net.laserdiamond.ventureplugin.items.armor.Vanilla.Config.VanillaArmorConfig;
import net.laserdiamond.ventureplugin.items.crafting.SmithingTable.SmithingTableCrafting;
import net.laserdiamond.ventureplugin.items.util.ItemForgerRegistry;
import net.laserdiamond.ventureplugin.entities.healthDisplay.mobHealthDisplay;
import net.laserdiamond.ventureplugin.events.abilities.AbilityListener;
import net.laserdiamond.ventureplugin.util.RegisterAbilityCaster;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.tunement.Config.TunementConfig;
import net.laserdiamond.ventureplugin.tunement.Manager.TunementProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class VenturePlugin extends JavaPlugin {

    private static VenturePlugin plugin;
    private StatProfileManager statProfileManager;
    private TunementConfig tunementConfig;
    private TunementProfileManager tunementProfileManager;
    private EffectManager effectManager;
    private EffectProfileConfig effectProfileConfig;
    private BukkitTask displayHUDTimer;
    private BukkitTask manaRegen;
    private BukkitTask effectTimer;
    private BukkitTask abilityTimer;

    private BaseStatsConfig baseStatsConfig;
    private EnchantConfig enchantConfig;
    private ArmorTrimConfig armorTrimConfig;
    private NetheriteArmorManager netheriteArmorManager;
    private VanillaArmorConfig vanillaArmorConfig;
    private BlazeArmorConfig blazeArmorConfig;

    private StormLordArmorConfig stormLordArmorConfig;
    private StormLordArmorManager stormLordArmorManager;

    private final List<AbilityListener> abilityListeners = new ArrayList<>();
    private final List<Method> rightClickAbilities = new ArrayList<>();
    private final List<Method> leftClickAbilities = new ArrayList<>();
    private final List<Method> dropItemAbilities = new ArrayList<>();
    private final List<Method> attackEntityAbilities = new ArrayList<>();
    private final List<Method> runnableAbilities = new ArrayList<>();

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

        // Mana Regen
        getServer().getPluginManager().registerEvents(new ManaRegen(this),this);

        // Refresh Items
        getServer().getPluginManager().registerEvents(new ItemForgerRegistry(this),this);

        // Damage
        getServer().getPluginManager().registerEvents(new DamageEvent(this),this);

        // Effect events
        getServer().getPluginManager().registerEvents(new EffectEvents(this),this);

        // Armor stats
        getServer().getPluginManager().registerEvents(new ArmorEquipStats(this),this);
        getServer().getPluginManager().registerEvents(new ApplyDefense(this),this);
        getServer().getPluginManager().registerEvents(new TrimMaterialListeners(this),this);

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

        // Mob names
        getServer().getPluginManager().registerEvents(new mobHealthDisplay(this),this);

        // Spell Casts
        getServer().getPluginManager().registerEvents(new AbilityListeners(this),this);

        // Register Commands
        getCommand("plugineffect").setExecutor(new EffectsCommand(this));
        getCommand("plugingive").setExecutor(new GiveItemsCommand(this));
        getCommand("pluginenchant").setExecutor(new EnchantCommand());
        getCommand("stats").setExecutor(new ViewStats());
        getCommand("refillmana").setExecutor(new fillMana());

        // TODO: Test Spell Casters
        addAbilities();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
    }

    public static VenturePlugin getInstance() {
        return plugin;
    }

    public StatProfileManager getStatProfileManager() {
        return statProfileManager;
    }
    public TunementProfileManager getTunementProfileManager() {
        return tunementProfileManager;
    }
    public EffectManager getEffectManager() {
        return effectManager;
    }
    public NetheriteArmorManager getNetheriteArmorManager() {
        return netheriteArmorManager;
    }
    public StormLordArmorManager getStormArmorManager() {
        return stormLordArmorManager;
    }

    public List<AbilityListener> getAbilityListeners()
    {
        return abilityListeners;
    }
    public List<Method> getAbilityMethods(AbilityCastType abilityCastType)
    {
        switch (abilityCastType)
        {
            case RIGHT_CLICK -> {
                return rightClickAbilities;
            }
            case LEFT_CLICK -> {
                return leftClickAbilities;
            }
            case DROP_ITEM -> {
                return dropItemAbilities;
            }
            case RUNNABLE -> {
                return runnableAbilities;
            }
            case ATTACK_ENTITY -> {
                return attackEntityAbilities;
            }
        }
        return new ArrayList<>();
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
    public TunementConfig getTunementConfig() {
        return tunementConfig;
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

        tunementConfig = new TunementConfig(this, "tunement");
        tunementConfig.loadConfig();

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
        tunementProfileManager = new TunementProfileManager(this);
        tunementProfileManager.loadFromConfig();

        statProfileManager = new StatProfileManager(this);

        effectManager = new EffectManager(this);
        effectManager.loadProfilesFromConfig();
    }
    private void createItemManagers() {
        netheriteArmorManager = new NetheriteArmorManager(this);
        stormLordArmorManager = new StormLordArmorManager();
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
    }
    private void addAbilities()
    {
        RegisterAbilityCaster.addListener(new EnchantListeners(this),this);
        RegisterAbilityCaster.addListener(stormLordArmorManager, this);

        /*
        for (AbilityListener abilityListener : abilityListeners)
        {
            for (Method method : abilityListener.getClass().getDeclaredMethods())
            {
                AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                switch (annotation.abilityCastType())
                {
                    case RIGHT_CLICK -> rightClickAbilities.add(method);
                    case LEFT_CLICK -> leftClickAbilities.add(method);
                    case DROP_ITEM -> dropItemAbilities.add(method);
                    case RUNNABLE -> runnableAbilities.add(method);
                    case ATTACK_ENTITY -> attackEntityAbilities.add(method);
                }
            }
        }
         */
    }

    private void saveProfilesToConfigs() {
        tunementProfileManager.saveToConfig();

        effectManager.saveProfilesToConfig();
    }
    private void saveConfigs() {
        tunementConfig.saveConfig();

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
}
