package net.laserdiamond.ventureplugin.util.File;

public interface FileLoaderSaver extends FileLoader {

    /** Inherit FileLoaded for this to work properly
     *  Add saveConfig, because not all config files will need to be saved!
     */
    void saveConfig();
}
