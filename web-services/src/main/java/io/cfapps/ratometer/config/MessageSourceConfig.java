package io.cfapps.ratometer.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSourceConfig {
    private static MessageSource staticSource;

    MessageSourceConfig(MessageSource messageSource) {
        staticSource = messageSource;
    }

    public static String getMessage(String property, String... placeholder) {
        return staticSource.getMessage(property, placeholder, Locale.getDefault());
    }
}
