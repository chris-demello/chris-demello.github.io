/*
 * Christopher DeMello
 * Professor Conlan
 * CS499
 * Created: 1/20/25
 *
 * Part of Planned Enhancement - Category 1: Software Design
 * Enhance security by using password hashing
 *
 * Password hashing logic based on Java PBKDF2 implementations
 * described in the Baeldung tutorial “Hashing a Password in Java”.
 * See: https://www.baeldung.com/java-password-hashing
 * (last updated August 20, 2025)
 * This article explains PBKDF2, salting, key derivation, and Java security best
 * practices for password storage.
 */

package com.CS360.weighttracker.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/*
* This is a helper class to bolster password security. It generates a random salt. Then hashes
* a password using PBKDF2. It verifies a login password by hashing the input password the same way and
* compares it to the stored hash.
*/
public class PasswordUtils {
    //A random generator used for cryptography
    private static final SecureRandom RNG = new SecureRandom();
    private static final int SALT_BYTES = 16;
    private static final int KEY_BITS = 256;
    //How many iterations PBKDF2 will run
    public static final int ITERS = 120_000;

    //stop instantiation
    private PasswordUtils() {}

    //Create a salt and returns it as Base64 string so it can be stored in the database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String generateSalt() {
        byte[] salt = new byte[SALT_BYTES];
        RNG.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    //Takes input password, Base64 salt string, and iteration count and returns PBKDF2 hash string
    //to store in database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String hashPassword(
            String password,
            String saltBase64,
            int iterations
    ) {
        PBEKeySpec spec = null;
        try {
            byte[] salt = Base64.getDecoder().decode(saltBase64);

            spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    iterations,
                    KEY_BITS
            );
            //Sets up hashing algorithm "PBKDF2 +HMAC-SHA256"
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Password Hashing failed", e);
        } finally {
            //clears password memory
            if (spec != null) spec.clearPassword();
        }
    }
    //Checks whether a login attempt is correct by re-hashing the input password using the stored
    //salt and iterations. Then it compares the result to the stored hash
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean verifyPassword(
            String inputPassword,
            String storedHash,
            String storedSalt,
            int storedIterations
    ) {
        //Prevents null pointer exception
        if (inputPassword == null || storedHash == null || storedSalt == null){
            return false;
        }
        //Recreates hash from input password
        String inputHash = hashPassword(inputPassword, storedSalt, storedIterations);
        //Compares input and stored hash
        return inputHash.equals(storedHash);
    }
}
