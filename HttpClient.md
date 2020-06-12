## HttpClient（version：4.5.10）

目的：熟悉基于http协议的客户端编程工具包，创建http协议的调用点。

测试环境搭建：

springboot + springMVC，内置tomcat容器作为服务端响应请求。

测试内容：



## HttpEntity

FileEntity, StringEntity(JsonEntity)

### 分块传输编码：（chunk）Transfer-Encoding: chunked

场景：动态内容，content-length无法预知；gzip压缩，压缩与传输同时进行。

HTTP1.1采用了持久连接，一次TCP的连接不马上释放，允许多个请求跟响应在一个TCP的连接上发送，所以客户机与服务器需要某种方式来标识一个报文在哪里结束以及下一个报文在哪里开始。简单的方法施使用content-length，但这只有当报文长度可以预先判断的时候才起作用，而对于动态的内容或者在发送数据前不能判定长度的情况下，可以使用分块的方法来传送编码。



### HttpClient interface

定制HTTP协议属性：

Aspect of the HTTP protocol such as redirect or authentication handling or making decision about connection persistence and keep alive duration.

### RequestBody是用在POST请求方式下的，Http1.1协议不支持，如果使用，报错：“Required request body is missing”



### 得到当前系统时间并按特定格式输出

Calendar calendar = Calendar.getInstance();

Date date = calendar.getTime();

sout(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date));

sout(new SimpleDateFormat("HH:mm:ss:SSS").format(date));

### Google guava

```java
//将list转换为特定规则的字符串，result:aa-bb-cc
String result = Joiner.on("-").join(list);
//将map转换为特定规则的字符串，result:xiaoming=12,xiaohong=13
String result = Joiner.on(",").withKeyValueSeparator("=").join(map);
//将String转为特定集合
String str = "1-2-3-4-5";
List<String> list = Splitter.on("-").splitToList(str);
//将String转为特定map
String str = "xiaoming=12,xiaohong=13";
Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(str);
StopWatch 文件 
```

### win8以管理员身份运行cmd方法：

直接到C:/Windows/System32 下面找到cmd.exe右键以管理员身份打开即可

### mongoDB启动：

mongod --dbpath "E:\mongo4.2\db" --logpath "E:\mongo4.2\log\MongoDB.log"



### ExecutorService

invokeAll方法处理一个任务的容器，并返回一个Future的容器。两个容器具有相同的结构：提交的任务容器列表和返回的Future列表存在顺序对应的关系。

### Avoid using non-primitive value as key, use string/number value instead.

![image-20200612185749071](C:\Users\lizhuangjie.chnet\AppData\Roaming\Typora\typora-user-images\image-20200612185749071.png)

使用postman/vue.js模拟上传文件到Spring MVC：

the request was rejected because no multipart boundary was found

解决方法：无需添加请求头：[Content-Type:"multipart-form-data"]，浏览器或者postman会自动添加 multipart boundary。