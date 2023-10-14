package com.blockypenguin.mods.penguinfix.fixes;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class FixRegistryImpl {
    private static final Map<Identifier, Fix> FIXES = new Reference2ObjectOpenHashMap<>();
    private static boolean FROZEN = false;

    public static Fix register(Identifier identifier, Fix fix) {
        if(FROZEN)
            throw new IllegalStateException("Attempted to register a Fix after registry freeze!");

        for(var existingFix : FIXES.entrySet()) {
            if(existingFix.getValue() == fix) {
                throw new IllegalArgumentException("Attempted to register a Fix twice: " + fix.getTranslatedName().getString());
            }else if(existingFix.getKey().equals(identifier)) {
                throw new IllegalArgumentException("Attempted to register two Fixes with equal ID: " + identifier + "!");
            }
        }

        FIXES.put(identifier, fix);
        return fix;
    }

    public static Optional<Fix> getFix(Identifier identifier) {
        Objects.requireNonNull(identifier);
        return Optional.ofNullable(FIXES.get(identifier));
    }

    public static void freeze() { FROZEN = true; }
}
