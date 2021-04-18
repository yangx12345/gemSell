package com.ddys.gemsell.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtils {

	//过期时间3小时
	private final static Long EXPIRE_SECOND = 3 * 60 * 60 * 1000L;

	/**
	 * 生成签名
	 * @param claimMap          claimMap
	 * @param secret            密钥
	 * @param expireMilliSecond 过期时间-毫秒（如果为null，则无过期时间）
	 * @return
	 */
	public static String sign(Map<String, String> claimMap, String secret, Long expireMilliSecond) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		JWTCreator.Builder builder = JWT.create();
		if (claimMap != null && claimMap.size() > 0) {
			for (Map.Entry<String, String> entry : claimMap.entrySet()) {
				String key = entry.getKey();
				if (key == null || key.equals("")) {
					continue;
				}
				builder.withClaim(key, entry.getValue());
			}
		}
		if (expireMilliSecond != null) {
			builder.withExpiresAt(new Date(System.currentTimeMillis() + expireMilliSecond));
		}
		return builder.sign(algorithm);
	}

	/**
	 * 验证token
	 * @param token  token
	 * @param secret 密钥
	 * @return
	 */
	public static DecodedJWT verify(String token, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			Verification verification = JWT.require(algorithm);
			JWTVerifier verifier = verification.build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取JWT中内容
	 * @param jwt       jwt
	 * @param claimName claim名称
	 * @return
	 */
	public static String getClaimValueByJwt(DecodedJWT jwt, String claimName) {
		return jwt.getClaim(claimName).asString();
	}

	/**
	 * 获取token中内容
	 * @param token     token
	 * @param claimName claim名称
	 * @return
	 */
	public static String getClaimValueByToken(String token, String claimName) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim(claimName).asString();
	}
}