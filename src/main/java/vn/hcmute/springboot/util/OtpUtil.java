package vn.hcmute.springboot.util;

import java.security.SecureRandom;

public final class OtpUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private OtpUtil() { }
    public static String generateOtp() { return String.format("%06d", RANDOM.nextInt(1_000_000)); }
}
