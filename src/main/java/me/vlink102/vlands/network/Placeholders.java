package me.vlink102.vlands.network;

import me.activated.core.plugin.AquaCoreAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Placeholders extends PlaceholderExpansion implements Relational {
    private VLands plugin;
    private final AquaCoreAPI aquaCoreAPI;

    public Placeholders(VLands plugin) {
        this.aquaCoreAPI = plugin.getAquaCoreAPI();
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "vlink102";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "vlands";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String getRequiredPlugin() {
        return "VLands";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        List<String> paramArray = Arrays.asList(params.split("_"));
        switch (getParameter(paramArray, 0)) {
            case "util" -> {
                switch (getParameter(paramArray, 1)) {
                    case "math" -> {
                        switch (getParameter(paramArray, 2)) {
                            case "distance" -> {
                                int x = Integer.parseInt(getParameter(paramArray, 3));
                                int x1 = Integer.parseInt(getParameter(paramArray, 6));
                                int y = Integer.parseInt(getParameter(paramArray, 4));
                                int y1 = Integer.parseInt(getParameter(paramArray, 7));
                                int z = Integer.parseInt(getParameter(paramArray, 5));
                                int z1 = Integer.parseInt(getParameter(paramArray, 8));
                                return BigDecimal.valueOf(Math.sqrt((x1 - x) ^ 2 + (y1 - y) ^ 2 + (z1 - z) ^ 2)).setScale(1, RoundingMode.HALF_UP).toPlainString();
                            }
                            case "distancesquared" -> {
                                int x = Integer.parseInt(getParameter(paramArray, 3));
                                int x1 = Integer.parseInt(getParameter(paramArray, 6));
                                int y = Integer.parseInt(getParameter(paramArray, 4));
                                int y1 = Integer.parseInt(getParameter(paramArray, 7));
                                int z = Integer.parseInt(getParameter(paramArray, 5));
                                int z1 = Integer.parseInt(getParameter(paramArray, 8));
                                return BigDecimal.valueOf((x1-x)+(y1-y)+(z1-z)).setScale(1, RoundingMode.HALF_UP).toPlainString();
                            }
                            case "distance2d" -> {
                                int x = Integer.parseInt(getParameter(paramArray, 3));
                                int x1 = Integer.parseInt(getParameter(paramArray, 5));
                                int z = Integer.parseInt(getParameter(paramArray, 4));
                                int z1 = Integer.parseInt(getParameter(paramArray, 6));
                                return BigDecimal.valueOf(Math.sqrt((x1 - x)^2 + (z1 - z)^2)).setScale(1, RoundingMode.HALF_UP).toPlainString();
                            }
                        }
                    }
                    case "time" -> {
                        switch (getParameter(paramArray, 2)) {
                            case "format" -> {
                                switch (getParameter(paramArray, 3)) {
                                    case "ms" -> {
                                        return formatIntoMMSS(Integer.parseInt(getStringAfter(paramArray,3)));
                                    }
                                    case "dhms" -> {
                                        return formatTime(Integer.parseInt(getStringAfter(paramArray, 3)));
                                    }
                                }
                            }
                        }
                    }
                    case "string" -> {
                        switch (getParameter(paramArray, 2)) {
                            case "center" -> {
                                String toCenter = getStringAfter(paramArray, 2);
                                return getCenteredMessage(toCenter);
                            }
                            case "centerfix" -> {
                                String toCenter = getStringAfter(paramArray, 2);
                                return getFixedCenteredMessage(toCenter);
                            }
                            case "blank" -> {
                                return "\n";
                            }
                            case "tab" -> {
                                return "\u0009";
                            }
                            case "trim" -> {
                                String toTrim = getStringAfter(paramArray, 2);
                                return toTrim.trim();
                            }
                            case "caps" -> {
                                String to = getStringAfter(paramArray, 2);
                                return StringUtils.capitaliseAllWords(to);
                            }
                            case "boolean" -> {
                                return niceBoolean(Boolean.parseBoolean(getStringAfter(paramArray, 2)), false);
                            }
                            case "booleanicon" -> {
                                return niceBoolean(Boolean.parseBoolean(getStringAfter(paramArray, 2)), true);
                            }
                        }
                    }

                }
            }
            case "player" -> {
                Player onlinePlayer = Bukkit.getPlayer(player.getUniqueId());
                assert onlinePlayer != null;
                switch (getParameter(paramArray, 1)) {
                    case "swimming" -> {
                        return String.valueOf(onlinePlayer.isSwimming());
                    }
                    case "inwater" -> {
                        return String.valueOf(onlinePlayer.isInWater());
                    }
                    case "inrain" -> {
                        return String.valueOf(onlinePlayer.isInRain());
                    }
                    case "inbubble" -> {
                        return String.valueOf(onlinePlayer.isInBubbleColumn());
                    }
                    case "inlava" -> {
                        return String.valueOf(onlinePlayer.isInLava());
                    }
                    case "onfire" -> {
                        return String.valueOf(onlinePlayer.isVisualFire());
                    }
                    case "isblocking" -> {
                        return String.valueOf(onlinePlayer.isBlocking());
                    }
                    case "issleeping" -> {
                        return String.valueOf(onlinePlayer.isSleeping());
                    }
                    case "issprinting" -> {
                        return String.valueOf(onlinePlayer.isSprinting());
                    }
                    case "issneaking" -> {
                        return String.valueOf(onlinePlayer.isSneaking());
                    }
                    case "isclimbing" -> {
                        return String.valueOf(onlinePlayer.isClimbing());
                    }
                    case "isdeeplysleeping" -> {
                        return String.valueOf(onlinePlayer.isDeeplySleeping());
                    }
                    case "isfrozen" -> {
                        return String.valueOf(onlinePlayer.isFrozen());
                    }
                    case "isgliding" -> {
                        return String.valueOf(onlinePlayer.isGliding());
                    }
                    case "isglowing" -> {
                        return String.valueOf(onlinePlayer.isGlowing());
                    }
                    case "isinpowderedsnow" -> {
                        return String.valueOf(onlinePlayer.isInPowderedSnow());
                    }
                    case "isinvehicle" -> {
                        return String.valueOf(onlinePlayer.isInsideVehicle());
                    }
                    case "isinvisible" -> {
                        return String.valueOf(onlinePlayer.isInvisible());
                    }
                    case "isinvulnerable" -> {
                        return String.valueOf(onlinePlayer.isInvulnerable());
                    }
                    case "isjumping" -> {
                        return String.valueOf(onlinePlayer.isJumping());
                    }
                    case "haspermission" -> {
                        return String.valueOf(onlinePlayer.hasPermission(getStringAfter(paramArray, 1)));
                    }
                    case "hasaquapermission" -> {
                        return String.valueOf(aquaCoreAPI.getGlobalPlayer(player.getUniqueId()).hasPermission(getStringAfter(paramArray, 1)));
                    }
                    case "isdead" -> {
                        return String.valueOf(onlinePlayer.isDead());
                    }
                    case "isflying" -> {
                        return String.valueOf(onlinePlayer.isFlying());
                    }
                }
            }
            case "world" -> {
                Player onlinePlayer = Bukkit.getPlayer(player.getUniqueId());
                if (onlinePlayer == null) {
                    return null;
                }
                World world = onlinePlayer.getWorld();
                switch (getParameter(paramArray, 1)) {
                    case "day" -> {
                        // todo ??? fixme
                        return new BigDecimal(TimeUnit.MILLISECONDS.convert(world.getFullTime() * 50L, TimeUnit.SECONDS) % 86400).setScale(0, RoundingMode.HALF_UP).toPlainString();
                    }
                    case "weather" -> {
                        return StringUtils.capitaliseAllWords(getWeather(onlinePlayer).toString());
                    }
                    case "clearduration" -> {
                        return formatTime(TimeUnit.MILLISECONDS.convert(world.getClearWeatherDuration() * 50L, TimeUnit.SECONDS));
                    }
                    case "thunderduration" -> {
                        return formatTime(TimeUnit.MILLISECONDS.convert(world.getThunderDuration() * 50L, TimeUnit.SECONDS));
                    }
                    case "weatherduration" -> {
                        return formatTime(TimeUnit.MILLISECONDS.convert(world.getWeatherDuration() * 50L, TimeUnit.SECONDS));
                    }
                    case "weathericon" -> {
                        return getWeather(onlinePlayer).getPretty();
                    }
                    case "temperature" -> {
                        // todo rounding
                        return String.valueOf(convertTemperature(world.getBlockAt(onlinePlayer.getLocation()).getTemperature()));
                    }
                    case "humidity" -> {
                        // todo rounding
                        return String.valueOf(convertHumidity(world.getBlockAt(onlinePlayer.getLocation()).getHumidity()));
                    }
                    case "biome" -> {
                        // todo capitalisation
                        char color = switch (world.getBiome(onlinePlayer.getLocation())) {
                            case OCEAN, RIVER -> '9';
                            case PLAINS, BIRCH_FOREST, OLD_GROWTH_BIRCH_FOREST, BAMBOO_JUNGLE, GROVE -> 'a';
                            case DESERT, BEACH, SNOWY_BEACH, SUNFLOWER_PLAINS, DRIPSTONE_CAVES -> 'e';
                            case WINDSWEPT_HILLS, STONY_SHORE, WINDSWEPT_FOREST, WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_SAVANNA, SOUL_SAND_VALLEY, BASALT_DELTAS, STONY_PEAKS -> '7';
                            case FOREST, OLD_GROWTH_SPRUCE_TAIGA, LUSH_CAVES, TAIGA, SWAMP, MANGROVE_SWAMP, JUNGLE, SPARSE_JUNGLE, DARK_FOREST, OLD_GROWTH_PINE_TAIGA -> '2';
                            case NETHER_WASTES, CRIMSON_FOREST -> 'c';
                            case THE_END -> '5';
                            case FROZEN_OCEAN, FROZEN_RIVER -> 'b';
                            case SNOWY_PLAINS, SNOWY_TAIGA -> 'f';
                            case MUSHROOM_FIELDS -> 'd';
                            case DEEP_OCEAN -> '1';
                            case SAVANNA, SAVANNA_PLATEAU -> '6';
                            case BADLANDS -> '6';
                            case WOODED_BADLANDS -> '6';
                            case SMALL_END_ISLANDS -> '5';
                            case END_MIDLANDS -> '5';
                            case END_HIGHLANDS -> '5';
                            case END_BARRENS -> '5';
                            case WARM_OCEAN -> '3';
                            case LUKEWARM_OCEAN -> '3';
                            case COLD_OCEAN -> 'b';
                            case DEEP_LUKEWARM_OCEAN -> '3';
                            case DEEP_COLD_OCEAN -> 'b';
                            case DEEP_FROZEN_OCEAN -> 'b';
                            case THE_VOID -> '0';
                            case FLOWER_FOREST -> 'd';
                            case ICE_SPIKES -> 'b';
                            case ERODED_BADLANDS -> '6';
                            case WARPED_FOREST -> '3';
                            case DEEP_DARK -> '1';
                            case MEADOW -> 'd';
                            case SNOWY_SLOPES -> 'f';
                            case FROZEN_PEAKS -> 'b';
                            case JAGGED_PEAKS -> 'b';
                            case CHERRY_GROVE -> 'd';
                            case CUSTOM -> 'k';
                        };
                        return Component.text("§" + color + world.getBiome(onlinePlayer.getLocation())).content();
                    }
                    case "dimension" -> {
                        switch (world.getEnvironment()) {
                            case NETHER -> {
                                return "§cNether";
                            }
                            case NORMAL -> {
                                return "§bOverworld";
                            }
                            case THE_END -> {
                                return "§5End";
                            }
                        }
                    }

                }
            }
        }
        return null;
    }


    public static String niceBoolean(Boolean b, boolean icon) {
        if (b == null) return "§7Unknown";
        // todo colors
        return b ? icon ? "&a✔" : "&aYes" : icon ? "&c✖" : "&cNo";
    }

    public static String formatIntoMMSS(int secs) {
        int remainder = secs % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;

        return ((minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
    }

    public static String formatTime(long secs) {
        long remainder = secs % 86400;

        long days     = secs / 86400;
        long hours     = remainder / 3600;
        long minutes    = (remainder / 60) - (hours * 60);
        long seconds    = (remainder % 3600) - (minutes * 60);

        if (days > 0) {
            return days+ "d" + hours + "h" + minutes + "m" + seconds + "s";
        } else if (hours > 0) {
            return hours + "h" + minutes + "m" + seconds + "s";
        } else if (minutes > 0) {
            return minutes + "m" + seconds + "s";
        } else {
            return seconds + "s";
        }
    }

    public double convertTemperature(double minecraftTemperature) {
        return (15.66 * minecraftTemperature);
    }

    public double convertHumidity(double minecraftHumidity) {
        return 17.05 * minecraftHumidity + 60;
    }

    public double getBiomeTemperature(Block block) {
         return block.getTemperature();
    }

    @Override
    public String onPlaceholderRequest(Player player, Player player1, String s) {
        List<String> paramArray = Arrays.asList(s.split("_"));
        switch (getParameter(paramArray, 0)) {
            case "cansee" -> {
                return String.valueOf(player.canSee(player1));
            }
        }
        return null;
    }

    public enum Weather {
        // todo colors
        RAIN("&b☔"),
        SNOW("&f☃"),
        THUNDER("&e⚡"),
        CLEAR("&e☀");

        private final String pretty;

        Weather(String pretty) {
            this.pretty = pretty;
        }

        public String getPretty() {
            return pretty;
        }
    }

    public Weather getWeather(Player player) {
        World world = player.getWorld();
        Block block = world.getBlockAt(player.getLocation());
        double temp = getBiomeTemperature(block);
        if (world.isThundering() || world.hasStorm()) return Weather.THUNDER;
        if (world.isClearWeather()) return Weather.CLEAR;
        if (temp > 0.95d) {
            return Weather.CLEAR;
        }
        if (temp < 0.15d) {
            return Weather.SNOW;
        }
        return Weather.RAIN;
    }

    public String getParameter(List<String> paramArray, int depth) {
        return paramArray.get(depth);
    }

    public String getStringAfter(List<String> paramArray, int depth) {
        if (depth > 0) {
            paramArray.subList(0, depth).clear();
        }
        return String.valueOf(paramArray);
    }

    private final static int CENTER_PX = 154;

    public static String getFixedCenteredMessage(String message) {
        return getCenteredMessage(message.trim());
    }

    public static String getCenteredMessage(String message){
        String[] lines = LegacyComponentSerializer.legacyAmpersand().serialize(Component.text(message)).split("\n", 40);
        StringBuilder returnMessage = new StringBuilder();


        for (String line : lines) {
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : line.toCharArray()) {
                if (c == '\u00a7') {
                    previousCode = true;
                } else if (previousCode) {
                    previousCode = false;
                    isBold = c == 'l';
                } else {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize = isBold ? messagePxSize + dFI.getBoldLength() : messagePxSize + dFI.getLength();
                    messagePxSize++;
                }
            }
            int toCompensate = CENTER_PX - messagePxSize / 2;
            int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;
            StringBuilder sb = new StringBuilder();
            while(compensated < toCompensate){
                sb.append(" ");
                compensated += spaceLength;
            }
            returnMessage.append(sb).append(line).append("\n");
        }

        return returnMessage.toString();
    }
}
