package cn.wrxdark.common.security.token;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;

/**
 * SignWithUtil
 *
 */
public class SecretKeyUtil {
    private static final String base64String="cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
    public static SecretKey generalKey() {
        //自定义
        byte[] encodedKey = Base64.decodeBase64(base64String);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public static SecretKey generalKeyByDecoders() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64String));

    }
}
