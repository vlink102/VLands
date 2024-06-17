package me.vlink102.vlands.network;

import me.vlink102.vlands.network.commands.Tab;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.common.value.qual.StringVal;

import javax.inject.Named;
import java.util.*;

public class Command {
    private final List<CommandObject> commands;
    private final String title;

    public Command(Builder builder) {
        this.commands = builder.commandObjects;
        this.title = builder.title;
    }

    public List<CommandObject> getCommands() {
        return commands;
    }

    public void addCommand(CommandObject object) {
        this.commands.add(object);
    }

    public String getTitle() {
        return title;
    }



    public static class Builder {
        private List<CommandObject> commandObjects;
        private String title;

        public Builder() {
            this.commandObjects = new ArrayList<>();
        }

        public Builder(String title) {
            this.title = title;
            this.commandObjects = new ArrayList<>();
        }

        public Builder addCommandObject(CommandObject object) {
            this.commandObjects.add(object);
            return this;
        }

        public Builder addCommandObjects(CommandObject... objects) {
            if (objects == null) {
                this.commandObjects = new ArrayList<>();
                return this;
            }
            this.commandObjects.addAll(List.of(objects));
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }

    public static final Menu.ColorScheme MAIN = new Menu.ColorScheme(
            NamedTextColor.DARK_AQUA,
            NamedTextColor.DARK_AQUA,
            NamedTextColor.DARK_AQUA,
            NamedTextColor.DARK_AQUA
    );

    public static class Menu {
        public List<TextComponent> generateDirectoryListing(Menu rootDirectory, Menu currentDirectory) {
            List<TextComponent> components = new ArrayList<>();
            components.add(Component.text(""));
            components.add(Component.text(" \uD83D\uDCE6 ")
                    .color(NamedTextColor.YELLOW)
                    .append(Component.text(rootDirectory.link.getTitle())
                            .color(NamedTextColor.GRAY))
                    .decoration(TextDecoration.ITALIC, false));
            for (int i = 0; i < rootDirectory.subTopics.size(); i++) {
                Menu subTopic = rootDirectory.subTopics.get(i);
                boolean isSame = currentDirectory.ID == subTopic.ID;
                if (i + 1 == rootDirectory.subTopics.size()) {
                    components.add(Component.text(" └─ ")
                            .color(NamedTextColor.GRAY)
                            .append(Component.text("\uD83D\uDCC2 ")
                                    .color(NamedTextColor.YELLOW))
                            .append(Component.text(subTopic.link.getTitle())
                                    .color(isSame ? NamedTextColor.AQUA : NamedTextColor.GRAY))
                            .decoration(TextDecoration.ITALIC, false));
                } else {
                    components.add(Component.text(" ├─ ")
                            .color(isSame ? NamedTextColor.DARK_AQUA : NamedTextColor.GRAY)
                            .append(Component.text("\uD83D\uDCC2 ")
                                    .color(NamedTextColor.YELLOW))
                            .append(Component.text(subTopic.link.getTitle())
                                    .color(isSame ? NamedTextColor.AQUA : NamedTextColor.GRAY))
                            .decoration(TextDecoration.ITALIC, false));
                }
            }
            components.add(Component.text(""));
            components.add(Component.text("Click to browse category").color(NamedTextColor.YELLOW)
                    .decoration(TextDecoration.ITALIC, false));
            return components;
        }

        public static class ColorScheme {
            private final NamedTextColor user;
            private final NamedTextColor admin;
            private final NamedTextColor required;
            private final NamedTextColor optional;

            public ColorScheme(NamedTextColor... textColors) {
                if (textColors.length != 4) throw new IllegalArgumentException("Have to be 4 colors in a colorscheme.");
                this.user = textColors[0];
                this.admin = textColors[1];
                this.required = textColors[2];
                this.optional = textColors[3];
            }

            public NamedTextColor getAdmin() {
                return admin;
            }

            public NamedTextColor getOptional() {
                return optional;
            }

            public NamedTextColor getRequired() {
                return required;
            }

            public NamedTextColor getUser() {
                return user;
            }
        }

        private final Command link;
        private final int ID;
        private final List<Menu> subTopics;
        private final ColorScheme scheme;

        public static Menu getRoot(Integer... indexes) {
            Menu current = VLands.getMenu();
            for (int i = 0; i < indexes.length; i++) {
                if (i + 1 == indexes.length) {
                    return current.subTopics.get(indexes[i]);
                }
                current = current.subTopics.get(indexes[i]);
            }
            return VLands.getMenu();
        }

        public static List<Menu> getDirectory(Integer... indexes) {
            List<Menu> menus = new ArrayList<>();
            Menu currentMenu = VLands.getMenu();
            menus.add(currentMenu);
            for (Integer index : indexes) {
                currentMenu = currentMenu.subTopics.get(index);
                menus.add(currentMenu);
            }
            return menus;
        }

        public int getID() {
            return ID;
        }

        public static Command getFromMenu(Integer... indexes) {
            return getRoot(indexes).getLink();
        }

        public Menu(Command link, int ID, ColorScheme scheme, Menu... subTopics) {
            this.link = link;
            this.ID = ID;
            this.subTopics = List.of(subTopics);
            this.scheme = scheme;
        }

        public Menu(String link, int ID, ColorScheme scheme, Menu... subTopics) {
            this.link = new Command.Builder(link).build();
            this.scheme = scheme;
            this.ID = ID;
            this.subTopics = List.of(subTopics);
        }

        public ColorScheme getScheme() {
            return scheme;
        }

        public Command getLink() {
            return link;
        }

        public List<Menu> getSubTopics() {
            return subTopics;
        }
    }



    public static class MainMenu extends Menu {
        public MainMenu() {
            super("VLands Network", 0, MAIN,
                    new Menu(new Tab(), 1, MAIN,                    // 0
                            new Menu("MySQL", 10, MAIN),        // 0, 0
                            new Menu("Scoreboard", 11, MAIN),   // 0, 1
                            new Menu("Bossbar", 12, MAIN),      // 0, 2
                            new Menu("Nametag", 13, MAIN)       // 0, 3
                    ),
                    new Menu("Quests", 2, MAIN),               // 1
                    new Menu("Advancements", 3, MAIN),         // 2
                    new Menu("Staff", 4, MAIN)                 // 3
            );
        }
    }

    public static final HashMap<Integer, Integer[]> ID_MAP = new HashMap<>() {{
        this.put(0, new Integer[]{});
        this.put(1, new Integer[]{0});
        this.put(10, new Integer[]{0, 0});
        this.put(11, new Integer[]{0, 1});
        this.put(12, new Integer[]{0, 2});
        this.put(13, new Integer[]{0, 3});
        this.put(2, new Integer[]{1});
        this.put(3, new Integer[]{2});
        this.put(4, new Integer[]{3});
    }};

    public static void sendHelpMenuByID(Player player, int ID) {
        sendHelpMenu(player, ID_MAP.get(ID));
    }

    public static void sendHelpMenu(Player player, Integer... indexes) {
        Command link = Menu.getFromMenu(indexes);
        sendFullHelpMessage(player, link);
        player.sendMessage(menuFooter(indexes));
    }

    public static TextComponent menuFooter(Integer... indexes) {
        List<Menu> dir = Menu.getDirectory(indexes);
        TextComponent.Builder builder = Component.text()
                .append(Component.text(" \uD83D\uDEC8 ").color(NamedTextColor.GOLD));
        for (int i = 0; i < dir.size(); i++) {
            Menu menu = dir.get(i);
            if (i > 0) {
                builder.append(Component.text(" ➜ ").color(NamedTextColor.DARK_GRAY));
            }
            if (i + 1 == dir.size()) {
                builder.append(Component.text(menu.getLink().getTitle()).color(NamedTextColor.GRAY));
            } else {
                ItemStack stack = new ItemStack(Material.WRITTEN_BOOK);
                ItemMeta meta = stack.getItemMeta();
                meta.displayName(Component.text("Directory Listing:")
                        .color(NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false));
                meta.lore(menu.generateDirectoryListing(menu, Menu.getRoot(indexes)));
                for (ItemFlag value : ItemFlag.values()) {
                    meta.addItemFlags(value);
                }
                stack.setItemMeta(meta);


                builder.append(Component.text(menu.getLink().getTitle())
                        .decorate(TextDecoration.ITALIC)
                        .color(NamedTextColor.GRAY)
                        //.hoverEvent(HoverEvent.showText(Component.text("Click to browse category").color(NamedTextColor.YELLOW)))
                        .hoverEvent(stack)
                        .clickEvent(ClickEvent.runCommand("/help id " + menu.getID())));
            }
        }
        return builder.build();
    }

    public static void sendFullHelpMessage(Player player, Command command) {
        TextComponent title = Component.text(command.getTitle().toUpperCase()).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD);
        player.sendMessage(Placeholders.getCenteredMessage(title));
        player.sendMessage("");
        for (CommandObject object : command.getCommands()) {
            player.sendMessage(helpLine(player, object).decorate(TextDecoration.ITALIC));
        }
        player.sendMessage("");
    }

    // full line
    public static TextComponent helpLine(Player player, CommandObject object) {
        if (!object.canViewParameterCluster(player)) return null;
        String permission = object.getPermission();
        NamedTextColor color = (Objects.equals(permission, "")) ? MAIN.user : (object.canViewParameterCluster(player) ? MAIN.admin : MAIN.user);
        TextComponent.Builder helpLine = Component.text()
                .append(Component.text(" \uD83D\uDCCB ")
                        .color(NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false)
                        .hoverEvent(Component.text("Click to try command!")
                                .color(NamedTextColor.YELLOW)
                                .decoration(TextDecoration.ITALIC, false))
                        .clickEvent(ClickEvent.suggestCommand(getRawString(player, object)))
                )
                .append(Component.text("/" + object.getPrefix())
                        .color(color));
        List<CommandParameter> filtered = new ArrayList<>(object.getParameters()).stream().filter(parameter -> parameter.canViewParameter(player)).toList();
        for (CommandParameter parameter : filtered) {
            helpLine.append(Component.text(" "));
            helpLine.append(helpParameter(player, parameter));
        }
        helpLine.append(Component.text(" "));

        ItemStack stack = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component.text("Command Description:")
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GOLD));
        meta.lore(List.of(
                Component.text(""),
                Component.text(object.getDescription())
                        .color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        ));
        for (ItemFlag value : ItemFlag.values()) {
            meta.addItemFlags(value);
        }
        stack.setItemMeta(meta);

        /*
        Component.text()
                            .append(Component.text()
                                    .append(Component.text("Press ").color(NamedTextColor.YELLOW))
                                    .append(Component.keybind().keybind("key.use").color(NamedTextColor.YELLOW))
                                    .append(Component.text(" to try command!").color(NamedTextColor.YELLOW))
                                    .color(NamedTextColor.YELLOW))
                            .decoration(TextDecoration.ITALIC, false)
                            .build()
         */

        helpLine.append(Component.text()
                .append(Component.text("(Hover)")
                        .hoverEvent(stack)
                        //.clickEvent(ClickEvent.runCommand("/vccb fill"))
                        //.clickEvent(ClickEvent.suggestCommand(getRawString(player, object)))
                        .color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false))
                .build());

        return helpLine.build();
    }

    // one parameter cluster
    public static TextComponent helpParameter(Player player, CommandParameter parameter) {
        if (!parameter.canViewParameter(player)) return null;
        TextComponent.Builder fullParameter = Component.text();

        NamedTextColor bracketColor = switch (parameter.getType()) {
            case OPTIONAL -> MAIN.optional;
            case REQUIRED -> MAIN.required;
            case DEFAULT -> NamedTextColor.DARK_RED; // Error
        };

        switch (parameter.getType()) {
            case OPTIONAL -> fullParameter.append(Component.text("(")).color(bracketColor);
            case REQUIRED -> fullParameter.append(Component.text("<")).color(bracketColor);
        }

        List<CommandParameterType> filtered = new ArrayList<>(parameter.getParamTypes()).stream().filter(type -> type.canViewParameterType(player)).toList();

        for (int i = 0; i < filtered.size(); i++) {
            CommandParameterType type = filtered.get(i);
            Class<?> dataType = type.getDataType();
            String hoverMessage = type.getHoverMessage();
            String name = type.getParameterName();
            String permission = type.getParameterPermission();
            NamedTextColor color = (Objects.equals(permission, "")) ? MAIN.user : (type.canViewParameterType(player) ? MAIN.admin : MAIN.user);

            if (i > 0) {
                fullParameter.append(Component.text("/").color(bracketColor));
            }

            if (parameter.getType() == CommandParameter.Type.DEFAULT) {
                TextComponent component = Component.text()
                        .append(Component.text(name).color(color))
                        .build();
                fullParameter.append(component);
            } else {
                ItemStack stack = new ItemStack(Material.NAME_TAG);
                ItemMeta meta = stack.getItemMeta();
                meta.displayName(Component.text("Parameter Information")
                        .color(NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false));
                meta.lore(List.of(
                        Component.text(""),
                        Component.text()
                                .append(Component.text("Data Type: ")
                                        .color(NamedTextColor.GRAY))
                                .append(Component.text(Placeholders.toReadable(dataType.getSimpleName()))
                                        .color(NamedTextColor.AQUA))
                                .decoration(TextDecoration.ITALIC, false)
                                .build(),
                        Component.text()
                                .append(Component.text("Parameter Type: ")
                                        .color(NamedTextColor.GRAY))
                                .append(Component.text(Placeholders.toReadable(parameter.getType().toString()))
                                        .color(NamedTextColor.DARK_AQUA))
                                .decoration(TextDecoration.ITALIC, false)
                                .build(),
                        Component.text()
                                .append(Component.text("Unit(s): ")
                                        .color(NamedTextColor.GRAY))
                                .append(Objects.equals(parameter.getUnitInfo(), "") ?
                                        Component.text("None").color(NamedTextColor.RED) :
                                        Component.text(parameter.getUnitInfo()).color(NamedTextColor.DARK_AQUA)
                                )
                                .decoration(TextDecoration.ITALIC, false)
                                .build(),
                        Component.text(""),
                        Component.text()
                                .append(Component.text("Permission: ")
                                        .color(NamedTextColor.GRAY))
                                .append(Objects.equals(permission, "") ?
                                        Component.text("None").color(NamedTextColor.YELLOW) :
                                        Component.text(permission).color(NamedTextColor.DARK_AQUA))
                                .decoration(TextDecoration.ITALIC, false)
                                .build(),
                        Component.text(""),
                        Component.text()
                                .append(Component.text("Description: ")
                                        .color(NamedTextColor.GRAY))
                                .append(Objects.equals(hoverMessage, "") ?
                                        Component.text("None provided").color(NamedTextColor.RED) :
                                        Component.text(hoverMessage).color(NamedTextColor.GRAY))
                                .decoration(TextDecoration.ITALIC, false)
                                .build()
                ));
                for (ItemFlag value : ItemFlag.values()) {
                    meta.addItemFlags(value);
                }
                stack.setItemMeta(meta);

                TextComponent component = Component.text()
                        .append(Component.text(name).color(color))
                        .hoverEvent(stack).build();
                fullParameter.append(component);
            }


            if (type.isTrailingParameter()) {
                fullParameter.append(Component.text("...")
                        .color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                        .hoverEvent(trailing()));
            }
        }

        switch (parameter.getType()) {
            case OPTIONAL -> fullParameter.append(Component.text(")")).color(bracketColor);
            case REQUIRED -> fullParameter.append(Component.text(">")).color(bracketColor);
        }
        return fullParameter.build();
    }

    public static String getRawString(Player player, CommandObject object) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("/" + object.getPrefix());

        List<CommandParameter> filtered = new ArrayList<>(object.getParameters()).stream().filter(parameter -> parameter.canViewParameter(player)).toList();


        for (CommandParameter parameter : filtered) {
            if (!parameter.canViewParameter(player)) continue;
            StringBuilder sb = new StringBuilder();
            switch (parameter.getType()) {
                case REQUIRED -> sb.append("<");
                case OPTIONAL -> sb.append("(");
            }
            List<CommandParameterType> typeFiltered = new ArrayList<>(parameter.getParamTypes()).stream().filter(type -> type.canViewParameterType(player)).toList();

            for (int i = 0; i < typeFiltered.size(); i++) {
                CommandParameterType paramType = typeFiltered.get(i);
                if (!paramType.canViewParameterType(player)) continue;
                if (i > 0) {
                    sb.append("/");
                }
                sb.append(paramType.getParameterName());
                if (paramType.isTrailingParameter()) {
                    sb.append("...");
                }
            }
            switch (parameter.getType()) {
                case REQUIRED -> sb.append(">");
                case OPTIONAL -> sb.append(")");
            }
            joiner.add(sb);
        }
        return joiner.toString();
    }

    public static TextComponent trailing() {
        return Component.text("Multiple of the preceding parameter may be provided")
                .color(NamedTextColor.GRAY);
    }
}
