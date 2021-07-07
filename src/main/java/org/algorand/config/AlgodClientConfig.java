package org.algorand.config;

import com.algorand.algosdk.v2.client.common.AlgodClient;
import org.algorand.properties.AlgodClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(AlgodClient.class)
@EnableConfigurationProperties(AlgodClientProperties.class)
public class AlgodClientConfig {

    @Autowired
    private AlgodClientProperties algodClientProperties;

    @Bean
    @ConditionalOnMissingBean
    //Currently, not supporting API key but leaving it here for future use
    public AlgodClient algodClient(){
        String host = algodClientProperties.getHost();
        String token = algodClientProperties.getToken();
        String apiKey = algodClientProperties.getApiKey();
        int port = Integer.parseInt(algodClientProperties.getPort());
        if(token == null || token.isEmpty()){
            token="";
        }
        System.out.println(host + ":" + port + " " + token);
        AlgodClient client = new AlgodClient(host, port, token);

        return client;
    }

}
