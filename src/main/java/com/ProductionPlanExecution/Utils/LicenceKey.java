package com.ProductionPlanExecution.Utils;

import java.security.SecureRandom;

public class LicenceKey {
    private static final int KEY_LENGTH = 25;
    public static String generateLicenseKey() {
        StringBuilder keyBuilder = new StringBuilder();

        SecureRandom random = new SecureRandom();
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Generate 25 characters for the license key
        for (int i = 0; i < KEY_LENGTH; i++) {
            // Insert dash every 5 characters
            if (i > 0 && i % 5 == 0) {
                keyBuilder.append('-');
            }

            // Insert 2 alphanumeric characters
            if (i % 5 < 2) {
                keyBuilder.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
            }
            // Insert 2 numeric characters
            else if (i % 5 < 4) {
                keyBuilder.append(random.nextInt(10));
            }
            // Insert 1 random alphanumeric character
            else {
                keyBuilder.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
            }
        }
        return keyBuilder.toString();
    }
}
