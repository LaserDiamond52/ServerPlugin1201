package net.laserdiamond.ventureplugin.util.Config;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public abstract class MiscConfig extends ConfigLoader {

    private final VenturePlugin plugin;
    private final String folderName;
    private final String fileName;
    private final File file;

    public MiscConfig(VenturePlugin plugin, String folderName, String fileName)
    {
        this.plugin = plugin;
        this.folderName = folderName;
        this.fileName = fileName;
        File folders = new File(plugin.getDataFolder() + File.separator + folderName);
        file = new File(folders, fileName + ".yml");
    }

    @Override
    public void loadConfig() {
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            plugin.saveResource(folderName + File.separator + fileName + ".yml", false);
        }
        try {
            getConfig().load(file);
        } catch (IOException | InvalidConfigurationException exception)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR LOADING " + fileName.replace("_", " ").toUpperCase() + " CONFIG FROM FILE");
            exception.printStackTrace();
        }
    }
}
