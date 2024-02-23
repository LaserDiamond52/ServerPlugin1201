package net.laserdiamond.serverplugin1201.enchants.Config;

import net.laserdiamond.serverplugin1201.Management.File.GetVarFile;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EnchantConfig implements GetVarFile {

    private final ServerPlugin1201 plugin;
    private final String fileName;
    private final File folders;
    private final File file;
    private final FileConfiguration config = new YamlConfiguration();

    public EnchantConfig(ServerPlugin1201 plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        folders = new File(plugin.getDataFolder() + File.separator + "enchants");
        file = new File(folders, fileName + ".yml");
    }

    @Override
    public void loadConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("enchants" + File.separator + fileName + ".yml", false);
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR LOADING ENCHANTS CONFIG FROM FILE");
            exception.printStackTrace();
        }
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

    @Override
    public FileConfiguration getConfig() {
        return config;
    }
}
