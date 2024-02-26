package net.laserdiamond.serverplugin1201.management.File;

import org.bukkit.configuration.file.FileConfiguration;

public interface FileLoader {

    // Load File + get Config

    void loadConfig();
    FileConfiguration getConfig();
}
