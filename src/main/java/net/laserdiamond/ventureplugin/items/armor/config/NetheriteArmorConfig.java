package net.laserdiamond.ventureplugin.items.armor.config;

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

public class NetheriteArmorConfig extends ArmorConfig {
    public NetheriteArmorConfig(VenturePlugin plugin, String fileName) {
        super(plugin, fileName);
    }
}
