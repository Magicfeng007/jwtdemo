package com.example.jwtdemo.jwt;

import com.example.jwtdemo.filter.JWTCheckFilter;
import io.jsonwebtoken.Claims;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class Auth {
    private final static Logger logger = LoggerFactory.getLogger(JWTCheckFilter.class);

    /**
     *使用HS256对称加密算法时，签名密钥和解密密钥必须相同
     *
     * 使用随机数字生成key(使用随机数字 new 一个 SecretKeySpec )
     */
    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 使用RS256非对称加密算法时，签名密钥使用私钥（keyPair.getPrivate()），解密密钥使用公钥（keyPair.getPublic()）
     */
    private final static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    private final static String secreKey = "gfhrewrwfgdfg4thytythytmttyh4tyyhtjmhhrrth534trtfgtyhm123321";

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

    /**
     * 自己指定key
     * @return
     */
    public static String buildJws2(){
        logger.info("key.getFormat() -->{}",key.getFormat());
        logger.info("key1 -->{}",key.toString());
        logger.info("key2 -->{}",new String(key.getEncoded()));
        logger.info("key3 -->{}", Encoders.BASE64.encode(key.getEncoded()));
        Map<String,Object> claims = new HashMap<>();
        claims.put("name","zjsmg");

        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(secreKey), SignatureAlgorithm.HS256.getJcaName());
//         根据key生成密钥（会根据字节参数长度自动选择相应的 HMAC 算法）
//        SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secreKey));
        return Jwts.builder().setSubject("jwtDemo").addClaims(claims).signWith(secretKey,SignatureAlgorithm.HS256).compact();
    }

    /**
     * 自己指定key
     * @return
     */
    public static void parseJws2(String jws){
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(secreKey),SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jws).getBody();
        logger.info("---->{}",claims.toString());
        Assert.isTrue(claims.getSubject().equals("jwtDemo"),"token校验失败");
    }

    public static void main(String[] args) {
        /*KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512
        keyPair.getPrivate();
        keyPair.getPublic();
        parseJws(buildJws());*/
        parseJws2(buildJws2());
    }

}
