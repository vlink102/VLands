package me.vlink102.vlands.network;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandObject {
    private final List<CommandParameter> parameters;
    private final String permission;
    private final String prefix;
    private final String description;

    public CommandObject(CommandObject.Builder builder) {
        this.parameters = builder.parameters;
        this.permission = builder.permission;
        this.prefix = builder.prefix;
        this.description = builder.description;
    }

    public void addParameter(CommandParameter parameter) {
        this.parameters.add(parameter);
    }

    public void removeParameter(int index) {
        this.parameters.remove(index);
    }

    public void clearParameters() {
        this.parameters.clear();
    }

    public List<CommandParameter> getParameters() {
        return parameters;
    }

    public String getPermission() {
        return permission;
    }

    public boolean canViewParameter(Player player) {
        if (permission == null) return true;
        return (player.hasPermission(permission));
    }

    public static class Builder {
        private final List<CommandParameter> parameters;
        private String permission;
        private String prefix;
        private String description;

        public Builder() {
            this.parameters = new ArrayList<>();
            this.permission = null;
            this.description = null;
        }

        public Builder(String prefix) {
            this.prefix = prefix;
            this.parameters = new ArrayList<>();
            this.permission = null;
            this.description = null;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder addParameter(CommandParameter parameter) {
            this.parameters.add(parameter);
            return this;
        }

        public Builder removeParameter(int index) {
            this.parameters.remove(index);
            return this;
        }

        public Builder clearParameters() {
            this.parameters.clear();
            return this;
        }

        public CommandObject build() {
            return new CommandObject(this);
        }
    }
}