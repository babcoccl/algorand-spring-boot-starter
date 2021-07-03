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
        contextRunner.withPropertyValues("algorand.algodclient.host=http://localhost",
                "algorand.algodclient.port=4001",
                "algorand.algodclient.token=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").run(
                (context -> {
                    assertThat(context).hasSingleBean(AlgodClientConfig.class);
                    assertThat(context.getBean(AlgodClientProperties.class).getHost()).isEqualTo("http://localhost");
                    assertThat(context.getBean(AlgodClientProperties.class).getPort()).isEqualTo("4001");
                    assertThat(context.getBean(AlgodClientProperties.class).getToken()).isEqualTo(
                            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }));
    }
}
