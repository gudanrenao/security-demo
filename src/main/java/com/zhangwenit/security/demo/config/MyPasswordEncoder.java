package com.zhangwenit.security.demo.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 5:27 PM
 * @Version 1.0
 **/
public class MyPasswordEncoder implements PasswordEncoder {
    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     *
     * @param rawPassword
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword.toString());
    }
}