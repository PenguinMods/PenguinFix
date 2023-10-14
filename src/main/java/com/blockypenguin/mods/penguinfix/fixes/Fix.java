package com.blockypenguin.mods.penguinfix.fixes;

import net.fabricmc.api.EnvType;
import net.minecraft.text.Text;

public class Fix {
    private boolean isEnabled;
    private EnvType side;
    private String nameTranslationKey;

    public Fix(boolean isEnabled, String nameTranslationKey, EnvType side) {
        this.isEnabled = isEnabled;
        this.side = side;
        this.nameTranslationKey = nameTranslationKey;
    }

    public boolean isEnabled() { return isEnabled; }
    public Text getTranslatedName() { return Text.translatable(nameTranslationKey); }
    public EnvType getTargetEnvironment() { return side; }
}
