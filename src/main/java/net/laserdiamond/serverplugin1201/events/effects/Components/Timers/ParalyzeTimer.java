package net.laserdiamond.serverplugin1201.events.effects.Components.Timers;

import net.laserdiamond.serverplugin1201.Management.messages.messages;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

public class ParalyzeTimer {

    public static HashMap<UUID, Double> paralyzeTimer;
    public static HashMap<UUID, Integer> paralyzeAmp;

    public static void setupTimer() {
        paralyzeTimer = new HashMap<>();
        paralyzeTimer = new HashMap<>();
    }

    public static void setTimer(LivingEntity livingEntity, int seconds, int amplifier) {
        double duration = System.currentTimeMillis() + (seconds * 1000);
        livingEntity.sendMessage(messages.paralyzeMessage(seconds));
        paralyzeTimer.put(livingEntity.getUniqueId(), duration);
        paralyzeAmp.put(livingEntity.getUniqueId(), amplifier);

        while (paralyzeAmp.get(livingEntity.getUniqueId()) == 0 && paralyzeAmp.get(livingEntity.getUniqueId()) > 0) {
            paralyzeAmp.put(livingEntity.getUniqueId(), 0);
        }
    }

    public static Double getDuration(UUID uuid) {
        if (paralyzeTimer.containsKey(uuid)) {
            return paralyzeTimer.get(uuid);
        }
        return 0.0;
    }

    public static boolean hasNoEffect(LivingEntity livingEntity) {
        if (!paralyzeTimer.containsKey(livingEntity.getUniqueId()) || paralyzeTimer.get(livingEntity.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
