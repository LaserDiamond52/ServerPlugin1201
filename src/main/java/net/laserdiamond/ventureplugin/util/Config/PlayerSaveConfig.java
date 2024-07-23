package net.laserdiamond.ventureplugin.util.Config;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.File.FileSaver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.io.IOException;

/**
 * Class that represents a player config file that is saved to the config
 */
public class PlayerSaveConfig extends PlayerConfig implements FileSaver {

    public PlayerSaveConfig(VenturePlugin plugin, String fileName)
    {
        super(plugin, fileName);
    }

    @Override
    public void saveConfig()
    {
        try {
            config.save(file);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved " + fileName + " config to file!");
        } catch (IOException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR WHILE SAVING " + fileName.toUpperCase() + " CONFIG TO FILE");
            e.printStackTrace();
        }
    }
}
