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

### 数据库，消息队列，redis

### 智慧理货解决方案：

系统应采用灵活、开放的模块化设计，赋予结构上极大的灵活性，为系统扩展、升级及可预见的管理模式的改变留有余地。

### 系统主要由前端抓拍摄像机负责集装箱图片的采集：

由PLC联动控制主机（识别服务器）负责联动PLC控制信号的转换翻译，并将小车及吊具的实时坐标发给抓拍摄像机获取集装箱实时位置，在集装箱装卸过程中触发不同位置球机对集装箱5个面进行精确抓拍，由高清抓拍摄像机获取的高清视频上传至集装箱识别主机进行集装箱号码机箱型尺码的识别，并上传到监控管理中心做人工验残荷集装箱号码的核对（即实时将识别结果通过提供给的数据接口传输至理货后台系统）。

### 平台总体架构：

集装箱动识别智慧理货系统平台基于“高内聚、低耦合”的原则和顶层模块或的思想而设计，平台各个应用子系统具备良好可移植、伸缩性，适应未来应用动态升级的需要。

第一层为表现层。

第二层为应用层：是面向于用户实际操作的客户端，是通过对基础功能和增值业务的归纳、抽象，生成的各种应用模块，支持不同的业务应用。可以为智慧理货、人工验残、视频监管等提供完整的业务处理流程和高效的办公手段。

第三层为平台服务层：是软件平台系统的核心，采用高性能的应用服务器中间件、各种智能引擎和系统管理工具，为应用层提供基础服务、增值服务、管理策略和方法工具。同时按照所提供的服务来管理、组织、调度设备和信息资源。

第四层为接入层：实现了PLC控制信号、音视频、图片抓拍信息的归纳抽象，并接入到平台进行统一管理、组织和调度，使用户无需关心所使用的设备和信息资源的具体类型，更好的为业务服务。

![image-20200616165524347](C:\Users\lizhuangjie.chnet\AppData\Roaming\Typora\typora-user-images\image-20200616165524347.png)

### 平台模块：

中心管理服务器（CMS）：

web服务器：

通过CMS服务器与系统中各功能服务器交互，提供web配置客户端和web控制客户端访问服务。用户通过IE登录web服务器（基于Apache Tomcat服务）使用平台业务应用功能。

消息队列服务器（HMQ）【Kafka】：

主动将系统的变更信息发送到客户端和相关服务器，让所有信息处理更加实时，同时消息队列服务器的处理机制能有效减少定时更新机制带来的数据库服务器访问压力。

流媒体转发服务器（VTDU）：

流媒体实现从设备取视频码流分发给客户端、存储服务器、联网服务器、解码器等。通过流媒体服务器进行转发，在广域网上只占用一个通道的资源，在局域网内再进行转发，最大限度地利用现有网络资源，使得多客户端同时浏览单路或多路图像得以实现。（将从设备来的一路连接，同时分发给多个客户，从而能有效减少设备端的压力）

报警管理服务器（spring quartz + web界面配置与显示【推送，webSocket】）：

报警管理服务器主要实现系统各种报警事件的转发，提供完善的报警日志管理，方便事后查询检索。

主要负责捕获视频触发的移动丢失、视频遮挡预警；并接受报警主机、硬盘等触发的IO报警，记录所有报警日志。根据用户的报警预案配置，实现相关联动。如客户端联动报警通知、联动短信、邮件、云台预置位调用、电视墙联动视频等相关报警预案。

设备接入服务器：

岸桥图片接入服务器（TDA）：

实现对前端球机及嵌入式集装箱信息识别主机的统一接入。并将抓拍识别信息转发给实时处理统计服务器，将抓拍图片写入图片服务器（PMS）存储。对前端设备的工作转台进行巡检。

图片管理服务器（PMS）：

采用预分配技术，能有效解决传统单张JPEG图片存储，访问效率低下、信息不够安全的问题，实现图片的高效写入和快速访问服务。主要负责交通接入服务器产生的区间合成图片的存储和管理。

状态管理服务器（netty【server】）：

（1）实时采集数据库管理单元、中心管理单元、媒体交换单元等主要设备的CPU、内存利用率、网络收发情况等信息，保证系统设备正常工作。

（2）实时采集磁盘阵列、多媒体接入单元等存储设备的存储状态信息，以及存储设备剩余空间、预计存储时间， 网络连接状况等信息，及时了解存储设备情况。

（3）系统能够设置服务器的新能数据阈值（包括CPU、内存等），当设备超出设定阈值时，产生故障告警，提示相应的工作人员。（系统集成常用的网络故障检测工具，实现自诊断功能）



## Kafka，quartz + web

springboot + quartz实现web端定时任务调度：

http://www.demodashi.com/demo/16172.html