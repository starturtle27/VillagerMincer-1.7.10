package com.starturtle27.villagermincer;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class XLanguageRegistry {

    public static void addLocalize(Object target, String en, String jp) {
        LanguageRegistry.addName(target, en);
        LanguageRegistry.instance()
            .addNameForObject(target, "ja_JP", jp);
    }

    public static void addStringLocalize(String tag, String en, String jp) {
        LanguageRegistry.instance()
            .addStringLocalization(tag, en);
        LanguageRegistry.instance()
            .addStringLocalization(tag, "ja_JP", jp);
    }

    public static void addEntityLocalize(String entityName, String en, String jp) {
        LanguageRegistry.instance()
            .addStringLocalization("entity." + entityName + ".name", en);
        LanguageRegistry.instance()
            .addStringLocalization("entity." + entityName + ".name", "ja_JP", jp);
    }
}
