package org.algorand.config;

import com.algorand.algosdk.v2.client.common.IndexerClient;
import org.algorand.properties.AlgodIndexerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(IndexerClient.class)
@EnableConfigurationProperties(AlgodIndexerProperties.class)
public class AlgodIndexerConfig {
    @Autowired
    private AlgodIndexerProperties algodIndexerProperties;

    @Bean
    @ConditionalOnMissingBean
    //Currently, not supporting API key but leaving it here for future use
    public IndexerClient indexerClient(){
        String host = algodIndexerProperties.getHost();
        String token = algodIndexerProperties.getToken();
        String apiKey = algodIndexerProperties.getApiKey();
        int port = Integer.parseInt(algodIndexerProperties.getPort());
        if(token == null || token.isEmpty()){
            token="";
        }
        System.out.println(host + ":" + port + " " + token);
        IndexerClient indexer = new IndexerClient(host, port, token);

        return indexer;
    }
}
