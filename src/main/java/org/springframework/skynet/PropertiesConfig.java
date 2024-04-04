package org.springframework.skynet;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;


@Configuration
@PropertySource("classpath:keyvalue.properties")
public class PropertiesConfig {

    @Bean
    public PropertiesFactoryBean keyValueProperties(Environment environment, ResourceLoader resourceLoader) {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(resourceLoader.getResource("classpath:keyvalue.properties"));
        return bean;
    }
}
