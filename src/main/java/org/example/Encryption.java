package org.example;

import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encryption {

    // For simplicity, attach public & private keys as fields.
    private static String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApz8LT3nIQ/03THODuq5QiuGDevoCaYrr2dZlO+LOQqzvm389bid4Jl//Etaqm5j5/gqPOkCDiJd2eZJZwqBiZjNv3hBVisnF0aB/nQh1ws7iXTxNQQlLUtNZyhGDFsP/q9jTi/wbj/AcvHvIrwS+IhIKZ1jpWBn6U7jcaWWttky/tnEFCCI5CRWpiqeSft6S5CHDRIzL3o0H9lSdyyM4Xy/rlFc4sYkDOuweSWspiObo6udmXT+ch1tIxrRz3EYJKEQHLXFz8gAe740XNiME2gYHiWm75Vek7VbfPUsdKo+dNNqaeP1WkKXBC2ezCNLV+B7gw3bqs0B8ohEXHj1nswIDAQAB";
    private static String privateKeyString = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnPwtPechD/TdMc4O6rlCK4YN6+gJpiuvZ1mU74s5CrO+bfz1uJ3gmX/8S1qqbmPn+Co86QIOIl3Z5klnCoGJmM2/eEFWKycXRoH+dCHXCzuJdPE1BCUtS01nKEYMWw/+r2NOL/BuP8By8e8ivBL4iEgpnWOlYGfpTuNxpZa22TL+2cQUIIjkJFamKp5J+3pLkIcNEjMvejQf2VJ3LIzhfL+uUVzixiQM67B5JaymI5ujq52ZdP5yHW0jGtHPcRgkoRActcXPyAB7vjRc2IwTaBgeJabvlV6TtVt89Sx0qj5002pp4/VaQpcELZ7MI0tX4HuDDduqzQHyiERcePWezAgMBAAECggEAC/aKqL2FfDYwJEP9BQzz+O3cTvR0Lg2AWl18x0sCxaqDcc7TsOiaLmXeeSqpcHuBolDUtu4nb81PGFobbpG0stUfaDXTO0a8jjFEqjxmhAVIFDLnNG2QRD/sTnPF5Ip85CjHKl5hsFWUu0zAFE4qtYWEgKj6TqbTt5+5BcGpVSlMUCibTErKPp8kLUccOj4DQkIYhhw5sIdUp1BH2pD7WfBdfG+G8Sl0C4SbFazgiJ4nAmhE2v2tJn4NbF0UNpt6RbJaOi9krsS02T9EcmncIzkMLY5S9KUm0Ice1MBpFjLlxfaC0AaHZzm5kxYN2JgJdDsOKE401nZoNQJVa21gFQKBgQDSZohGtAXksTR5Nm0foss/CUPeom6UBM9ORSZnJgEYVfPui04Hp5cnhXp3vAuWQVQC9HLogjW6T9Azo4Kw/btOTKjO5iM09eA3Wx6RS3GDHk5JnM5oJgweut057J1flr0yNXX2+jjK/z/4fY+4LF66TVEXZe4gBTb512vaBQXUjwKBgQDLfjjr7td7r2NvKV8cCegx9Ho5fC/w1dXe+S/JucszU55oaIm7Dczpqb19CKEDaUj1kZOBKdtES6DlAPs/Z7OJ8g/spHQkotXLDtnGSptza8u5qwxJQTt69xKfpuAnZT2ueahB5aIgfgNqWErFzyBJ/3BbHdJXw2KbJufpXjM0nQKBgBmwjz8ix/1ubZvHO3e7ZtmhhfnvF1VXaQCDjPbXOxb/l7Muqy5gFiKrN5LbEQcdnuZldopRK8I98Iv9kP2PXYln7sykaPQUeXYI2Cihb5C06JYwYTtyOdSeuFhUPbroKt2wjWDEmJ6gn+z1V9EDCeXxfs8+kN60BExpMzABoP6dAoGBAK45qKSf/pBgLuMq6cqI1OuIZOk44ZbyjkV01iyBzW9phXPhJSMpAquAb8NQkwLnrtbJyB/LZXdIxT2spHC56ecOgAHdH6+KVz+evGfg0s10mp2hzEgf+LwkNCbK6pnwPWpXsO6SnEfzfBsd8w7/cqbIdDsfAkuv9oneOqr8wxylAoGAcs4ZV9DPtTGlNRzrSCsxufu6C0spr1RBwkqfojzuQgXyeSikTZjB88h4s9rX/aw0+43idxrT6pELxVm4JyhDy+NLpkpC7xgaRivSdAlu5LPMgTTDttuBu9HMELI/6E73uYdC6ZsQ10j8kGeRJ9zqm58UMBDHHgfdwvmjcdnehsQ=";

    @UserFunction
    @Description("Encrypt a string with the predefined key - org.example.encrypt('plain text')")
    public String encrypt(@Name("plaintext") String plainText) throws Exception {
        return encryptText(plainText, publicKeyString);
    }

    @UserFunction
    @Description("Decrypt an encrypted text - org.example.decrypt('encrypted text')")
    public String decrypt(@Name("encryptedText") String encryptedText) throws Exception {
        return decryptText(encryptedText, privateKeyString);
    }

    private static String encryptText(String plainText, String publicKeyStr) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec((publicKeyBytes));
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    private static String decryptText(String encryptedText, String privateKeyStr) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr.getBytes());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }
}
