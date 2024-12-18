package net.ilovepackets.packetapi.player;

import lombok.AllArgsConstructor;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author (monzabi)
 * @date (11 / 12 / 2024)
 * @class (PacketPlayer)
 */

@AllArgsConstructor
public class PacketPlayer {


    private final Player player;


    public void sendPacket(final Packet<?> packet) {
        final CraftPlayer craftPlayer = (CraftPlayer) this.player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }

}
