package net.laserdiamond.serverplugin1201.events.effects.Components.Timers;

import net.laserdiamond.serverplugin1201.management.messages.Messages;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

public class VulnerableTimer {

    public static HashMap<UUID, Double> vulnerableTimer;
    public static HashMap<UUID, Integer> vulnerableAmp;

    public static void setupTimer() {
        vulnerableTimer = new HashMap<>();
        vulnerableAmp = new HashMap<>();
    }

    public static void setTimer(LivingEntity livingEntity, int seconds, int amplifier) {
        double duration = System.currentTimeMillis() + (seconds * 1000);
        livingEntity.sendMessage(Messages.vulnerableMessage(seconds));
        vulnerableTimer.put(livingEntity.getUniqueId(), duration);
        vulnerableAmp.put(livingEntity.getUniqueId(), amplifier);

        // When timer for effect equals 0 and the amplifier is above 0, set the amplifier back to 0

        // If this doesn't work, just set the amp and make it only matter when the living entity's timer is above 0
        while (vulnerableTimer.get(livingEntity.getUniqueId()) == 0 && vulnerableAmp.get(livingEntity.getUniqueId()) > 0) {
            vulnerableAmp.put(livingEntity.getUniqueId(), 0);
        }
    }

    public static Double getDuration(UUID uuid) {
        if (vulnerableTimer.containsKey(uuid)) {
            return vulnerableTimer.get(uuid);
        }
        return 0.0;
    }

    public static Integer getAmplifier(UUID uuid) {
        if (vulnerableAmp.containsKey(uuid)) {
            return vulnerableAmp.get(uuid);
        }
        return 0;
    }

    public static boolean hasNoEffect(LivingEntity livingEntity) {
        if (!vulnerableTimer.containsKey(livingEntity.getUniqueId()) || vulnerableTimer.get(livingEntity.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
