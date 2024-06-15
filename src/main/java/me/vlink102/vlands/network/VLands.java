package me.vlink102.vlands.network;

import lombok.Getter;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VLands extends JavaPlugin {

    @Getter
    private AquaCoreAPI aquaCoreAPI;
    @Getter
    private PlayerUtils playerUtils;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.aquaCoreAPI = AquaCoreAPI.INSTANCE;
        this.playerUtils = new PlayerUtils(this);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
