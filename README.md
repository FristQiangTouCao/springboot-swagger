###使用说明
######第一步、配置文件配置 application.yml
```$xslt
hongtian:
  api:
    enable: false
    auth: 'weed'
    packages: {"包1":"com.test.controller","包1":"com.test.controller"}
    title: 'test'
    version: '1.0'
    description: '描述'
```
######第二步、在启动类开启api功能@EnableHongTianApi
```$xslt
@Configuration
@SpringBootApplication
@EnableHongTianApi
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```
####参数说明
|key|说明|默认|
|:----:|:----:|:----:|
|enable|是否开启api文档|true|
|auth|作者|空|
|title|文档标题|空|
|version|api版本|1.0|
|description|api描述|空|
|packages|api分组映射,key为组名，value为包路径，可配置多个|空|