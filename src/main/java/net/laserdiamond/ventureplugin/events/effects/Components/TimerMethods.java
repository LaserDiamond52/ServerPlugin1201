package net.laserdiamond.ventureplugin.events.effects.Components;

import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public interface TimerMethods {


    void setUpTimer();

    void setEffectTimer(LivingEntity livingEntity, int seconds, int amplifier);

    Double getRemainingTime(UUID uuid);

    Integer getEffectAmplifier(UUID uuid);

    boolean timerDone(LivingEntity livingEntity);

}
