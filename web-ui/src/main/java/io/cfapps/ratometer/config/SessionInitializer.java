package io.cfapps.ratometer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(SessionInitializer.class);

    public SessionInitializer() {
        log.info("Instantiating Http Session application initializer...");
    }

}
