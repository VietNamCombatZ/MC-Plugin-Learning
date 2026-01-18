package project01.learningProject01.gravitygun.listener;

import project01.learningProject01.gravitygun.GravityGunItem;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GravityInteractListener implements Listener {

    private final JavaPlugin plugin;

    private final Map<UUID, LivingEntity> holdingEntities = new HashMap<>();

    private static final double MAX_PICKUP_DISTANCE = 5.0;

    public GravityInteractListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // ================= NHẤC / THẢ =================

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR &&
                event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (!GravityGunItem.isGravityGun(
                plugin,
                player.getInventory().getItemInMainHand()
        )) return;

        // Nếu đang giữ entity → THẢ
        if (holdingEntities.containsKey(player.getUniqueId())) {
            releaseEntity(player);
            return;
        }

        // Ray trace Zombie
        Zombie zombie = rayTraceZombie(player);
        if (zombie == null) return;

        liftEntity(player, zombie);
    }

    // ================= NÉM =================

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getAction() != Action.LEFT_CLICK_AIR &&
                event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        if (!GravityGunItem.isGravityGun(
                plugin,
                player.getInventory().getItemInMainHand()
        )) return;

        if (!holdingEntities.containsKey(player.getUniqueId()))
            return;

        throwEntity(player);
    }

    // ================= RAY TRACE =================

    private Zombie rayTraceZombie(Player player) {

        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                MAX_PICKUP_DISTANCE,
                entity -> entity instanceof Zombie
        );

        if (result == null) return null;

        Entity hit = result.getHitEntity();
        if (!(hit instanceof Zombie zombie)) return null;

        return zombie;
    }

    // ================= LOGIC =================

    private void liftEntity(Player player, LivingEntity entity) {

        entity.setAI(false);
        entity.setGravity(false);
        entity.setVelocity(new Vector(0, 0, 0));

        holdingEntities.put(player.getUniqueId(), entity);
        startFollowTask(player, entity);
    }

    private void releaseEntity(Player player) {

        LivingEntity entity = holdingEntities.remove(player.getUniqueId());
        if (entity == null) return;

        entity.setAI(true);
        entity.setGravity(true);
    }

    private void throwEntity(Player player) {

        LivingEntity entity = holdingEntities.remove(player.getUniqueId());
        if (entity == null) return;

        entity.setAI(true);
        entity.setGravity(true);

        double force = plugin.getConfig().getDouble("throw-force", 2.5);
        Vector direction = player.getEyeLocation().getDirection().normalize();

        entity.setVelocity(direction.multiply(force));
    }

    // ================= THEO TẦM NHÌN =================

    private void startFollowTask(Player player, LivingEntity entity) {

        UUID playerId = player.getUniqueId();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (!player.isOnline()
                        || !holdingEntities.containsKey(playerId)
                        || entity.isDead()) {
                    holdingEntities.remove(playerId);
                    cancel();
                    return;
                }

                Vector direction = player.getEyeLocation()
                        .getDirection()
                        .normalize();

                Vector target = player.getEyeLocation()
                        .toVector()
                        .add(direction.multiply(2.0));

                entity.teleport(target.toLocation(player.getWorld()));
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
