package net.laserdiamond.ventureplugin.enchants.Components;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EnchantmentWrapper extends Enchantment {

    private final String name;
    private final int maxLvl;
    private final int startLvl;
    private final EnchantmentTarget enchantmentTarget;
    private final boolean isDiscoverable;
    private List<Enchantment> conlfictingEnchants = new ArrayList<>();

    public EnchantmentWrapper(String namespace, String name, int startLvl, int maxLvl, EnchantmentTarget enchantmentTarget, boolean isDiscoverable) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.startLvl = startLvl;
        this.maxLvl = maxLvl;
        this.enchantmentTarget = enchantmentTarget;
        this.isDiscoverable = isDiscoverable;
    }

    public EnchantmentWrapper(String namespace, String name, int startLvl, int maxLvl, EnchantmentTarget enchantmentTarget, boolean isDiscoverable, List<Enchantment> conflictingEnchants) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.startLvl = startLvl;
        this.maxLvl = maxLvl;
        this.enchantmentTarget = enchantmentTarget;
        this.isDiscoverable = isDiscoverable;
        this.conlfictingEnchants = conflictingEnchants;
        // TODO Auto-generated constructor stub
    }


    public Enchantment getEnchantment() {
        return Enchantment.getByKey(getKey());
    }



    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return startLvl;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return enchantmentTarget;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {

        for (Enchantment enchantment : conlfictingEnchants) {
            if (other.equals(enchantment)) {
                return true;
            }
        }
        return false;
        //return getEnchantment().conflictsWith(other);
        //return true;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return true;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return null;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return isDiscoverable;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }

    @Override
    public @NotNull Key key() {
        return super.key();
    }
}
