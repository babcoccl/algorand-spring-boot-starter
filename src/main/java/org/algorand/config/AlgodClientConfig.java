package org.algorand.config;

import com.algorand.algosdk.algod.client.AlgodClient;
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
        String apiAddress = algodClientProperties.getApiAddress();
        System.out.println("API address: " + apiAddress);

        //TODO: Write a helper method to validate URL based on some regex voodoo
        if(!apiAddress.isEmpty()) {
            return (AlgodClient) new AlgodClient().setBasePath(apiAddress);
        } else {
            return new AlgodClient();
        }
    }

}
