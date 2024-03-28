package net.laserdiamond.ventureplugin.items.armor.trims.Config;

import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArmorTrimConfig extends ArmorConfig {


    public ArmorTrimConfig(VenturePlugin plugin, String fileName) {
        super(plugin, fileName);
    }
}
