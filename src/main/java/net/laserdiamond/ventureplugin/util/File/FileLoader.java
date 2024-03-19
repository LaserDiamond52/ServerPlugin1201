package net.laserdiamond.ventureplugin.util.File;

import org.bukkit.configuration.file.FileConfiguration;

public interface FileLoader {

    /**
     * Load File + get Config
     */
    void loadConfig();
    FileConfiguration getConfig();
}
