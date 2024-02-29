package net.laserdiamond.serverplugin1201.events.effects.Components.Timers;

import net.laserdiamond.serverplugin1201.management.messages.messages;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.UUID;

public class ManaFreezeTimer {

    public static HashMap<UUID, Double> manaFreezeTimer;
    public static HashMap<UUID, Integer> manaFreezeAmp;

    public static void setupTimer() {
        manaFreezeTimer = new HashMap<>();
        manaFreezeAmp = new HashMap<>();
    }

    public static void setTimer(LivingEntity livingEntity, int seconds, int amplifier) {
        double duration = System.currentTimeMillis() + (seconds * 1000);
        livingEntity.sendMessage(messages.manaFreezeMessage(seconds));
        manaFreezeTimer.put(livingEntity.getUniqueId(), duration);
        manaFreezeAmp.put(livingEntity.getUniqueId(), amplifier);

        while (manaFreezeTimer.get(livingEntity.getUniqueId()) == 0 && manaFreezeAmp.get(livingEntity.getUniqueId()) > 0) {
            livingEntity.sendMessage("Mana freeze gone!");
            manaFreezeAmp.put(livingEntity.getUniqueId(), 0);
        }
    }

    public static Double getDuration(UUID uuid) {
        if (manaFreezeTimer.containsKey(uuid)) {
            return manaFreezeTimer.get(uuid);
        }
        return 0.0;
    }

    public static Integer getAmplifier(UUID uuid) {
        if (manaFreezeAmp.containsKey(uuid)) {
            return manaFreezeAmp.get(uuid);
        }

        return 0;
    }



    public static boolean hasNoEffect(LivingEntity livingEntity) {
        if (!manaFreezeTimer.containsKey(livingEntity.getUniqueId()) || manaFreezeTimer.get(livingEntity.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
