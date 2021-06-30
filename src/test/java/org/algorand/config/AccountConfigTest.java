package org.algorand.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.algorand.properties.AccountProperties;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountConfigTest {



    @Test
    void accountMnemonicCanBeConfigured() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(AccountConfig.class));
        contextRunner.withPropertyValues(
                "algorand.account.mnemonic=portion never forward pill lunch organ biology weird catch curve isolate" +
                        " plug innocent skin grunt bounce clown mercy hole eagle soul chunk type absorb trim").run((context -> {
            assertThat(context).hasSingleBean(AccountConfig.class);
            assertThat(context.getBean(AccountProperties.class).getMnemonic()).isEqualTo(
                    "portion never forward pill lunch organ biology weird catch curve isolate" +
                            " plug innocent skin grunt bounce clown mercy hole eagle soul chunk type absorb trim");
        }));
    }

    @Test
    void accountSeedCanBeConfigured() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(AccountConfig.class));
        contextRunner.withPropertyValues(
                "algorand.account.seed=AJNNFQN7DSR7QEY766V7JDG35OPM53ZSNF7CU264AWOOUGSZBMLMSKCRIU").run((context -> {
            assertThat(context).hasSingleBean(AccountConfig.class);
            assertThat(context.getBean(AccountProperties.class).getSeed().getBytes(StandardCharsets.UTF_8)).isEqualTo(
                    "AJNNFQN7DSR7QEY766V7JDG35OPM53ZSNF7CU264AWOOUGSZBMLMSKCRIU".getBytes(StandardCharsets.UTF_8));
        }));
    }

}
