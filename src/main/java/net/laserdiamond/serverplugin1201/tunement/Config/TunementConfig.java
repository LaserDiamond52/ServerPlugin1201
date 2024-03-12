package net.laserdiamond.serverplugin1201.tunement.Config;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.management.File.FileLoaderSaver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TunementConfig implements FileLoaderSaver {

    private final ServerPlugin1201 plugin;
    private final File folders;
    private final File file;
    private final String fileName;
    private final FileConfiguration config = new YamlConfiguration();

    public TunementConfig(ServerPlugin1201 plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        folders = new File(plugin.getDataFolder(), File.separator + "players");
        file = new File(folders, fileName + ".yml");
    }
    @Override
    public void loadConfig() {
        if (!file.exists())
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "No existing Tunement file found!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Creating new Tunement file...");
            file.getParentFile().mkdirs();
            plugin.saveResource("players" + File.separator + fileName + ".yml", false);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Found existing tunment file!");
        }
        try {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading tunement file...");
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE LOADING TUNEMENT FILE");
            exception.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void saveConfig() {
        try {
            config.save(file);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved Tunement config to file!");
        } catch (IOException exception)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE SAVING TUNEMENT CONFIG TO FILE");
            exception.printStackTrace();
        }
    }
}
