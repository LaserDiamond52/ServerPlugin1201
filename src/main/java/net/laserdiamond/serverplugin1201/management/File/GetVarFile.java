package net.laserdiamond.serverplugin1201.management.File;

public interface GetVarFile extends FileLoader {

    // Methods to get variables from Config Files
    Double getDouble(String path);
    Integer getInt(String path);
    String getString(String path);
    Boolean getBoolean(String path);

}
