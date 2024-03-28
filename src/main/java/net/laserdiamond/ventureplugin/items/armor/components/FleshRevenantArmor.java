package net.laserdiamond.ventureplugin.items.armor.components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class FleshRevenantArmor extends VentureArmorSet implements AbilityCasting.onKillAbility {
    public FleshRevenantArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
    }

    @Override
    public ArmorConfig config() {
        return plugin.getFleshRevenantArmorConfig();
    }

    @Override
    public String armorSetName() {
        return "Flesh Revenant";
    }

    @Override
    public ArmorCMD getArmorCMD() {
        return ArmorCMD.FLESH_HORROR;
    }

    @Override
    public Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
        Material material = null;
        switch (armorPieceTypes)
        {
            case HELMET -> material = Material.PLAYER_HEAD;
            case CHESTPLATE -> material = Material.LEATHER_CHESTPLATE;
            case LEGGINGS -> material = Material.LEATHER_LEGGINGS;
            case BOOTS -> material = Material.LEATHER_BOOTS;
        }
        return material;
    }

    @Override
    public VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.RARE;
    }

    @Override
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double lifeLeechAmt = config().getDouble("lifeLeechAmount");
        String abilityName = config().getString("abilityName");

        List<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Upon killing a mob/player, instantly");
        lore.add(ChatColor.GRAY + "regain " + ChatColor.RED + lifeLeechAmt + ChatColor.GRAY + "% of your max " + ChatColor.RED + "❤");
        lore.add(" ");
        return lore;
    }

    @AbilityHandler(abilityCastType = AbilityCastType.ON_KILL)
    @Override
    public void onKill(Player player, LivingEntity killedEntity) {
        double maxHealth = player.getMaxHealth();
        double lifeStealAmount = maxHealth * config().getDouble("lifeLeechAmount") * 0.01;
        double currentHealth = player.getHealth();

        player.setHealth(Math.min(currentHealth + lifeStealAmount, maxHealth));
    }
}
