package project01.learningProject01.gravitygun.nms;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GlowPacketUtil {

    private static final ProtocolManager manager =
            ProtocolLibrary.getProtocolManager();

    public static void setGlow(Player viewer, Entity entity, boolean glow) {

        PacketContainer packet = manager.createPacket(
                com.comphenix.protocol.PacketType.Play.Server.ENTITY_METADATA
        );

        packet.getIntegers().write(0, entity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher(entity);

        byte mask = glow ? (byte) 0x40 : (byte) 0x00;
        watcher.setObject(
                new WrappedDataWatcher.WrappedDataWatcherObject(
                        0,
                        WrappedDataWatcher.Registry.get(Byte.class)
                ),
                mask
        );

        packet.getWatchableCollectionModifier()
                .write(0, watcher.getWatchableObjects());

        try {
            manager.sendServerPacket(viewer, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
