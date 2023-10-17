package io.github.llnancy.jingtian.javase.java15;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EdECPoint;
import java.security.spec.EdECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.NamedParameterSpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * Edwards-Curve Digital Signature Algorithm（爱德华兹曲线数字签名算法）
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK15 2023/7/19
 */
public class EdDSA {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        // example: generate a key pair and sign
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();
        Signature sig = Signature.getInstance("Ed25519");
        sig.initSign(kp.getPrivate());

        byte[] msg = "java15".getBytes();
        sig.update(msg);
        byte[] sign = sig.sign();
        System.out.println(Base64.getEncoder().encodeToString(sign));

        // example: use KeyFactory to construct a public key
        KeyFactory kf = KeyFactory.getInstance("EdDSA");
        boolean xOdd = false;
        BigInteger y = new BigInteger("1");
        NamedParameterSpec spec = new NamedParameterSpec("Ed25519");
        EdECPublicKeySpec pubSpec = new EdECPublicKeySpec(spec, new EdECPoint(xOdd, y));
        PublicKey publicKey = kf.generatePublic(pubSpec);
        System.out.println(publicKey.getAlgorithm());
        System.out.println(Arrays.toString(publicKey.getEncoded()));
        System.out.println(publicKey.getFormat());
    }
}
