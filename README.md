# 中科金直播项目对外接口文档  

## 服务端API文档

### 简介

### 调用方式

#### 请求结构

**1.请求地址**  
私有化和混合化部署模式下，为租户自定义域名。  
**2.通信协议**  
HTTP或HTTPS  
**3.请求方法**  
支持的 HTTP 请求方法:GET|POST 

#### 公共参数

公共参数是用于标识用户和接口签名的参数，如非必要，在每个接口单独的接口文档中不再对这些参数进行说明，但每次请求均需要携带这些参数，才能正常发起请求。  

* **HTTP Header 请求头部中，如下：**

<table>
  <tr>
    <th >参数名称</th>
    <th >类型</th>
    <th >必填</th>
     <th >参与签名</th>
    <th >示例值</th>
    <th >描述</th>
  </tr>
  <tr>
    <td>Authorization</td>
    <td>String</td>
    <td>是</td>
    <td>否</td>
    <td>QFW2RpFWbLRCj2byieE7o6NQcoc+/7ja0ziKRhTBA88=</td>
    <td>身份认证头部字段。</td>
  </tr>
  <tr>
    <td>x-version</td>
    <td>String</td>
    <td>是</td>
    <td>是</td>
    <td>1.0</td>
    <td>API版本,若接口没有特别标明，默认为1.0。</td>
  </tr>
  <tr>
    <td>x-nonce</td>
    <td>String</td>
    <td>是</td>
    <td>是</td>
    <td>1223456</td>
    <td>随机字符串，长度不超过32位。</td>
  </tr>
  <tr>
    <td>x-timestamp</td>
    <td>Integer</td>
    <td>是</td>
    <td>是</td>
    <td>1599201549123</td>
    <td>当前 UNIX 时间戳，可记录发起 API 请求的时间（单位/毫秒），注意：如果与服务器时间相差超过5分钟，会引起签名过期错误。</td>
  </tr>
</table>


#### <a name="signMethod">签名方法</a>

**1. 获取安全凭证**  
私有化和混合化模式下会预先提供。 安全凭证包括 secretId 和 secretKey：  

* secretId 用于标识 API 调用者身份  
* secretKey 用于加密签名字符串和服务器端验证签名字符串的密钥。  
* 用户必须严格保管安全凭证，避免泄露。

**2. 生成签名串**  
有了安全凭证SecretId 和 SecretKey后，就可以生成签名串了。以下是生成签名串的详细过程：  
假设用户的 SecretId 和 SecretKey 分别是：  
secretId: zkj*******  
secretKey: 123*******  

> 注意：这里只是示例，请根据用户实际申请的 SecretId 和 SecretKey 进行后续操作！

* 对参数排序  
  首先对参与签名的请求头参数按参数名的字典序（ ASCII 码）升序排序。示例参数的排序结果如下:  

    ```json
  {
    "x-nonce": "123abc",
    "x-secret-id": "amt",
    "x-timestamp": 1599295681000,
    "x-version": "1.0"
  }
    ```

* 拼接请求字符串  
  此步骤生成请求字符串。 将把上一步排序好的请求参数格式化成“参数名称=参数值”的形式，然后将格式化后的各个参数用"&"拼接在一起，最终生成的请求字符串为:  

    ```text
    x-nonce=123abc&x-secret-id=amt&x-timestamp=1635160029299&x-version=1.0
    ```

* 生成签名串  
  此步骤生成签名串。 使用 HmacSHA256 算法对上一步中获得的签名原文字符串进行签名，即可获得最终的签名串。  
  最终得到的签名串为：  

    ```text
    N4An6w/JtW78RbRwYZk9G0vMsVpZFcp07Hu2VarayzI=
    ```

* 拼接Authorization认证头
  此步骤生成最终Authorization请求头。 LIVE前缀 + 空格 + x-secret-id + 冒号 + 签名串，即可获得最终的拼接Authorization。  
  最终得到的签名串为：  

    ```text
    LIVE amt:N4An6w/JtW78RbRwYZk9G0vMsVpZFcp07Hu2VarayzI=
    ```

  **3. 认证头生成Demo(Java)**  

```java
        import javax.crypto.Mac;
        import javax.crypto.spec.SecretKeySpec;
        import java.nio.charset.StandardCharsets;
        import java.security.InvalidKeyException;
        import java.security.NoSuchAlgorithmException;
        import java.util.Base64;
        
        class AuthorizationCheckHandlerTest {
        
            private static String sigByHmacSHA256(String secretId, String secretKey, String nonce, String version) {
                String contentToBeSigned = new StringBuilder().append("x-nonce").append("=").append(nonce)
                        .append("&").append("x-secret-id").append("=").append(secretId)
                        .append("&").append("x-timestamp").append("=").append(System.currentTimeMillis())
                        .append("&").append("x-version").append("=").append(version)
                        .toString();
                try {
                    byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);
                    Mac hmac = Mac.getInstance("HmacSHA256");
                    SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA256");
                    hmac.init(keySpec);
                    byte[] byteSig = hmac.doFinal(contentToBeSigned.getBytes(StandardCharsets.UTF_8));
                    return Base64.getEncoder().encodeToString(byteSig);
                } catch (NoSuchAlgorithmException | InvalidKeyException var13) {
                    throw new RuntimeException("签名失败");
                }
            }
        
            private static String generateAuth(String secretId, String sign) {
                return "LIVE " + secretId + ":" + sign;
            }
        
        
            public static void main(String[] args) {
                //租户身份标识 直播提供
                String secretId = "amt";
                //秘钥 直播提供
                String secretKey = "kpit6xwtpqf28ahfuqen8mixvleajvfh";
                //随机字符串 长度不大于32位
                String nonce = "123abc";
                //版本号，本期固定为1.0
                String version = "1.0";
                String authorization = generateAuth(secretId, sigByHmacSHA256(secretId, secretKey, nonce, version));
                System.out.println("Authorization:" + authorization);
            }
        
        }
```

#### 返回结果

注意：目前只要请求被服务端正常处理了，响应的 HTTP 状态码均为200。例如返回的消息体里的错误码是签名失败，但 HTTP 状态码是200，而不是401。  
**1. 正确返回结果**  
以获取用户签名接口为例，若调用成功，其可能的返回如下为：  

```json
{
    "datas": {
        "userSig": "Jy01P9LCFqqxJDMX7BZLSyNLE2NT41oAP5sviQ__"
    },
    "resp_code": 0,
    "resp_msg": ""
}
```

* `resp_code`|`resp_msg`|`datas`为固定字段，无论请求成功与否，只要 API 处理了，则必定会返回。
* 若请求处理成功，则`resp_code`为0,`datas`为接口定义的输出参数。
* 若请求处理失败，则`resp_code`不为0，`resp_msg`为接口错误描述。

### 接口列表

#### 获取管理后台免登陆url

**1. 接口请求方法&地址**  
GET    {DOMAIN}/backend/external-api/backstageUrl  
**2. payload请求体(body)**  
无

**3. 输出参数**  

<table>
  <tr>
    <th >参数名称</th>
    <th >类型</th>
    <th >必填</th>
    <th >示例值</th>
    <th >描述</th>
  </tr>
  <tr>
    <td>url</td>
    <td>String</td>
    <td>是</td>
    <td>http://amt.livedev2.zkjin.com/web/backstage?Authorization=123</td>
    <td>管理后台免登陆url。</td>
  </tr>
</table>

**4. 输入输出示例**  

* 输入(body)

    无

* 输出

  ```json
  {
    "datas": {
      "url": "http://amt.livedev2.zkjin.com/web/backstage?Authorization=123"
    },
    "resp_code": 0,
    "resp_msg": "SUCCESS"
  }
  ```
