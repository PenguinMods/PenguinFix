package com.blockypenguin.mods.penguinfix.client;

import com.blockypenguin.mods.penguinfix.PenguinFix;
import com.blockypenguin.mods.penguinfix.fixes.Fix;
import com.blockypenguin.mods.penguinfix.fixes.FixRegistryImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public final class PenguinFixClient implements ClientModInitializer {
    private static final Logger LOGGER = PenguinFix.createLogger("Client", "Initialiser");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Registering Fixes...");
        FixRegistryImpl.register(PenguinFix.createID("mc122477"), new Fix(true, "fix.penguinfix.mc122477", EnvType.CLIENT));
        FixRegistryImpl.freeze();
        LOGGER.info("Done. Fix Registry Frozen.");
    }
}
