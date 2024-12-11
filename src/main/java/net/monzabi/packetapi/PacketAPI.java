package net.monzabi.packetapi;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author (monzabi)
 * @date (11 / 12 / 2024)
 * @class (PacketAPI)
 */

public final class PacketAPI extends JavaPlugin {

    @Getter
    @Setter
    private static PacketAPI INSTANCE;

    @Override
    public void onEnable() {
        setINSTANCE(this);
    }
}
