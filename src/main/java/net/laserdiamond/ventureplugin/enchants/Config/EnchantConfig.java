package net.laserdiamond.ventureplugin.enchants.Config;

import net.laserdiamond.ventureplugin.util.Config.MiscConfig;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EnchantConfig extends MiscConfig {


    public EnchantConfig(VenturePlugin plugin, String folderName, String fileName) {
        super(plugin, folderName, fileName);
    }
}
