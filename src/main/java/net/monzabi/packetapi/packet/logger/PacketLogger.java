package net.monzabi.packetapi.packet.logger;

import net.minecraft.server.v1_8_R3.Packet;
import net.monzabi.packetapi.PacketAPI;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;

/**
 * @author (monzabi)
 * @date (11 / 12 / 2024)
 * @class (PacketLogger)
 */
public class PacketLogger {

    private final Packet<?> packet;
    private final Player player;

    public PacketLogger(final Packet<?> packet, final Player player) {
        this.packet = packet;
        this.player = player;
    }

    public final void logPacket() throws Exception {
        final File file = new File(PacketAPI.getINSTANCE().getDataFolder() + "packetlogger.yml");
        final String packetName = this.packet.getClass().getSimpleName();
        final FileWriter fileWriter = new FileWriter(file);

        if (!file.exists()) file.mkdir();

        fileWriter.write("[" + this.player.getName() + "] " + " » " + packetName + " « ");


        // Check if the file is old and if yes create a new
        if (file.length() >= 1L) {
            file.createNewFile();
        }
    }
}
