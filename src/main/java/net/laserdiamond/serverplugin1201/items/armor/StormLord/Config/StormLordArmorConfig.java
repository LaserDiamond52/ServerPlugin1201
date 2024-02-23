package net.laserdiamond.serverplugin1201.items.armor.StormLord.Config;

import net.laserdiamond.serverplugin1201.Management.File.GetVarFile;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StormLordArmorConfig implements GetVarFile {

    private ServerPlugin1201 plugin;
    private String fileName;
    private File folders;
    private File file;
    private FileConfiguration config = new YamlConfiguration();

    public StormLordArmorConfig(ServerPlugin1201 plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        folders = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + "armor" + File.separator + "storm_lord");
        file = new File(folders, fileName + ".yml");
    }

    @Override
    public void loadConfig() {

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("items" + File.separator + "armor" + File.separator + "storm_lord" + File.separator + fileName + ".yml", false);
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR LOADING STORM LORD ARMOR CONFIG FROM FILE");
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
