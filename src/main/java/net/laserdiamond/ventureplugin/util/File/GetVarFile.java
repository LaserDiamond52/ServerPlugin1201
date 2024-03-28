package net.laserdiamond.ventureplugin.util.File;

import java.util.List;

/**
 * Contains methods to get variables from config file
 */
public interface GetVarFile extends FileLoader {

    Double getDouble(String path);
    Integer getInt(String path);
    List<Integer> getIntList(String path);
    List<Double> getDoubleList(String path);
    List<String> getStringList(String path);
    String getString(String path);
    Boolean getBoolean(String path);

}
