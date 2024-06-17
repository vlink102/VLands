package me.vlink102.vlands.network.commands;

import me.vlink102.vlands.network.Command;
import me.vlink102.vlands.network.CommandObject;
import me.vlink102.vlands.network.CommandParameter;
import me.vlink102.vlands.network.CommandParameterType;

public class Tab extends Command {
    private static final CommandObject reload = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("reload").build()
                            ).build()
            )
            .setDescription("Reloads plugin and config")
            .build();
    private static final CommandObject propertyChange = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("group").build()
                            ).addParameterType(
                                    new CommandParameterType.Builder("player").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("name").build()
                            ).build()
            ).addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("property").build()
                            ).build()
            ).addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("values")
                                            .setTrailing(true)
                                            .build()
                            ).build()
            )
            .setDescription("Modifies the specified property of the target")
            .build();

    private static final CommandObject listProperties = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("group").build()
                            ).addParameterType(
                                    new CommandParameterType.Builder("player").build()
                            ).build()
            ).setDescription("Lists the available properties")
            .build();

    /*
    private static final CommandObject nametagPreview = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("nametag").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("preview").build()
                            ).build()
            ).setDescription("Shows your nametag for yourself")
            .build();

     */

    private static final CommandObject announceBar = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("announce").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("bar").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("name").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("seconds")
                                            .setDataType(Integer.class)
                                            .build()
                            ).build()
            )
            .setDescription("Temporarily display bossbar for all players")
            .build();
    private static final CommandObject parse = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("parse").build()
                            ).build()
            ).addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("target").build()
                            ).build()
            ).addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("string").build()
                            ).build()
            )
            .setDescription("Parse a string")
            .build();

    private static final CommandObject debug = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("debug").build()
                            ).build()
            ).addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.OPTIONAL)
                            .addParameterType(
                                    new CommandParameterType.Builder("player").build()
                            ).build()
            )
            .setDescription("Display debug information about a player")
            .build();

    private static final CommandObject cpu = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("cpu").build()
                            ).build()
            )
            .setDescription("Displays CPU usage of the plugin")
            .build();

    private static final CommandObject clearData = new CommandObject.Builder("btab")
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("group").build()
                            ).addParameterType(
                                    new CommandParameterType.Builder("player").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .setType(CommandParameter.Type.REQUIRED)
                            .addParameterType(
                                    new CommandParameterType.Builder("name").build()
                            ).build()
            )
            .addParameter(
                    new CommandParameter.Builder()
                            .addParameterType(
                                    new CommandParameterType.Builder("remove").build()
                            ).build()
            )
            .setDescription("Clears all data of the target")
            .build();


    public Tab() {
        super(new Tab.Builder("Tablist").addCommandObjects(reload, propertyChange, cpu, clearData, /*nametagPreview, */debug, parse, announceBar, listProperties));
    }
}
