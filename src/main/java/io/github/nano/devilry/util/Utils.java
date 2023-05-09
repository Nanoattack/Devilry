package io.github.nano.devilry.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public class Utils {
    @NotNull
    public static <T, V> T useNonNullOrElse(Function<V, T> function, V value, T orElse) {
        return value == null ? orElse : function.apply(value);
    }
}
