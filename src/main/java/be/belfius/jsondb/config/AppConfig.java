package be.belfius.jsondb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "application.properties", ignoreResourceNotFound = true)
public class AppConfig {
}
