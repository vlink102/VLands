package me.vlink102.vlands.network;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private final List<CommandObject> commands;
    private final TextComponent title;

    public Command(Builder builder) {
        this.commands = builder.commandObjects;
        this.title = Component.text(builder.title);
    }

    public void addCommand(CommandObject object) {
        this.commands.add(object);
    }

    public TextComponent getTitle() {
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
        }

        public Builder addCommandObject(CommandObject object) {
            this.commandObjects.add(object);
            return this;
        }

        public Builder addCommandObjects(CommandObject... objects) {
            this.commandObjects.addAll(List.of(objects));
            return this;
        }

        public Command build() {
            return new Command(this);
        }
    }
}
