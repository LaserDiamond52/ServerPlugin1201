package net.laserdiamond.ventureplugin.items.util;

import org.bukkit.ChatColor;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class VentureItemRarity implements PersistentDataType<String, VentureItemRarity.Rarity> {


    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Rarity> getComplexType() {
        return Rarity.class;
    }

    @Override
    public @NotNull String toPrimitive(@NotNull Rarity complex, @NotNull PersistentDataAdapterContext context) {
        return complex.getRarity();
    }

    @Override
    public @NotNull VentureItemRarity.Rarity fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        return Rarity.ofRarity(primitive);
    }

    public enum Rarity {

        COMMON (ChatColor.GREEN, "common", ChatColor.GREEN + "Common"),
        RARE (ChatColor.BLUE, "rare", ChatColor.BLUE + "Rare"),
        EPIC (ChatColor.DARK_PURPLE, "epic", ChatColor.DARK_PURPLE + "Epic"),
        LEGENDARY (ChatColor.GOLD, "legendary", ChatColor.GOLD + "Legendary"),
        FABLED (ChatColor.RED, "fabled", ChatColor.RED + "Fabled"),
        MYTHICAL (ChatColor.AQUA, "mythical", ChatColor.AQUA + "Mythical"),
        FORBIDDEN (ChatColor.LIGHT_PURPLE, "forbidden", ChatColor.LIGHT_PURPLE + "Forbidden");

        private final ChatColor rarityColor;
        private final String rarity;
        private final String displayName;

        Rarity(ChatColor rarityColor, String rarity, String displayName) {
            this.rarityColor = rarityColor;
            this.rarity = rarity;
            this.displayName = displayName;
        }

        public static String ofString(String inputName) {
            for (Rarity rarityType : values()) {
                if (rarityType.rarity.equals(inputName)) {
                    return rarityType.rarity;
                }
            }
            throw new IllegalArgumentException(ChatColor.RED + "No such rarity: " + inputName);
        }

        public static Rarity ofRarity(String inputName) {
            for (Rarity rarityType : values()) {
                if (rarityType.rarity.equals(inputName)) {
                    return rarityType;
                }
            }
            throw new IllegalArgumentException(ChatColor.RED + "No such rarity: " + inputName);
        }

        public ChatColor getRarityColor() {
            return rarityColor;
        }

        public String getRarity() {
            return rarity;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
