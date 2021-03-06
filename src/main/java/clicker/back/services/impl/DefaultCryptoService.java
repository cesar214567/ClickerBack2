package clicker.back.services.impl;
import clicker.back.services.CryptoService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
@Service
public class DefaultCryptoService implements CryptoService {


    private static final String key = "aesEncryptionKey";
    private static final String initVector = "8035b7f6552fa909";

    @Override
    public String encrypt3(String value) throws Exception {
        TextEncryptor encryptor = Encryptors.text(key,initVector);
        return encryptor.encrypt(value);
    }

    @Override
    public String decrypt3(String value) throws Exception {
        TextEncryptor encryptor = Encryptors.text(key,initVector);
        return encryptor.decrypt(value);
    }


}
