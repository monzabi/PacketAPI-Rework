package net.monzabi.packetapi.packet;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author (monzabi)
 * @date (11 / 12 / 2024)
 * @class (PacketEvent)
 */

public class PacketEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    // Set the packet
    private final Packet<?> packet;
    // Set the player
    @Getter
    private final Player player;
    // Set the boolean to false
    private boolean cancel = false;

    // Add the constructor

    /**
     * @param packet Set the packet
     * @param player Set the player
     */
    public PacketEvent(Packet<?> packet, Player player) {
        this.packet = packet;
        this.player = player;
    }


    // Get the handlers

    /**
     * @return handlers
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    // Get the handler

    /**
     * @return handlers
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    // Get the name of the packet -> PacketPlayInCustomPayload, PacketPlayInWindowClick etc...
    public String getPacketName() {
        return this.packet.getClass().getSimpleName();
    }

    /**
     * @return cancel
     */
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }


    /**
     * @param cancel set the cancel to true/false
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}