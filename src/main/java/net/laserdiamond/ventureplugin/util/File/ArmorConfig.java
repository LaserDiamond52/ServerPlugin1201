package net.laserdiamond.ventureplugin.util.File;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class ArmorConfig implements GetVarFile {

    private final VenturePlugin plugin;
    private final String fileName;
    private final File file;
    private final FileConfiguration config = new YamlConfiguration();

    public ArmorConfig(VenturePlugin plugin, String fileName)
    {
        this.plugin = plugin;
        this.fileName = fileName;
        File folders = new File(plugin.getDataFolder() + File.separator + "items" + File.separator + "armor" + File.separator + fileName);
        file = new File(folders, fileName + ".yml");
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

    @Override
    public List<Integer> getIntList(String path)
    {
        return config.getIntegerList(path);
    }
}
