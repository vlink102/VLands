package me.vlink102.vlands.network;

import org.bukkit.entity.Player;

import java.util.*;

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

    public String getPrefix() {
        return prefix;
    }

    public String getDescription() {
        return description;
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

    public boolean canViewParameterCluster(Player player) {
        if (Objects.equals(permission, "")) return true;
        return (player.hasPermission(permission));
    }

    public static class Builder {
        private final List<CommandParameter> parameters;
        private String permission;
        private String prefix;
        private String description;

        public Builder(String prefix) {
            this.prefix = prefix;
            this.parameters = new ArrayList<>();
            this.permission = "";
            this.description = "";
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

        public Builder addParameter(CommandParameter.Builder parameter) {
            this.parameters.add(parameter.build());
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