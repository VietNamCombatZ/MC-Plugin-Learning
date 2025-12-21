package project01.learningProject01.listeners;

import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class CrawlListener implements Listener {

    private final HashMap<UUID, Integer> lastSneakTime = new HashMap<>();

    private static long DOUBLE_SHIFT_TIME = 400; // 400 milliseconds
    private static int MAX_DISTANCE_TO_TARGET_BLOCK = 3;
    private static double TELEPORT_SPEED = 0.2;

    @EventHandler
    public void onSneak (PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        if(!event.isSneaking()) {
            return;
        }

        UUID playerID = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if(lastSneakTime.containsKey(playerID)) {
            long lastTime = lastSneakTime.get(playerID);
            if(currentTime - lastTime <= DOUBLE_SHIFT_TIME) {
                // Double shift detected
                tryCrawl(player);
            }

        }
        lastSneakTime.put(playerID, (int) currentTime);

    }

    private void tryCrawl(Player player) {

        //check if player is looking at stone bricks within max distance
        Block target = player.getTargetBlockExact(MAX_DISTANCE_TO_TARGET_BLOCK);

        if(target == null) {return;}

        if(target.getType() != Material.STONE_BRICKS) {return;}

        //set player to swimming pose
        player.setSwimming(true);


        // teleport player slightly forward and down
        Vector direction = player.getLocation().getDirection().normalize().multiply(TELEPORT_SPEED);
        player.setVelocity(direction);

        // teleport player to target block location + offset
        Location crawlLocation = target.getLocation().clone().add(0.5, -1, 0.5);
        player.teleport(crawlLocation);

    }

}
