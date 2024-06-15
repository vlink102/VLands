package me.vlink102.vlands.network;

import me.activated.core.api.player.GlobalPlayer;
import me.activated.core.api.player.PlayerData;
import me.activated.core.plugin.AquaCoreAPI;

import java.util.List;
import java.util.UUID;

public class PlayerUtils {
    private final AquaCoreAPI aquaCoreAPI;
    private final VLands plugin;

    public PlayerUtils(VLands plugin) {
        this.plugin = plugin;
        this.aquaCoreAPI = plugin.getAquaCoreAPI();
    }

    public PlayerData getPlayerData(UUID uuid) {
        return aquaCoreAPI.getPlayerData(uuid);
    }

    public GlobalPlayer getGlobalPlayer(UUID uuid) {
        return aquaCoreAPI.getGlobalPlayer(uuid);
    }
}
