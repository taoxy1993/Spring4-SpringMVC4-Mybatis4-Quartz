#### Spring4+SpringMVC4+Mybatis3+IDEA+REST风格框架(微信小程序+APP+Boss后台)

- 纯REST风格,是微信小程序、APP、后台管理系统的统一后台,前后端分离

- ResponseUtil类统一响应结构

- ExceptionAspect类统一异常处理

- SecurityAspect类统一验证

- JsonUtils统一处理请求参数json

- FileUploadUtil统一文件上传

- JiguangPush集成极光推送

- MessageUtils集成阿里大鱼短信

- MarkimageUtils类集成图片水印

- HtmlToText类集成富文本转文本

- EmojiUtil类集成emoji表情转换存储(数据库如果为utf-8mb4也不用转)

- RedisSingletonUtil类集成redis默认端口3991,密码www.taoxy.online

- 数据库采用mysql,innodb+utf-8mb4

- CorsFilter类解决跨域请求问题

- SenseAgroRequestWrapper类缓存post方法的body数据

- 集成网易IM聊天

- 集成amr音频转mp3

- 集成文件压缩和打包下载

- 集成websocket

- 集成maven Profiles三种打包配置

- 集成微信支付,退款,消息模版推送

- 数据库方面

  > ```
  > sense_agro_admin表用于boss后台用户管理
  > sense_agro_member表用于微信用户登录小程序后存储信息和openid
  > sense_agro_specialist用于专家app端登录,uname是登录名不是name,uname必须是手机号
  > 且做了手机号的唯一输入,异常是在代码里处理的
  > sense_agro_user表示是记录specialist的登录操作的,可有可无
  > ```

- 接口格式

  ##### 微信小程序登录

  - ##### 请求url

    > applet/user/login/

  - ##### 请求方式

    > post+json

  - ##### 请求参数

    > | 请求参数 | 参数类型 | 参数说明 |
    > | -------- | -------- | -------- |
    > | code     | String   | 微信code |

  - ##### 参数格式

    > ```
    > {
    > "code":"xxxxxx"
    > }
    > ```

  - ##### 返回参数

    > | 返回参数  | 参数类型 | 参数说明                                                     |
    > | --------- | -------- | ------------------------------------------------------------ |
    > | status    | int      | 0:失败,1:成功                                                |
    > | msg       | String   | 成功时为空,失败为具体原因                                    |
    > | content   | Map      |                                                              |
    > | sessionId | String   | 成功为sessionId值,失败为空,之后所有的接口前端都需要带上此参数 |

  - ##### 返回格式

    > ```
    > {
    > 	"status": 1,
    > 	"msg": "",
    > 	"content": {
    > 		"sessionId": "xxxxxxxxxx"
    > 	}
    > }
    > ```

  ##### Boss后台登录

  - ##### 请求url

    > taoxy/boss/login/

  - ##### 请求方式

    > post+json

  - ##### 请求参数

    > | 请求参数 | 参数类型 | 参数说明 |
    > | -------- | -------- | -------- |
    > | name     | String   | 用户名   |
    > | passwd   | String   | 密码     |

  - ##### 参数格式

    > ```
    > {
    > 	"name":"xxx",
    > 	"passwd":"xxx"
    > }
    > ```

  - ##### 返回参数

    > | 返回参数 | 参数类型 | 参数说明                                                     |
    > | -------- | -------- | ------------------------------------------------------------ |
    > | status   | String   | 0账号/密码错误,1登录成功                                     |
    > | msg      | String   | 登录成功/登录失败                                            |
    > | content  | map      | 成功/失败都返回空                                            |
    > | token    | String   | http的请求头里面,之后所有的接口前端都需要带上此参数,且参数名前端需要改为sessionId |

  - ##### 返回格

    > ```
    > {
    > 	"status":"xxx",
    > 	"msg":"",
    > 	"content":{
    > 	}
    > }
    > ```

  ##### 专家APP登录

  #### 1.1 用户登录

  - ##### 请求url

    > taoxy/specialist/login/

  - ##### 请求方式

    > post+json

  - ##### 请求参数

    > | 请求参数 | 参数类型 | 参数说明 |
    > | -------- | -------- | -------- |
    > | uname    | String   | 手机号   |
    > | passwd   | String   | 密码     |

  - ##### 参数格式

    > ```
    > {
    > "uname":"xxxxxx",
    > "passwd":"xxxxx"
    > }
    > ```

  - ##### 返回参数

    > | 返回参数 | 参数类型 | 参数说明                                            |
    > | -------- | -------- | --------------------------------------------------- |
    > | status   | int      | 0:失败,1:成功                                       |
    > | msg      | String   | 成功时为空,失败为具体原因                           |
    > | content  | Map      |                                                     |
    > | token    | String   | http的请求头里面,之后所有的接口前端都需要带上此参数 |

  - ##### 返回格式

    > ```
    > {
    > 	"status": 1,
    > 	"msg": "",
    > 	"content": {
    > 	}
    > }
    > ```