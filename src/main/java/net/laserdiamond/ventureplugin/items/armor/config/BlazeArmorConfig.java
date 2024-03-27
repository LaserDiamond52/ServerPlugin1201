package net.laserdiamond.ventureplugin.items.armor.config;

import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BlazeArmorConfig implements GetVarFile {

    private final VenturePlugin plugin;
    private final String fileName;
    private final File file;
    private final FileConfiguration config = new YamlConfiguration();


    public BlazeArmorConfig(VenturePlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        File folders = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + "armor" + File.separator + "blaze");
        file = new File(folders, fileName + ".yml");
    }

    @Override
    public void loadConfig() {

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("items" + File.separator + "armor" + File.separator + "blaze" + File.separator + fileName + ".yml", false);
        }
        try {
            this.config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR LOADING BLAZE ARMOR CONFIG FROM FILE");
            exception.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return this.config;
    }

    @Override
    public Double getDouble(String path) {
        return this.config.getDouble(path);
    }

    @Override
    public Integer getInt(String path) {
        return this.config.getInt(path);
    }

    @Override
    public String getString(String path) {
        return this.config.getString(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }
}
