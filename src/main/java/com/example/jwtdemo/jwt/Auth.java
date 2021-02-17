package com.example.jwtdemo.jwt;

import com.example.jwtdemo.filter.JWTCheckFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.KeyPair;

@RestController
@RequestMapping("auth")
public class Auth {
    private final static Logger logger = LoggerFactory.getLogger(JWTCheckFilter.class);

    /**
     *使用HS256对称加密算法时，签名密钥和解密密钥必须相同
     */
    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 使用RS256非对称加密算法时，签名密钥使用私钥（keyPair.getPrivate()），解密密钥使用公钥（keyPair.getPublic()）
     */
    private final static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    @GetMapping("jws")
    public String getJws(){
        logger.info("key1 -->{}",key.toString());
        logger.info("key2 -->{}",new String(key.getEncoded()));
        logger.info("key3 -->{}", Encoders.BASE64.encode(key.getEncoded()));

        String jws = buildJws();
        return jws;
    }

    public static void parseJws(String jws){
        logger.info("key11 -->{}",key.toString());
        logger.info("key21 -->{}",new String(key.getEncoded()));
        logger.info("key31 -->{}", Encoders.BASE64.encode(key.getEncoded()));
        Assert.isTrue(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals("jwtDemo"),"token校验失败");
    }

    public static String buildJws(){
        logger.info("key.getFormat() -->{}",key.getFormat());
        logger.info("key1 -->{}",key.toString());
        logger.info("key2 -->{}",new String(key.getEncoded()));
        logger.info("key3 -->{}", Encoders.BASE64.encode(key.getEncoded()));
        return Jwts.builder().setSubject("jwtDemo").signWith(key).compact();
    }

    public static void main(String[] args) {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512
        keyPair.getPrivate();
        keyPair.getPublic();
        parseJws(buildJws());
    }

}