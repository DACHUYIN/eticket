# 电子票券交易微信小程序
## 更新计划
11月底前完成分类的市场功能
## 项目介绍
之前过年去香港的时候，在大众点评买了太平山的缆车和观景台的电子门票，然后发现买错日期，买的明天的，然而明天早就上了飞机了，故想转手，在大众点评发评论出门票是秒删的，后面想到闲鱼，但是闲鱼也是禁止任何电子票券的交易的。后面只能评论人家的游记，说急出门票。反正就感觉不方便，后面回来之后就想着自己做个微信小程序专门用来交易电子票券或者电影门票啥的（有过因为临时加班导致自己买的电影票报废的情形）。<br>
选择微信小程序，是因为可以减少一个发布前台服务的服务器。虽然个人注册的账号是没法做任何商业小程序的，也没法对接微信支付，但是用来自学仿照闲鱼做个电商小程序也足够了，毕竟也没真想上架商用啥的=-=。。
## 技术选择
![](https://github.com/DACHUYIN/eticket/blob/master/配置文件/images/架构.jpg)
前台：之前用的腾讯原生的语言，后面做了10%的样子，发现对于我这个90%都是写后台的人来说，学腾讯那套太累了。后面全部删掉重构了，改用了uni-app,uni-app是基于vue的，本来之前也有一点vue的经验，所以上手比较容易。<br>
后台：整个流程和上面的图片差不多。买了2台阿里云，然后谷歌云，亚马逊云和微软云都用一年免费试用期，自己加上父母的信息，注册了1台谷歌云，3台亚马逊云和2天微软云，用来模拟整个生产环境。<br>
框架的话，用了Springboot，做了eureka，springcloudgateway,member和transaction这4个微服务。<br>
持久化框架没有选择Mybatis，选择了更加轻量的JPA，可以省去大量的XML文件。复杂的查询打算用elasticsearch去解决的，后面服务器不够了，就没有搭建。<br>
数据库的话用1台微软云和1台亚马逊云做了MySQL集群，然后用shardingsphere用来分库分表，选择的字段是手机号。<br>
缓存的话用了3台亚马逊云搭了redis集群，然后2台阿里云做了双Master双Slave的RocketMQ集群(主从异步)，搭框架的时候想着，整个交易流程，像是发布券码，购买券码之类的数据存储全部通过redis，然后这部分服务都是在transaction微服务上完成的。比如发布券码，redis中存储了数据，然后通过RocketMQ，把数据发送到member微服务，让member微服务去和MySQL进行数据存储。<br>
nginx这块，手上网段一样的服务器搭了rocketMQ，网段不一样通过keepalive搭建nginx集群没找到合适的解决方法，就用了一台niginx服务器。前台去请求nginx服务器，nginx配置写了springcloudgateway服务器的地址，然后springcloudgateway和member、transaction都是注册在eureka上的，就可以通过springcloudgateway去调用其它2个微服务，springcloudgateway是双节点，达到了高可用的目的。<br>


