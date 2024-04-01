package net.laserdiamond.ventureplugin.util.File;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.util.Config.ConfigLoader;
import net.laserdiamond.ventureplugin.util.Stars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ArmorConfig extends ConfigLoader {

    private final VenturePlugin plugin;
    private final String fileName;
    private final File file;

    public ArmorConfig(VenturePlugin plugin, String fileName)
    {
        this.plugin = plugin;
        this.fileName = fileName;
        File folders = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + "armor" + File.separator + fileName);
        file = new File(folders, fileName + ".yml");
        plugin.getArmorConfigs().add(this);
    }

    @Override
    public final void loadConfig()
    {
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            plugin.saveResource("items" + File.separator + "armor" + File.separator + fileName + File.separator + fileName + ".yml", false);
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE LOADING " + fileName.replace("_", " ").toUpperCase() + " CONFIG FROM FILE");
            exception.printStackTrace();
        }
    }

    private final double starBonus = Stars.STARS.getBoostPerStar();

    /**
     * Gets the stat type of the armor set from the config file
     * @param armorPieceTypes The armor piece type
     * @param statType The stat to get
     * @param stars The amount of stars the item should have
     * @return The value of that stat as a double
     */
    public double getStat(@NotNull ArmorPieceTypes armorPieceTypes, StatType statType, int stars)
    {
        if (statType.isPerPiece())
        {
            return config.getDouble(armorPieceTypes.getName() + statType.key) * (1 + stars * this.starBonus);
        } else
        {
            return config.getDouble(statType.key);
        }
    }

    public enum StatType
    {
        MELEE_DAMAGE ("MeleeDamage", true),
        RANGE_DAMAGE ("RangeDamage", true),
        MAGIC_DAMAGE ("MagicDamage", true),
        MANA ("Mana", true),
        HEALTH ("Health", true),
        DEFENSE("Defense", true),
        FIRE_DEFENSE("FireDefense", true),
        PROJECTILE_DEFENSE("ProjectileDefense", true),
        EXPLOSION_DEFENSE("BlastDefense", true),
        MAGIC_DEFENSE("MagicDefense", true),
        TOUGHNESS ("toughness", false),
        FORTITUDE ("fortitude", false),
        SPEED ("Speed", true);
        private final String key;
        private final boolean isPerPiece;

        StatType(String key, boolean isPerPiece)
        {
            this.key = key;
            this.isPerPiece = isPerPiece;
        }

        public String getKey() {
            return key;
        }

        public boolean isPerPiece() {
            return isPerPiece;
        }
    }

}
