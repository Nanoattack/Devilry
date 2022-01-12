package com.nano.devilry.util;

import net.minecraft.util.StringRepresentable;

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

    public String getSerializedName() {
        return this.name;
    }
}
