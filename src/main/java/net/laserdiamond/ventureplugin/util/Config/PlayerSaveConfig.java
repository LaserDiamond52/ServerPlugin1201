package net.laserdiamond.ventureplugin.util.Config;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.io.IOException;

/**
 * Class that represents a player config file that is saved to the config
 */
public class PlayerSaveConfig extends PlayerConfig {

    public PlayerSaveConfig(VenturePlugin plugin, String fileName)
    {
        super(plugin, fileName);
    }

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
