package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorCMD;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public final class FleshRevenantArmor extends VentureArmorSet implements AbilityCasting.onKillAbility {
    public FleshRevenantArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
    }

    @Override
    protected ArmorConfig config() {
        return plugin.getFleshRevenantArmorConfig();
    }

    @Override
    protected String armorName() {
        return "Flesh Revenant";
    }

    @Override
    public ArmorCMD armorCMD() {
        return ArmorCMD.FLESH_HORROR;
    }

    @Override
    protected Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
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
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.RARE;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double lifeLeechAmt = config().getDouble("lifeLeechAmount");
        String abilityName = config().getString("abilityName");

        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Upon killing a mob/player, instantly");
        lore.add(ChatColor.GRAY + "regain " + ChatColor.RED + lifeLeechAmt + ChatColor.GRAY + "% of your max " + ChatColor.RED + "❤");
        lore.add(" ");
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));
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
