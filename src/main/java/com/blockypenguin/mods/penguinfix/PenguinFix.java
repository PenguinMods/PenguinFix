package com.blockypenguin.mods.penguinfix;

import com.blockypenguin.mods.penguinfix.config.Config;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PenguinFix implements ModInitializer {
    public static final String MOD_ID = "penguinfix";
    public static final String MOD_NAME = "PenguinFix";
    public static final Config CONFIG = new Config(
        MOD_ID,
        PenguinFix.class.getClassLoader().getResourceAsStream("penguinfix/defaultconfig.json5")
    );

    @Override
    public void onInitialize() {
        createLogger("Initialiser").info(CONFIG.toMiniJSON());
    }

    public static Identifier createID(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static Logger createLogger(String... path) {
        return LoggerFactory.getLogger(MOD_NAME + ':' + String.join("/", path));
    }
}
