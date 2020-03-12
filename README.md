# :star: PingeBed

:black_nib:真· 本地图床，使用SpringBoot开发，面向用户的网络图床服务。
:joy: 开发时长两年半的个人图床

# 实现
用户在品格图床中上传图片文件(`jpg`/`jpeg`/`png`/`svg`/`gif`/`bmp`/`ico`/`tiff`)，图床会自动把图片上传到程序的`运行目录`中

PingeBed会把用户上传的图片保存到`本地`，而不是公共容器中。

我们提供了两个API让用户可以获取自己的历史记录，这样就不用进入/history页面了(虽然我感觉/history页面很好看:cry:)
	/api/getIp：获取用户的IP地址，为查询历史记录做准备
	/api/getPics?ip=*: 通过IP地址查询历史记录
历史记录通过建立一个文件的方式存储
![image.png](https://pic.stackoverflow.wiki/uploadImages/2409/8900/43f/2f35/859f/8766/3c2e/2a24/2020/03/12/14/41/eac1d8c6-e378-4a05-8f42-356e19dc46e3.png)
	样式  序号=原文件名:/年/日/随机生成的文件名
![image.png](https://pic.stackoverflow.wiki/uploadImages/222/35/243/5/2020/03/12/18/40/1ecfadd2-3f10-4ce0-b736-f2c14d932c51.png)

# :books: 功能

- [x] 自动生成图片对应的`URL格式链接`、`HTML标签格式链接`、`Markdown格式链接`

- [x] 图片链接克隆（转存）功能，可输入图片的URL，图床会自动下载并保存到图床服务器中

- [x] 历史记录功能（按IP地址读取，所以更换IP地址后无法查询）

- [x] 图床管理员后台设置界面（基于配置文件存储，不依赖数据库）

- [x] 仅管理员可上传功能（默认关闭，必须在后台管理员登录后才能上传）

# :underage: 技术使用
#### 前端：
		Jquery
		Bootstrap
		Axios
#### 后端
		Thymeleaf
		Spring Boot
#### 开发工具
		IDEA

# :mag: 展示
##  上传
![image.png](https://pic.stackoverflow.wiki/uploadImages/222/35/243/5/2020/03/12/18/47/de446dcc-0cdb-4c85-aa6e-481816fa51ef.png)
## 历史记录

有时间描述，更加清晰

![image.png](https://pic.stackoverflow.wiki/uploadImages/222/35/243/5/2020/03/12/18/39/2dcfa60e-9de7-49a4-b726-27c5fb184ea0.png)
##  管理
![image.png](https://pic.stackoverflow.wiki/uploadImages/222/35/243/5/2020/03/12/18/48/a6957cfd-72e0-4a5c-b10a-4c8d0ff8a694.png)

## 整体
![success.gif](https://pic.stackoverflow.wiki/uploadImages/222/35/243/5/2020/03/12/19/02/58187c52-0298-4433-8b98-3438df8f7075.gif)

# :clipboard: 使用方法
因为是本地图床，所以不需要配置数据库。如果是使用IDEA运行或者是使用maven打包成war包，图床会自动在项目根目录下创建uploadImages文件夹，并把上传的文件保存在此文件夹内。你可以在FileUtils.java中找到此代码：

```
String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";
```

因为项目使用了Thymeleaf，所以static是存储了静态资源的根目录。
### 	打包War 包:

* 品格图床可以在Tomcat中运行
* 如果你想自己修改品格图床的源码，Clone后在Intellij IDEA中运行，使用Maven - package打包新的war包，新的war包位置在一般在`target`目录中。如图所示：
![image.png](https://pic.stackoverflow.wiki/uploadImages/222/35/243/5/2020/03/12/19/23/9dd3d334-5231-4bf1-8f10-6ccd4fe5d40b.png)

###		:heavy_plus_sign:修改上传文件大小限制

你可以在application.properties文件里修改配置

```
//上传文件大小
spring.servlet.multipart.max-file-size=30MB
//最大请求大小
spring.servlet.multipart.max-request-size=30MB
```
用户会在前端页面看到文件上传限制
###		:no_entry_sign: 注意事项
如果你使用了Tomcat 或 Tomcat和Nginx搭载了Picuang，你可能会遇到上传失败的情况。请按照下方的几个解决办法尝试：

1. Tomcat：context.xml

修改`conf/context.xml`文件，在`</Context>`之前添加一行：

```
<Resources cachingAllowed="true" cacheMaxSize="100000" />
```

2. Tomcat：server.xml

修改`conf/server.xml`文件，在你使用端口的`Connector`配置中添加一条：

```
maxPostSize="209715200"
```

3. Nginx

在你的`location / {`下添加一行：

```
client_max_body_size 100m;`
```

# :heart: Thanks 

感谢[AdlerED](https://github.com/AdlerED)大佬对我的帮助, [必床](https://pic.stackoverflow.wiki/) ,这是他的开源图床项目，我也是受了他的启发才做出了这个项目，所以有很多地方上都有异曲同工之处

# :mailbox_with_mail: 
​		因为个人水平和开发时间原因，所以这个图床项目可能还存在一些bug，如果你发现了bug或者是有一些好的建议可以选择加微信(zq2011001421)和邮箱(zlyszzq@163.com)二选一的方式告诉我。