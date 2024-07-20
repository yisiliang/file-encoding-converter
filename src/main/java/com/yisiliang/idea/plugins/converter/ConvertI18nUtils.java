package com.yisiliang.idea.plugins.converter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConvertI18nUtils {
    private static Properties I18N_PROP = null;
    private volatile static boolean I18N_LOAD = false;

    private static void initI18NProp() {
        if (!I18N_LOAD) {
            synchronized (ConvertI18nUtils.class) {
                if (!I18N_LOAD) {
                    String userLanguage = System.getProperty("user.language");
                    if (userLanguage == null || userLanguage.isEmpty()) {
                        userLanguage = "en";
                    }
                    InputStream languageInputStream = ConvertI18nUtils.class.getClassLoader().getResourceAsStream("i18n/" + userLanguage + ".properties");
                    if (languageInputStream == null) {
                        languageInputStream = ConvertI18nUtils.class.getClassLoader().getResourceAsStream("i18n/en.properties");
                    }
                    try {
                        I18N_PROP = new Properties();
                        I18N_PROP.load(languageInputStream);
                        I18N_LOAD = true;
                    } catch (IOException e) {
                        //ignore
                    } finally {
                        closeSafe(languageInputStream);
                    }
                }
            }
        }
    }

    public static String getI18NValue(String key) {
        initI18NProp();
        if (I18N_PROP != null) {
            String property = I18N_PROP.getProperty(key);
            if (property != null) {
                return property;
            }
        }
        return key;
    }

    private static void closeSafe(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }
}
