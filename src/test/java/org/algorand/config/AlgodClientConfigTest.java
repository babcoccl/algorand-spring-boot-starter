package org.algorand.config;

import org.algorand.properties.AlgodClientProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class AlgodClientConfigTest {
    @Test
    void apiBaseCanBeConfigured() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(AlgodClientConfig.class));
        contextRunner.withPropertyValues("algorand.algodclient.apiaddress=http://localhost:8080").run((context -> {
            assertThat(context).hasSingleBean(AlgodClientConfig.class);
            assertThat(context.getBean(AlgodClientProperties.class).getApiAddress()).isEqualTo("http://localhost:8080");
        }));
    }
}
