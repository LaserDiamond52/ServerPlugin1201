package net.laserdiamond.ventureplugin.util;

public class ItemRegistryKey {

    private final int customModelData;
    private int stars;


    public ItemRegistryKey(int customModelData, int stars) {
        this.customModelData = customModelData;
        this.stars = stars;
    }

    public ItemRegistryKey(int customModelData)
    {
        this.customModelData = customModelData;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getStars() {
        return stars;
    }
}
