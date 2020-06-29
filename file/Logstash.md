### Logstash:

将数据进行过滤和格式化（转成JSON格式），然后传给数据存储或者消息队列，用于后续加工处理。

案例一：app埋点监控，app将埋点日志发送到埋点日志网关，在埋点日志网关通过部署Logstash，将日志发送到Logstash，再有Logstash发送到Kafka，最后由Kafka保存到MongoDB，由大数据系统定时跑批，将埋点统计结果加工出来提供给前端查询。

消息队列Kafka + 日志收集Logstash（看视频）

