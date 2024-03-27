package net.laserdiamond.ventureplugin.util.File;

import java.util.List;

public interface GetVarFile extends FileLoader {

    /**
     * Methods to get variables from Config Files
     * @param path
     * @return
     */
    Double getDouble(String path);
    Integer getInt(String path);
    List<Integer> getIntList(String path);
    String getString(String path);
    Boolean getBoolean(String path);

}
