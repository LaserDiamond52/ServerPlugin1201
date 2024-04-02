package net.laserdiamond.ventureplugin.util.particles;

import org.bukkit.Location;
import org.bukkit.Particle;


public class Particles {

    /**
     * Creates a 4-way spiral aura with the desired particles
     * @param particle The particle the aura uses
     * @param location The location for the aura
     * @param radius The radius of the particles
     * @param y The y vector of the particles
     */
    public static void spiralAura(Particle particle, Location location, int radius, double[] y)
    {
        Location location2 = location.clone();
        Location location3 = location.clone();
        Location location4 = location.clone();

        double x = radius * Math.cos(y[0]);
        double x2 = -radius * Math.cos(y[0]);

        double z = radius * Math.sin(y[0]);
        double z2 = -radius * Math.sin(y[0]);

        location.getWorld().spawnParticle(particle, location.add(x, y[0], z),0,0,0,0);
        location2.getWorld().spawnParticle(particle, location2.add(x2, y[0], z2),0,0,0,0);
        location3.getWorld().spawnParticle(particle, location3.add(-z, y[0], x),0,0,0,0);
        location4.getWorld().spawnParticle(particle, location4.add(-z2, y[0], x2),0,0,0,0);
    }
}
