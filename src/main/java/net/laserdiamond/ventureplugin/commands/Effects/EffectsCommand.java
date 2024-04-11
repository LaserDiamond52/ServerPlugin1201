package net.laserdiamond.ventureplugin.commands.Effects;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.effects.Managers.EffectManager;
import net.laserdiamond.ventureplugin.util.EffectKeys;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EffectsCommand implements CommandExecutor, TabExecutor {

    private VenturePlugin plugin;
    private EffectManager effectManager;

    public EffectsCommand(VenturePlugin plugin) {
        this.plugin = plugin;
        effectManager = plugin.getEffectManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission(Permissions.EFFECT.getPermission())) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please specify a target");
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Please specify an effect");

            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputEffect = args[1];

                if (target != null) {
                    giveEffect(sender, target, inputEffect, 10, 0, true);

                    /*
                    try {
                        EffectKeys effectKey = EffectKeys.fromString(inputEffect);
                        //giveEffect(sender, target, effectKey, 10);
                    } catch (CommandException exception) {
                        sender.sendMessage(ChatColor.RED + "Not an effect");
                    }

                     */

                }

            } else if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputEffect = args[1];
                String inputDuration = args[2];

                if (target != null) {
                    try {
                        int duration = Integer.parseInt(inputDuration);
                        giveEffect(sender, target, inputEffect, duration, 0, true);
                    } catch (NumberFormatException exception) {
                        if (inputDuration.equals("clear")) {
                            sender.sendMessage(ChatColor.AQUA + "Clear effect: " + inputEffect);
                        } else {
                            target.sendMessage(ChatColor.RED + "Please input an integer");
                        }
                    }

                }
            } else if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputEffect = args[1];
                String inputDuration = args[2];
                String inputAmplifier = args[3];


            } else if (args.length == 5) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputEffect = args[1];
                String inputDuration = args[2];
                String inputAmplifier = args[3];
                String inputForce = args[4];



            }
        } else {
            sender.sendMessage(Messages.notAllowedCommand());
        }

        return true;
    }

    private void giveEffect(CommandSender sender, Player target, String inputName, int duration, int amplifier, boolean particles) {

        try {
            try {
                String vanillaEffect = ofVanillaEffectName(inputName);
                target.sendMessage(ChatColor.GREEN + vanillaEffect);

                PotionEffectType vanillaEffectToAdd = getVanillaEffect(inputName);
                target.addPotionEffect(new PotionEffect(vanillaEffectToAdd, duration * 20, amplifier, particles));
            } catch (IllegalArgumentException exception) {
                try {
                    String customEffect = ofCustomEffectName(inputName);

                    HashMap<UUID, Double> effectDuration = getCustomEffectDuration(inputName);
                    HashMap<UUID, Integer> effectAmplifier = getCustomEffectAmplifier(inputName);

                    effectDuration.put(target.getUniqueId(), (double) duration);
                    effectAmplifier.put(target.getUniqueId(), amplifier + 1);


                    double currentDuration = effectDuration.get(target.getUniqueId());
                    int currentAmplifier = effectAmplifier.get(target.getUniqueId());

                    target.sendMessage(ChatColor.DARK_RED + customEffect + " Duration: " + currentDuration + " Amplifier: " + currentAmplifier);

                } catch (IllegalArgumentException exception1) {
                    sender.sendMessage(ChatColor.RED + "Not an effect: " + inputName);
                }
            }

            //target.sendMessage(string);
        } catch (IllegalArgumentException exception) {

            sender.sendMessage(ChatColor.RED + "Not an effect: " + inputName);

        }
    }

    private final List<String> effectNames = new ArrayList<>();
    {
        for (PotionEffectType potionEffectType : PotionEffectType.values()) {
            String name = potionEffectType.getKey().getKey();
            effectNames.add(name);
        }

        for (EffectKeys effectKeys : EffectKeys.values()) {
            String name = effectKeys.getCommandName();
            if (!name.equals(EffectKeys.GOD.getCommandName())) {
                effectNames.add(name);
            }

        }

    }

    // TODO: USE THIS TO DETERMINE EFFECT TO GIVE!!!!

    private String ofVanillaEffectName(String input) {

        for (PotionEffectType potionEffectType : PotionEffectType.values()) {
            String keyName = potionEffectType.getKey().getKey();

            if (input.equals(keyName)) {
                return keyName;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    private PotionEffectType getVanillaEffect(String input) {

        for (PotionEffectType potionEffectType : PotionEffectType.values()) {
            String keyName = potionEffectType.getKey().getKey();

            if (input.equals(keyName)) {
                return potionEffectType;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    private String ofCustomEffectName(String input) {

        for (EffectKeys effectKeys : EffectKeys.values()) {
            String keyName = effectKeys.getCommandName();

            if (input.equals(keyName) && !input.equals(EffectKeys.GOD.getCommandName())) {
                return keyName;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    private HashMap<UUID, Double> getCustomEffectDuration(String input) {

        for (EffectKeys effectKeys : EffectKeys.values()) {
            String keyName = effectKeys.getCommandName();

            if (input.equals(keyName) && !input.equals(EffectKeys.GOD.getCommandName())) {
                return effectKeys.getEffectDurationMap();
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    private HashMap<UUID, Integer> getCustomEffectAmplifier(String input) {

        for (EffectKeys effectKeys : EffectKeys.values()) {
            String keyName = effectKeys.getCommandName();

            if (input.equals(keyName) && !input.equals(EffectKeys.GOD.getCommandName())) {
                return effectKeys.getEffectAmpMap();
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();
        if (sender.hasPermission(Permissions.EFFECT.getPermission())) {

            //Bukkit.broadcastMessage("Command arg length: " + args.length);

            if (args.length == 1) {
                // TODO: Offer target
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String playerName = player.getName();
                    argsList.add(playerName);
                }
            } else if (args.length == 2) {
                // TODO: Offer effect

                // Add all vanilla potion effects
                for (PotionEffectType potionEffectType : PotionEffectType.values()) {
                    String potionName = potionEffectType.getKey().getKey();
                    argsList.add(potionName);
                }


                // Add all plugin effects EXCEPT for God Mode
                for (EffectKeys effectKeys : EffectKeys.values()) {
                    String effectName = effectKeys.getCommandName();

                    if (!effectName.equals(EffectKeys.GOD.getCommandName())) {
                        argsList.add(effectName);
                    }

                }

            } else if (args.length == 3) {
                // TODO: Duration or Clear
                argsList.add("duration");
                argsList.add("clear");
            }

            else if (args.length == 4) {
                // TODO: Amplifier

                argsList.add("amplifier");

            }

            /*
            else if (args.length == 5) {

                for (PotionEffectType effectType : PotionEffectType.values()) {
                    String argName = effectType.getName();
                    if (argName.equals(args[1])) {
                        argsList.add("true");
                        argsList.add("false");
                    } else {
                        return new ArrayList<>();
                    }
                }
            }

             */

        } else  {
            return new ArrayList<>();
        }

        return argsList;
    }
}
