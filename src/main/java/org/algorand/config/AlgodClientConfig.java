package org.algorand.config;

import com.algorand.algosdk.v2.client.common.AlgodClient;
import org.algorand.properties.AlgodClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(AlgodClient.class)
@EnableConfigurationProperties(AlgodClientProperties.class)
public class AlgodClientConfig {

    @Autowired
    private AlgodClientProperties algodClientProperties;

    public AlgodClient algodClient(){
        String host = algodClientProperties.getHost();
        String token = algodClientProperties.getToken();
        int port = Integer.parseInt(algodClientProperties.getPort());

        return new AlgodClient(host, port, token);
    }

}
