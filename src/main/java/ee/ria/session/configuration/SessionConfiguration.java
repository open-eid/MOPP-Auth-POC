package ee.ria.session.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfiguration {

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Bean
    public Config hazelCastConfig() {
        Config config =  new Config();
        config.setInstanceName("hazelcast-instance");
        config.addMapConfig(new MapConfig().setName(ConfigurationProvider.MAP_SESSION_CACHE)
                .setMaxSizeConfig(new MaxSizeConfig(500, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                .setTimeToLiveSeconds(120));
        if(!configurationProvider.isMultiCastEnabled()) {
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
            config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(configurationProvider.getTcpIp());
        }
        return config;
    }
}
