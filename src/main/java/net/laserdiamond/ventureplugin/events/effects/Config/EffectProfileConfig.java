package net.laserdiamond.ventureplugin.events.effects.Config;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EffectProfileConfig {

    private VenturePlugin plugin;

    private String fileName;
    private File folders;
    private File file;
    private FileConfiguration effectConfig = new YamlConfiguration();

    public EffectProfileConfig(VenturePlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        folders = new File(plugin.getDataFolder() + File.separator + "players");
        file = new File(folders, fileName + ".yml");
    }

    public void loadConfig() {
        // If the file doesn't exist, create a new one
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("players" + File.separator + fileName + ".yml", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No effect duration config file found!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Creating new effect config from file!");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Found existing effect config file!");
        }
        try {
            effectConfig.load(file);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded effect duration config from file!");
        } catch (IOException | InvalidConfigurationException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR LOADING EFFECT PROFILE CONFIG FILE!");
            exception.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            effectConfig.save(file);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved effect config to file!");
        } catch (IOException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE SAVING EFFECTS CONFIG TO FILE");
            exception.printStackTrace();
        }
    }

    public FileConfiguration getEffectConfig() {
        return effectConfig;
    }
}
