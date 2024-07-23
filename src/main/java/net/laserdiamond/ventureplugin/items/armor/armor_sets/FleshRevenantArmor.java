package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorMaterial;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import org.bukkit.ChatColor;
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
    protected String armorName() {
        return "Flesh Revenant";
    }

    @Override
    public VentureArmorMaterial ventureArmorMaterial() {
        return VentureArmorMaterial.FLESH_HORROR;
    }

    @Override
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.RARE;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double lifeLeechAmt = getArmorConfig().getDouble("lifeLeechAmount");
        String abilityName = getArmorConfig().getString("abilityName");

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
        double lifeStealAmount = maxHealth * getArmorConfig().getDouble("lifeLeechAmount") * 0.01;
        double currentHealth = player.getHealth();

        player.setHealth(Math.min(currentHealth + lifeStealAmount, maxHealth));
    }
}
