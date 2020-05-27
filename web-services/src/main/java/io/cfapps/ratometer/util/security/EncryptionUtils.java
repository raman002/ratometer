package io.cfapps.ratometer.util.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

	public static final int BCRYPT_SALT_SIZE = 10;
    private static PasswordEncoder passEncoder;

    private EncryptionUtils(PasswordEncoder passwordEncoder) {
        passEncoder = passwordEncoder;
    }

    public static String getBcryptHash(String input) {
        return passEncoder.encode(input);
    }

    public static boolean matchesBcryptHash(String rawPassword, String encodedPassword) {
        return passEncoder.matches(rawPassword, encodedPassword);
    }
}
