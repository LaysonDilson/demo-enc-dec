package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class CriptTestResource {

    private  static final String CIPHER = "AES/CBC/PKCS5Padding";
    private  String initializationVector;
    private  String secret;

    @PostMapping("/encrypt")
    public ResponseEntity encr(@RequestBody EncryData encryData) throws Exception {
        init(encryData);
        return ResponseEntity.ok(encrypt(encryData.getStrToEncDec(),encryData.getSalt()));
    }

    private void init(EncryData encryData) {
        this.initializationVector=encryData.getVector();
        this.secret=encryData.getSecret();
    }

    @PostMapping("/decrypt")
    public ResponseEntity decry(@RequestBody EncryData encryData) throws Exception {
        init(encryData);
        return ResponseEntity.ok(decrypt(encryData.getStrToEncDec(),encryData.getSalt()));
    }

    public static SecretKey getAESKeyFromPassword(char[] secret, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // iterationCount = 65536
        // keyLength = 256
        KeySpec spec = new PBEKeySpec(secret, salt, 65536, 256);

        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

    }

    private IvParameterSpec getInitializationVectorSpec() {
        byte[] iv = this.initializationVector.getBytes();
        return new IvParameterSpec(iv);
    }

    private String encrypt(String strToEncrypt, String salt) throws Exception {
        SecretKey secretKey = getAESKeyFromPassword(this.secret.toCharArray(), salt.getBytes());
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, getInitializationVectorSpec());
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }


    private String decrypt(String strToDecrypt, String salt) throws Exception {
        IvParameterSpec ivParameterSpec = getInitializationVectorSpec();
        SecretKey secretKey = getAESKeyFromPassword(this.secret.toCharArray(), salt.getBytes());

        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
}
