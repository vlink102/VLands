package me.vlink102.vlands.network;

import lombok.Getter;
import me.activated.core.plugin.AquaCoreAPI;
import me.vlink102.vlands.network.commands.Tab;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class VLands extends JavaPlugin implements CommandExecutor {

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
            new Tab();
            getServer().getScheduler().scheduleSyncDelayedTask(this, () -> getCommand("help").setExecutor(this));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
