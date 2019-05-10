package jwt;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreatJWT {

    /**
     * 生成 token(jwt)
     */

    public  static void main(String[] args){

        JwtBuilder huahuaCommunity = Jwts.builder().setId("123456")
                .setSubject("貂蝉")//使用者
                .setIssuedAt(new Date()) //jwt的签发时间
               // .setExpiration(new Date(new Date().getTime()+60000))
                .claim("roles","admin,singer,dancer ")
                .signWith(SignatureAlgorithm.HS256, "huahuaCommunity");//加密的算法HS256 加密的签名是 huahuaCommunity

        System.out.println(huahuaCommunity.compact());
       //通过加盐的规则 huahuaCommunity.compact() 获取token令牌


    }

}