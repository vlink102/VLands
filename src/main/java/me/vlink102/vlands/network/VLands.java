package me.vlink102.vlands.network;

import lombok.Getter;
import me.activated.core.plugin.AquaCoreAPI;
import me.vlink102.vlands.network.commands.ChatCopy;
import me.vlink102.vlands.network.commands.Tab;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class VLands extends JavaPlugin implements CommandExecutor {

    @Getter
    private AquaCoreAPI aquaCoreAPI;
    @Getter
    private PlayerUtils playerUtils;

    @Getter
    private static me.vlink102.vlands.network.Command.MainMenu menu;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.aquaCoreAPI = AquaCoreAPI.INSTANCE;
        this.playerUtils = new PlayerUtils(this);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();
            menu = new me.vlink102.vlands.network.Command.MainMenu();
            getCommand("vccb").setExecutor(new ChatCopy());
            getServer().getScheduler().scheduleSyncDelayedTask(this, () -> getCommand("help").setExecutor(this));
        }
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length > 0) {
            switch (args[0]) {
                case "id" -> {
                    if (args.length == 2) {
                        int ID = Integer.parseInt(args[1]);
                        me.vlink102.vlands.network.Command.sendHelpMenuByID(player, ID);
                    }
                }
                case "tab" -> {
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "mysql", "sql" -> me.vlink102.vlands.network.Command.sendHelpMenu(player, 0, 0);
                            case "scoreboard", "sb", "board" -> me.vlink102.vlands.network.Command.sendHelpMenu(player, 0, 1);
                            case "bossbar", "bar", "boss", "bb" -> me.vlink102.vlands.network.Command.sendHelpMenu(player, 0, 2);
                            case "nametag", "tag", "tags", "nametags" -> me.vlink102.vlands.network.Command.sendHelpMenu(player, 0, 3);
                        }
                    } else {
                        me.vlink102.vlands.network.Command.sendHelpMenu(player, 0);
                    }
                }
                case "quests", "q", "quest", "goals", "goal", "challenges", "challenge" ->
                        me.vlink102.vlands.network.Command.sendHelpMenu(player, 1);
                case "advancements", "av", "adv", "achievements", "achieve", "advancement", "achievement" ->
                        me.vlink102.vlands.network.Command.sendHelpMenu(player, 2);
                case "staff", "admin" -> me.vlink102.vlands.network.Command.sendHelpMenu(player, 3);
            }
        } else {
            me.vlink102.vlands.network.Command.sendHelpMenu(player);
        }

        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
