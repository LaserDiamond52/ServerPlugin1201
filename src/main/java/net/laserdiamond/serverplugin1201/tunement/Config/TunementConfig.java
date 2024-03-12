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

    private final ServerPlugin1201 PLUGIN;
    private final File FOLDERS;
    private final File FILE;
    private final String FILE_NAME;
    private final FileConfiguration CONFIG = new YamlConfiguration();

    public TunementConfig(ServerPlugin1201 plugin, String fileName) {
        this.PLUGIN = plugin;
        this.FILE_NAME = fileName;
        FOLDERS = new File(plugin.getDataFolder(), File.separator + "players");
        FILE = new File(FOLDERS, fileName + ".yml");
    }
    @Override
    public void loadConfig() {
        if (!FILE.exists())
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "No existing Tunement file found!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Creating new Tunement file...");
            FILE.getParentFile().mkdirs();
            PLUGIN.saveResource("players" + File.separator + FILE_NAME + ".yml", false);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Found existing tunment file!");
        }
        try {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Loading tunement file...");
            CONFIG.load(FILE);
        } catch (IOException | InvalidConfigurationException exception)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE LOADING TUNEMENT FILE");
            exception.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return CONFIG;
    }

    @Override
    public void saveConfig() {
        try {
            CONFIG.save(FILE);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved Tunement config to file!");
        } catch (IOException exception)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE SAVING TUNEMENT CONFIG TO FILE");
            exception.printStackTrace();
        }
    }
}
