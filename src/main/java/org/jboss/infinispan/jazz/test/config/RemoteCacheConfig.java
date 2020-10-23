package org.jboss.infinispan.jazz.test.config;


import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteCacheConfig {

    private InfinispanConfigProperties infinispanConfigProperties;

    public RemoteCacheConfig(InfinispanConfigProperties infinispanConfigProperties) {
        this.infinispanConfigProperties = infinispanConfigProperties;
    }

    @Bean
    public RemoteCacheManager remoteCacheManager() {
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
