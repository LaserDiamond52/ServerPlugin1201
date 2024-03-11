package net.laserdiamond.serverplugin1201.entities.management;

import org.bukkit.Location;
import org.bukkit.entity.Mob;

public interface MobFabricate {

    Mob createMob(Location location, MobMappings mobMappings);
}
