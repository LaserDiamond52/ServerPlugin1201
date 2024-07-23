package net.laserdiamond.ventureplugin.util.File;

public interface FileSaver extends FileLoader {

    /** Inherit FileLoaded for this to work properly
     *  <p>Add saveConfig, because not all config files will need to be saved!
     */
    void saveConfig();
}
