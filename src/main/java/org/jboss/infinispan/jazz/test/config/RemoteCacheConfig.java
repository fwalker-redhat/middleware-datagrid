package org.jboss.infinispan.jazz.test.config;


import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class RemoteCacheConfig {

    private static Logger LOG = Logger.getLogger(RemoteCacheConfig.class.getName());

    private InfinispanConfigProperties infinispanConfigProperties;

    public RemoteCacheConfig(InfinispanConfigProperties infinispanConfigProperties) {
        this.infinispanConfigProperties = infinispanConfigProperties;
    }

    @Bean
    public RemoteCacheManager remoteCacheManager() {
        LOG.info(
                String.format("Creating Remote Connection with Details: Servername=%s, Host=%s, Port=%s, Username=%s, Password=*****, Realm=%s, SaslMechanism=%s",
                        infinispanConfigProperties.getServerName(), infinispanConfigProperties.getHost(),
                        infinispanConfigProperties.getPort(), infinispanConfigProperties.getUsername(),
                        infinispanConfigProperties.getRealm(), infinispanConfigProperties.getSaslMechanism()));
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.addServer().host(infinispanConfigProperties.getHost()).port(infinispanConfigProperties.getPort())
                .security()
                .authentication().enable()
                .serverName(infinispanConfigProperties.getServerName())
                .username(infinispanConfigProperties.getUsername())
                .password(infinispanConfigProperties.getPassword())
                .realm(infinispanConfigProperties.getRealm())
                .saslMechanism(infinispanConfigProperties.getSaslMechanism());
        return new RemoteCacheManager(cb.build());
    }

}
