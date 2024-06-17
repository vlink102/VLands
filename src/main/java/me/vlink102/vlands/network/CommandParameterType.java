package me.vlink102.vlands.network;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandParameterType {
    private final String parameterName; // <parameterName>
    private final boolean isTrailingParameter; // <parameterName...>
    private final Class<?> dataType; // String
    private final String hoverMessage; // "Hello"
    private final String parameterPermission;

    public String getParameterPermission() {
        return parameterPermission;
    }

    public static class DataType {
        private final Class<?> clazz;

        public DataType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getClazz() {
            return clazz;
        }
    }

    public CommandParameterType(Class<?> dataType, String hoverMessage, boolean isTrailingParameter, String parameterName, String parameterPermission) {
        this.parameterName = parameterName;
        this.isTrailingParameter = isTrailingParameter;
        this.dataType = dataType;
        this.hoverMessage = hoverMessage;
        this.parameterPermission = parameterPermission;
    }

    public CommandParameterType(Class<?> dataType, String hoverMessage, boolean isTrailingParameter, String parameterName) {
        this.parameterName = parameterName;
        this.isTrailingParameter = isTrailingParameter;
        this.dataType = dataType;
        this.hoverMessage = hoverMessage;
        this.parameterPermission = "";
    }

    public CommandParameterType(Builder builder) {
        this.parameterName = builder.parameterName;
        this.dataType = builder.dataType;
        this.isTrailingParameter = builder.isTrailingParameter;
        this.hoverMessage = builder.hoverMessage;
        this.parameterPermission = builder.parameterPermission;
    }

    public boolean canViewParameterType(Player player) {
        if (Objects.equals(parameterPermission, "")) return true;
        return (player.hasPermission(parameterPermission));
    }

    public String getParameterName() {
        return parameterName;
    }

    public boolean isTrailingParameter() {
        return isTrailingParameter;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public String getHoverMessage() {
        return hoverMessage;
    }

    public static class Builder {
        protected String parameterName; // <parameterName>
        protected boolean isTrailingParameter; // <parameterName...>
        protected Class<?> dataType; // String
        protected String hoverMessage; // "Hello"
        protected String parameterPermission;

        public Builder() {
            this.parameterName = "";
            this.isTrailingParameter = false;
            this.dataType = String.class;
            this.hoverMessage = "";
            this.parameterPermission = "";
        }

        public Builder(String name) {
            this.parameterName = name;
            this.isTrailingParameter = false;
            this.dataType = String.class;
            this.hoverMessage = "";
            this.parameterPermission = "";
        }

        public Builder setParameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public Builder setTrailing(boolean trailing) {
            this.isTrailingParameter = trailing;
            return this;
        }

        public Builder setDataType(Class<?> type) {
            this.dataType = type;
            return this;
        }

        public Builder setHoverMessage(String hoverMessage) {
            this.hoverMessage = hoverMessage;
            return this;
        }

        public Builder setParameterPermission(String permission) {
            this.parameterPermission = permission;
            return this;
        }

        public CommandParameterType build() {
            return new CommandParameterType(this);
        }
    }
}