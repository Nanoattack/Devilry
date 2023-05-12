package io.github.nano.devilry.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
//todo

public enum AltarPart implements StringRepresentable {
    MAIN("main"),
    SIDE("side");

    private final String name;

    AltarPart(String string) {
        this.name = string;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
