package net.laserdiamond.ventureplugin.util.Config;

import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public abstract class ConfigLoader implements GetVarFile {

    public final FileConfiguration config = new YamlConfiguration();

    @Override
    public final FileConfiguration getConfig() {
        return config;
    }

    @Override
    public final Double getDouble(String path)
    {
        return config.getDouble(path);
    }

    @Override
    public final Integer getInt(String path)
    {
        return config.getInt(path);
    }

    @Override
    public final String getString(String path)
    {
        return config.getString(path);
    }

    @Override
    public final Boolean getBoolean(String path)
    {
        return config.getBoolean(path);
    }

    @Override
    public final List<Integer> getIntList(String path) {
        return config.getIntegerList(path);
    }

    @Override
    public final List<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    @Override
    public final List<String> getStringList(String path) {
        return config.getStringList(path);
    }
}
