package jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryJWT {

    public static void main(String[] args){
        //token 令牌
       String token ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJzdWIiOiLosoLonYkiLCJpYXQiOjE1NTYyODA4MzcsInJvbGVzIjoiYWRtaW4sc2luZ2VyLGRhbmNlciAifQ.jTI24yX77OADI_mxeldunFvJC9SXX0WZoUGs3bUDEW8";

        try {
          Claims body = Jwts.parser().setSigningKey("huahuaCommunity")
                .parseClaimsJws(token).getBody();
            System.out.println("用户的id："+body.getId());
            System.out.println("用户的名称："+body.getSubject());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println("系统时间："+new Date());
           // System.out.println("过期时间:"+sdf.format(body.getExpiration()));
            System.out.println("签发的时间："+ sdf.format(body.getIssuedAt()));
            System.out.println("用户的角色："+body.get("roles"));

        }catch (Exception e){

            System.out.println("签名认证失效 请重新获取");
        }

    }


}