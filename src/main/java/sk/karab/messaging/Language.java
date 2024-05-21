package sk.karab.messaging;

import sk.karab.configuration.ConfigId;
import sk.karab.configuration.YmlConfig;

public class Language {


    private static Language instance;
    private ConfigId language;


    public Language() {

        if (instance == null) instance = this;
        else return;
        detectLanguage();
    }


    private void detectLanguage() {
        try {
            language = ConfigId.valueOf("LANG_" + YmlConfig.find(ConfigId.CONFIG).getString("language").toUpperCase());
        } catch (IllegalArgumentException exception) {
            language = ConfigId.LANG_EN;
        }
    }


    public ConfigId _getLanguageConfigId() {
        return language;
    }


    public static ConfigId getLanguageConfigId() {
        return instance._getLanguageConfigId();
    }


}
