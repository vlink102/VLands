package me.vlink102.vlands.network;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandParameter {
    private final List<CommandParameterType> paramTypes;
    private final Type type;
    private final String unitInfo;
    private final String permission;

    public enum Type {
        REQUIRED, // <arg>
        OPTIONAL, // (arg)
        DEFAULT // arg
    }

    public String getPermission() {
        return permission;
    }

    public boolean canViewParameter(Player player) {
        if (Objects.equals(permission, "")) return true;
        return (player.hasPermission(permission));
    }

    public CommandParameter(Type type, String unitInfo, String permission, CommandParameterType... parameterTypes) {
        this.paramTypes = Arrays.asList(parameterTypes);
        this.type = type;
        this.permission = permission;
        this.unitInfo = unitInfo;
    }

    public CommandParameter(Builder builder) {
        this.paramTypes = builder.paramTypes;
        this.type = builder.type;
        this.unitInfo = builder.unitInfo;
        this.permission = builder.permission;
    }

    public String getUnitInfo() {
        return unitInfo;
    }

    public Type getType() {
        return type;
    }

    public List<CommandParameterType> getParamTypes() {
        return paramTypes;
    }

    public static class Builder {
        protected final List<CommandParameterType> paramTypes;
        protected Type type;
        protected String unitInfo;
        protected String permission;

        public Builder() {
            this.type = Type.DEFAULT;
            this.paramTypes = new ArrayList<>();
            this.unitInfo = "";
            this.permission = "";
        }

        public Builder addParameterType(CommandParameterType type) {
            this.paramTypes.add(type);
            return this;
        }

        public Builder setUnitInfo(String unitInfo) {
            this.unitInfo = unitInfo;
            return this;
        }

        public Builder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public CommandParameter build() {
            return new CommandParameter(this);
        }
    }
}
