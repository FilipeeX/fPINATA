package sk.karab.messaging;

import sk.karab.configuration.ConfigId;
import sk.karab.configuration.YmlConfig;

public class Language {


    private static Language instance;
    private ConfigId language;


    public Language() {

        if (instance == null) instance = this;
        else return;
        _detectLanguage();
    }


    private void _detectLanguage() {
        try {
            language = ConfigId.valueOf("LANG_" + YmlConfig.find(ConfigId.CONFIG).getString("language").toUpperCase());
        } catch (IllegalArgumentException exception) {
            language = ConfigId.LANG_EN;
        }
    }

    public static void detectLanguage() {
        instance._detectLanguage();
    }


    public ConfigId _getLanguageConfigId() {
        return language;
    }

    public static ConfigId getLanguageConfigId() {
        return instance._getLanguageConfigId();
    }


}
