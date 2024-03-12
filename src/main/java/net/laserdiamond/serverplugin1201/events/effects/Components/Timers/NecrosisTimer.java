package net.laserdiamond.serverplugin1201.events.effects.Components.Timers;

import net.laserdiamond.serverplugin1201.management.messages.Messages;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

public class NecrosisTimer {

    public static HashMap<UUID, Double> necrosisTimer;
    public static HashMap<UUID, Integer> necrosisAmp;

    public static void setupTimer() {
        necrosisTimer = new HashMap<>();
        necrosisAmp = new HashMap<>();
    }

    public static void setTimer(LivingEntity livingEntity, int seconds, int amplifier) {
        double duration = System.currentTimeMillis() + (seconds * 1000);
        livingEntity.sendMessage(Messages.necrosisMessage(seconds));
        necrosisTimer.put(livingEntity.getUniqueId(), duration);
        necrosisAmp.put(livingEntity.getUniqueId(), amplifier);

        while (necrosisTimer.get(livingEntity.getUniqueId()) == 0 && necrosisAmp.get(livingEntity.getUniqueId()) > 0) {
            necrosisAmp.put(livingEntity.getUniqueId(), 0);
        }
    }

    public static Double getDuration(UUID uuid) {
        if (necrosisTimer.containsKey(uuid)) {
            return necrosisTimer.get(uuid);
        }
        return 0.0;
    }

    public static Integer getAmplifier(UUID uuid) {
        if (necrosisAmp.containsKey(uuid)) {
            return necrosisAmp.get(uuid);
        }
        return 0;
    }

    public static boolean hasNoEffect(LivingEntity livingEntity) {
        if (!necrosisTimer.containsKey(livingEntity.getUniqueId()) || necrosisTimer.get(livingEntity.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
