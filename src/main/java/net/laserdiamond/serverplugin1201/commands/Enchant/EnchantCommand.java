package net.laserdiamond.serverplugin1201.commands.Enchant;

import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.UpdateItem;
import net.laserdiamond.serverplugin1201.management.messages.messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand implements CommandExecutor, TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("serverplugin1201.enchant")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please specify a target");
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Please specify an enchantment");

            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputEnchantName = args[1];
                if (target != null) {
                    enchant(sender, target, inputEnchantName, 1);
                }


            } else if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputEnchantName = args[1];
                String inputLevel = args[2];
                if (target != null) {
                    try {
                        int level = Integer.parseInt(inputLevel);
                        enchant(sender, target, inputEnchantName, level);
                    } catch (NumberFormatException exception) {
                        sender.sendMessage(ChatColor.RED + "Please input an integer for the enchantment level");
                    }
                }
            }
        } else {
            sender.sendMessage(messages.notAllowedCommand());
        }

        return true;
    }

    private void enchant(CommandSender sender, Player target, String input, int levelInput) {

        try {
            Enchantment enchantToAdd = EnchantsClass.EnchantEnum.of(input);
            ItemStack mainHand = target.getInventory().getItemInMainHand();
            if (enchantToAdd.getItemTarget().includes(mainHand)) {
                ItemForger mainHandForger = new ItemForger(mainHand);
                int finalLevel = Math.min(enchantToAdd.getMaxLevel(), levelInput);
                mainHandForger.addEnchant(enchantToAdd, finalLevel);
                mainHandForger.setLore(UpdateItem.renewLore(mainHandForger.toItemStack()));
                target.getInventory().setItemInMainHand(mainHandForger.toItemStack());
            } else {
                sender.sendMessage(ChatColor.RED + "Cannot apply " + input + " to " + mainHand.getType());
            }
        } catch (IllegalArgumentException exception) {
            sender.sendMessage(ChatColor.RED + "Not an enchantment: " + input);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();
        if (sender.hasPermission("serverplugin1201.enchant")) {

            if (args.length == 1) {
                // TODO: Offer target
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String playerName = player.getName();
                    argsList.add(playerName);
                }
            } else if (args.length == 2) {
                // TODO: Offer enchant
                for (EnchantsClass.EnchantEnum enchantEnum : EnchantsClass.EnchantEnum.values()) {
                    String enchantArgName = enchantEnum.getCommandName();
                    argsList.add(enchantArgName);

                }

            } else if (args.length == 3) {
                // TODO: Offer enchant level
                for (EnchantsClass.EnchantEnum enchantEnum : EnchantsClass.EnchantEnum.values()) {
                    String argName = enchantEnum.getCommandName();

                    if (argName.equals(args[1])) {
                        Enchantment enchantment = enchantEnum.getEnchantment();
                        int maxLvl = enchantment.getMaxLevel();
                        int startLvl = enchantment.getStartLevel() - 1;
                        for (int i = maxLvl; i > startLvl; i--) {
                            argsList.add("" + i);
                        }
                    }
                }
            }

        } else {
            return new ArrayList<>();
        }
        return argsList;
    }
}
