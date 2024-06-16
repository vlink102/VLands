package me.vlink102.vlands.network;

import org.bukkit.entity.Player;

public class CommandParameterType {
    private final String parameterName; // <parameterName>
    private final boolean isTrailingParameter; // <parameterName...>
    private final DataType dataType; // String
    private final String hoverMessage; // "Hello"
    private final String parameterPermission;

    public enum DataType {
        STRING("String"), // "ZenmosM"
        INTEGER("Integer"), // 102
        DOUBLE("Decimal"), // 0.03d
        BOOLEAN("Boolean"), // true
        ENUM("Enumerator"), // BIRCH_LOG
        DURATION("Duration"); // 30m

        private final String prettyParameterName;

        DataType(String prettyParameterName) {
            this.prettyParameterName = prettyParameterName;
        }

        public String getPrettyParameterName() {
            return prettyParameterName;
        }
    }

    public CommandParameterType(DataType dataType, String hoverMessage, boolean isTrailingParameter, String parameterName, String parameterPermission) {
        this.parameterName = parameterName;
        this.isTrailingParameter = isTrailingParameter;
        this.dataType = dataType;
        this.hoverMessage = hoverMessage;
        this.parameterPermission = parameterPermission;
    }

    public CommandParameterType(DataType dataType, String hoverMessage, boolean isTrailingParameter, String parameterName) {
        this.parameterName = parameterName;
        this.isTrailingParameter = isTrailingParameter;
        this.dataType = dataType;
        this.hoverMessage = hoverMessage;
        this.parameterPermission = null;
    }

    public CommandParameterType(Builder builder) {
        this.parameterName = builder.parameterName;
        this.dataType = builder.dataType;
        this.isTrailingParameter = builder.isTrailingParameter;
        this.hoverMessage = builder.hoverMessage;
        this.parameterPermission = builder.parameterPermission;
    }

    public boolean canViewParameter(Player player) {
        if (parameterPermission == null) return true;
        return (player.hasPermission(parameterPermission));
    }

    public String getParameterName() {
        return parameterName;
    }

    public boolean isTrailingParameter() {
        return isTrailingParameter;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getHoverMessage() {
        return hoverMessage;
    }

    public static class Builder {
        private String parameterName; // <parameterName>
        private boolean isTrailingParameter; // <parameterName...>
        private DataType dataType; // String
        private String hoverMessage; // "Hello"
        private String parameterPermission;

        public Builder() {
            this.parameterName = null;
            this.isTrailingParameter = false;
            this.dataType = DataType.STRING;
            this.hoverMessage = null;
            this.parameterPermission = null;
        }

        public Builder(String name) {
            this.parameterName = name;
            this.isTrailingParameter = false;
            this.dataType = DataType.STRING;
            this.hoverMessage = null;
            this.parameterPermission = null;
        }

        public Builder setParameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public Builder setTrailing(boolean trailing) {
            this.isTrailingParameter = trailing;
            return this;
        }

        public Builder setDataType(DataType type) {
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