package net.laserdiamond.serverplugin1201.Management.File;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface FileLoader {

    // Load File + get Config

    void loadConfig();
    FileConfiguration getConfig();
}
