package net.laserdiamond.serverplugin1201.events.effects;

import net.laserdiamond.serverplugin1201.management.EffectKeys;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EffectBoolean {

    public static boolean hasEffect(LivingEntity livingEntity, EffectKeys key) {
        PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
        return (pdc.get(key.getKey(), PersistentDataType.BOOLEAN) != null &&
                pdc.has(key.getKey(), PersistentDataType.BOOLEAN) &&
                pdc.get(key.getKey(), PersistentDataType.BOOLEAN).equals(true));
    }

    public static boolean hasEffectKey(LivingEntity livingEntity, EffectKeys keys) {
        PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
        return pdc.get(keys.getKey(), PersistentDataType.BOOLEAN) != null &&
                pdc.has(keys.getKey(), PersistentDataType.BOOLEAN);
    }

    public static void setEffectBoolean(LivingEntity livingEntity, EffectKeys key, boolean setEffect) {
        PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
        pdc.set(key.getKey(), PersistentDataType.BOOLEAN, setEffect);
    }
}
