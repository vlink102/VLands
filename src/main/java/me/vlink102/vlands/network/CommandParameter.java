package me.vlink102.vlands.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParameter {
    private final List<CommandParameterType> paramTypes;
    private final Type type;

    public enum Type {
        REQUIRED, // <arg>
        OPTIONAL, // (arg)
        DEFAULT // arg
    }

    public CommandParameter(Type type, CommandParameterType... parameterTypes) {
        this.paramTypes = Arrays.asList(parameterTypes);
        this.type = type;
    }

    public CommandParameter(Builder builder) {
        this.paramTypes = builder.paramTypes;
        this.type = builder.type;

    }

    public Type getType() {
        return type;
    }

    public List<CommandParameterType> getParamTypes() {
        return paramTypes;
    }

    public static class Builder {
        private final List<CommandParameterType> paramTypes;
        private Type type;

        public Builder(Type type) {
            this.type = type;
            this.paramTypes = new ArrayList<>();
        }

        public Builder() {
            this.type = Type.DEFAULT;
            this.paramTypes = new ArrayList<>();

        }

        public Builder addParameterType(CommandParameterType type) {
            this.paramTypes.add(type);
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
