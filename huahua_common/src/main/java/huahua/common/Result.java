package huahua.common;


import lombok.Data;

/**
 * 公共的返回类
 */

@Data
public class Result {

     private boolean flag;//是否成功

     private Integer code;//状态码

     private String message;//请求信息

     private Object  data;//响应的数据

     public Result(boolean flag, Integer code, String message) {
          this.flag = flag;
          this.code = code;
          this.message = message;
     }

     public Result(boolean flag, Integer code, String message, Object data) {
          this.flag = flag;
          this.code = code;
          this.message = message;
          this.data = data;
     }

     public Result() {
     }


}