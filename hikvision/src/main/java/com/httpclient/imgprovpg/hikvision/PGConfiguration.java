package com.httpclient.imgprovpg.hikvision;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.httpclient.imgprovpg.hikvision")
public class PGConfiguration {

}
