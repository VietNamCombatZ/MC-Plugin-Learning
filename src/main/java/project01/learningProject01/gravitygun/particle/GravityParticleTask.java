package project01.learningProject01.gravitygun.particle;

import project01.learningProject01.gravitygun.GravityGunItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GravityParticleTask {

    private final JavaPlugin plugin;

    public GravityParticleTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (!GravityGunItem.isGravityGun(
                            plugin,
                            player.getInventory().getItemInMainHand()
                    )) continue;

                    spawnCircle(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 4L); // mỗi 4 tick
    }

    private void spawnCircle(Player player) {

        Location center = player.getLocation().clone().add(0, 0.1, 0);
        double radius = plugin.getConfig().getDouble("particle-radius", 1.2);

        for (int i = 0; i < 360; i += 20) {

            double angle = Math.toRadians(i);

            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;

            Location particleLoc = center.clone().add(x, 0, z);

            player.getWorld().spawnParticle(
                    Particle.FLAME,
                    particleLoc,
                    1,
                    0, 0, 0,
                    0
            );
        }
    }
}
