package net.laserdiamond.ventureplugin.items.armor.Vanilla.Config;

import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class NetheriteArmorConfig implements GetVarFile {

    private final VenturePlugin plugin;
    private final String fileName;
    private final File folders;
    private final File file;
    private final FileConfiguration config = new YamlConfiguration();

    public NetheriteArmorConfig(VenturePlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        folders = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + "armor" + File.separator + "vanilla");
        file = new File(folders, fileName + ".yml");
    }
    @Override
    public void loadConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("items" + File.separator + "armor" + File.separator + "vanilla" + File.separator + fileName + ".yml", false);
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR LOADING NETHERITE ARMOR CONFIG FROM FILE");
            exception.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public Double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public Integer getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }
}