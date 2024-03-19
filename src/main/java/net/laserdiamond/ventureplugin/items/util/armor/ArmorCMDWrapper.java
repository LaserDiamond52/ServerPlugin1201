package net.laserdiamond.ventureplugin.items.util.armor;

public class ArmorCMDWrapper {

    private Integer helmet, chestplate, leggings, boots;

    public ArmorCMDWrapper(Integer helmet, Integer chestplate, Integer leggings, Integer boots)
    {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public Integer getHelmet() {
        return helmet;
    }

    public void setHelmet(Integer helmet) {
        this.helmet = helmet;
    }

    public Integer getChestplate() {
        return chestplate;
    }

    public void setChestplate(Integer chestplate) {
        this.chestplate = chestplate;
    }

    public Integer getLeggings() {
        return leggings;
    }

    public void setLeggings(Integer leggings) {
        this.leggings = leggings;
    }

    public Integer getBoots() {
        return boots;
    }

    public void setBoots(Integer boots) {
        this.boots = boots;
    }
}
