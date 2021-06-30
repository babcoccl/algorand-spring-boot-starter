package org.algorand.config;

import com.algorand.algosdk.account.Account;
import org.algorand.properties.AccountProperties;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

@Configuration
@ConditionalOnClass(Account.class)
@EnableConfigurationProperties(AccountProperties.class)
public class AccountConfig {

    @Autowired
    private AccountProperties accountProperties;

    /**
     * Creates a new account
     * @return new account
     * @throws NoSuchAlgorithmException
     */
    @Bean
    @ConditionalOnMissingBean
    public Account account() throws GeneralSecurityException, NoSuchAlgorithmException {
        String mnemonic = accountProperties.getMnemonic();
        byte[] seed = null;
        System.out.println("Mnemonic: " + mnemonic);

        //Validate the provided mnemonic string is the correct length, otherwise throw property exception
        if(!validateMnemonic(mnemonic)) {
            throw new InvalidPropertyException(AccountConfig.class, "mnemonic",
                    "Mnemonic is an invalid length. Mnemonic must be 25 words in length");
        }

        //Validate the provided seed is the correct length, otherwise throw property exception
        if(!validateSeed(accountProperties.getSeed())) {
            throw new InvalidPropertyException(AccountConfig.class, "seed",
                    "Seed is an invalid length. Seed must be 58 bytes in length. Seed was " +
                            accountProperties.getSeed().getBytes(StandardCharsets.UTF_8).length + " bytes long");
        }

        //Convert the provided seed string to bytes
        if(stringHasValue(accountProperties.getSeed())) {
            seed = accountProperties.getSeed().getBytes(StandardCharsets.UTF_8);
            System.out.println("Seed: " + seed.toString());
        }

        //Create the account based on values provided, throw invalid property exception if both seed and mnemonic exist.
        if(mnemonic == null && seed == null) {
            return new Account();
        } else if(mnemonic != null && seed == null) {
            return new Account(mnemonic);
        } else if(mnemonic == null && seed != null) {
            return new Account(seed);
        } else {
            throw new InvalidPropertyException(AccountConfig.class, "mnemonic & seed",
                    "Cannot specify both mnemonic and seed at account creation. Please configure one or the other.");
        }
    }

    /**
     * A helper method to validate the mnemonic is 25 words long or null
     * @param mnemonic to be validated
     * @return true if it's a valid mnemonic false if it's not
     */
    private boolean validateMnemonic(String mnemonic) {
        String m = mnemonic;

        if(m == null || m.isEmpty()) {
            return true;
        }
        return m.split("\\s+").length == 25 ? true : false;

    }

    /**
     * A helper method to validate the seed is 58 bytes long or null
     * @param seed to be validated
     * @return true if it's a valid seed false if it's not
     */
    private boolean validateSeed(String seed) {
        String s = seed;

        if(s == null || s.isEmpty()) {
            return true;
        }
        return s.getBytes(StandardCharsets.UTF_8).length == 58 ? true : false;
    }

    /**
     * A helper method to check if a string has value (not null or empty)
     * @param str to be validated
     * @return true if it has value false if it's null or empty
     */
    private boolean stringHasValue(String str) {
        return (str != null && !str.isEmpty()) ? true : false;
    }

}
