package org.jboss.infinispan.jazz.test.config;


import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.logging.Logger;

@Configuration
public class RemoteCacheConfig {

    private static Logger LOG = Logger.getLogger(RemoteCacheConfig.class.getName());

    private InfinispanConfigProperties configurationProperties;

    public RemoteCacheConfig(InfinispanConfigProperties infinispanConfigProperties) {
        this.configurationProperties = infinispanConfigProperties;
    }

    @Bean
    public RemoteCacheManager remoteCacheManager() {
        LOG_CONFIG(configurationProperties);
        return new RemoteCacheManager(CREATE_CONFIGURATION_BUILDER(configurationProperties));
    }

    private static org.infinispan.client.hotrod.configuration.Configuration
    CREATE_CONFIGURATION_BUILDER(InfinispanConfigProperties config) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        Optional.ofNullable(config.getHost()).ifPresent(host -> cb.addServer().host(host).port(config.getPort()));
        if (config.getUsername() != null && config.getPassword() != null) {
            cb.security().authentication().enable().serverName(config.getServerName()).username(config.getUsername())
                    .password(config.getPassword()).realm(config.getRealm()).saslMechanism(config.getSaslMechanism());
        }
        return cb.build();
    }

    private static void LOG_CONFIG(InfinispanConfigProperties config) {
        LOG.info(
                String.format("Creating Remote Connection with Details: Servername=%s, Host=%s, Port=%s, Username=%s," +
                                " Password=*****, Realm=%s, SaslMechanism=%s", config.getServerName(), config.getHost(),
                        config.getPort(), config.getUsername(), config.getRealm(), config.getSaslMechanism()));
    }

}
