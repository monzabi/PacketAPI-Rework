package net.ilovepackets.packetapi.packet.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.Packet;
import net.ilovepackets.packetapi.packet.PacketEvent;
import net.ilovepackets.packetapi.util.INet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author (monzabi)
 * @date (11 / 12 / 2024)
 * @class (PacketInjector)
 */

public class PacketInjector extends MessageToMessageDecoder<Packet<?>> implements INet {

    /**
     * Set the player
     */
    private final Player player;
    @Getter
    private final Map<String, PacketInjector> injection = new HashMap<>();

    public PacketInjector(final Player player) {
        this.player = player;
    }

    /**
     * Set the injector name
     */

    @Setter
    @Getter
    private String decoder_name;
    @Setter
    @Getter
    private String splitter_name;
    @Setter
    @Getter
    private String decompress_name;

    /**
     * Set the channel
     */
    private Channel channel;

    /**
     * @apiNote Inject the player into the decoder
     */

    @Override
    public final void inject() {
        final CraftPlayer craftPlayer = (CraftPlayer) this.player;

        /* Set the channel to craftplayer#gethandle#playerconnection#a */
        this.channel = craftPlayer.getHandle().playerConnection.a().channel;

        // Put the player in the injection hashmap
        this.getInjection().put(this.player.getName(), this);


        /*
         * Add the pipeline
         */

        // Add a try catch to prevent errors

        try {
            if (this.channel.pipeline().get("decoder") != null && this.channel.pipeline().get("splitter") != null && this.channel.pipeline().get("decompress") != null) {
                this.channel.pipeline().addAfter("decoder", this.getDecoder_name(), this);
                this.channel.pipeline().addAfter("splitter", this.getSplitter_name(), this);
                this.channel.pipeline().addAfter("decompress", this.getDecompress_name(), this);
            }
            this.channel.pipeline().addBefore("decoder", decoder_name, this);
            this.channel.pipeline().addBefore("splitter", splitter_name, this);
            this.channel.pipeline().addBefore("decompress", decompress_name, this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Uninject the player from the pipelines ("> elysium_decoder, elysium_splitter, elysium_decompress")
     */
    @Override
    public final void unInject() {
        final CraftPlayer craftPlayer = (CraftPlayer) this.player;
        this.channel = craftPlayer.getHandle().playerConnection.a().channel;

        if (this.channel.pipeline().get(this.getDecoder_name()) != null && this.channel.pipeline().get(this.getSplitter_name()) != null && this.channel.pipeline().get(this.getDecompress_name()) != null) {
            channel.pipeline().remove(this.getDecoder_name());
            channel.pipeline().remove(this.getSplitter_name());
            channel.pipeline().remove(this.getDecompress_name());
        }
        this.getInjection().remove(this.player.getName());
        this.channel.close();
    }

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Packet<?> packet, final List<Object> list) {

        //Call the packet event
        final PacketEvent packetEvent = new PacketEvent(packet, player);
        Bukkit.getPluginManager().callEvent(packetEvent);
        // Cancel the packet
        if (packetEvent.isCancelled()) return;
        // Process the packet
        list.add(packet);
    }
}
