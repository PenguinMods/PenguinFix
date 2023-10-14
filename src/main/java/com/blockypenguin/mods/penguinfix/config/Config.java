package com.blockypenguin.mods.penguinfix.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.blockypenguin.mods.penguinfix.PenguinFix;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.*;

public final class Config {
    private static final Logger LOGGER = PenguinFix.createLogger("Config Manager");
    private static final Jankson JANKSON = Jankson.builder().build();

    private final JsonObject json = new JsonObject();
    private final JsonObject defaultConfig;
    private final String configName;
    private final File configFile;
    private boolean broken = false;

    public Config(String configName, InputStream defaultConfig) {
        this.configName = configName;
        this.configFile = FabricLoader.getInstance().getConfigDir().resolve(configName + ".json5").toFile();

        try {
            this.defaultConfig = JANKSON.load(defaultConfig);
        }catch(SyntaxError | IOException e) {
            LOGGER.error("Failed to load default config file for config '" + configName + "'", e);
            throw new RuntimeException(e);
        }

        this.syncConfig();
    }

    public void syncConfig() {
        if(!configFile.exists() || !configFile.isFile())
            writeToFile();

        JsonObject tempJson = defaultConfig.clone();

        try {
            tempJson.putAll(JANKSON.load(configFile));
            this.broken = false;
        }catch(IOException | SyntaxError e) {
            handleError(e);
            this.broken = true;
        }

        if(!tempJson.equals(this.json)) {
            this.json.clear();
            this.json.putAll(tempJson);

            if(this.broken)
                writeToFile();
        }
    }

    private void handleError(Exception e) { LOGGER.error("Failed to load config '" + configName + "'", e); }

    private void writeToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(configFile);
            fos.write(toJSON().getBytes());
            fos.flush();
            fos.close();
        }catch(IOException e) {
            handleError(e);
        }
    }

    public boolean get(String key, boolean defaultValue) { return json.getBoolean(key, defaultValue); }
    public byte get(String key, byte defaultValue) { return json.getByte(key, defaultValue); }
    public char get(String key, char defaultValue) { return json.getChar(key, defaultValue); }
    public double get(String key, double defaultValue) { return json.getDouble(key, defaultValue); }
    public float get(String key, float defaultValue) { return json.getFloat(key, defaultValue); }
    public int get(String key, int defaultValue) { return json.getInt(key, defaultValue); }
    public long get(String key, long defaultValue) { return json.getLong(key, defaultValue); }
    public short get(String key, short defaultValue) { return json.getShort(key, defaultValue); }
    public JsonElement get(String key, JsonElement defaultValue) { return json.getOrDefault(key, defaultValue); }

    public String toJSON() { return this.json.toJson(true, true); }
    public String toMiniJSON() { return this.json.toJson(); }

    public boolean isBroken() { return broken; }
}