package project01.learningProject01.gravitygun.listener;

import project01.learningProject01.gravitygun.GravityGunItem;
import project01.learningProject01.gravitygun.nms.GlowPacketUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GravityGlowListener implements Listener {

    private final JavaPlugin plugin;

    // Player UUID -> Entity UUID đang được glow
    private final Map<UUID, UUID> glowingEntity = new HashMap<>();

    public GravityGlowListener(JavaPlugin plugin) {
        this.plugin = plugin;
        startTask();
    }

    private void startTask() {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    // Không cầm súng → bỏ glow
                    if (!GravityGunItem.isGravityGun(
                            plugin,
                            player.getInventory().getItemInMainHand()
                    )) {
                        clearGlow(player);
                        continue;
                    }

                    // Ray trace tìm Zombie trước mặt
                    RayTraceResult result = player.getWorld().rayTraceEntities(
                            player.getEyeLocation(),
                            player.getEyeLocation().getDirection(),
                            5,
                            entity -> entity instanceof Zombie
                    );

                    if (result == null || !(result.getHitEntity() instanceof Zombie zombie)) {
                        clearGlow(player);
                        continue;
                    }

                    UUID playerUUID = player.getUniqueId();
                    UUID zombieUUID = zombie.getUniqueId();

                    // Nếu đang glow đúng con này → không làm gì
                    if (zombieUUID.equals(glowingEntity.get(playerUUID)))
                        continue;

                    // Glow entity mới
                    clearGlow(player);
                    GlowPacketUtil.setGlow(player, zombie, true);
                    glowingEntity.put(playerUUID, zombieUUID);
                }
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }

    private void clearGlow(Player player) {

        UUID entityUUID = glowingEntity.remove(player.getUniqueId());
        if (entityUUID == null) return;

        Entity entity = player.getWorld().getEntity(entityUUID);
        if (entity != null) {
            GlowPacketUtil.setGlow(player, entity, false);
        }
    }
}
