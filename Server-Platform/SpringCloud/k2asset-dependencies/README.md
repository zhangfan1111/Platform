# k2asset-dependencies

#### 项目介绍
k2asset-dependencies整体项目依赖，位于最顶层。

#### 软件架构
软件架构说明

![输入图片说明](https://gitee.com/uploads/images/2018/0525/175454_1158fc95_1922807.png "服务说明.png")

#### 使用说明

1. 每次有改后需要重新install
2. install顺序：k2asset-dependencies -> k2asset-start | k2asset-utils -> k2asset-***
3. 服务启动顺序：
    - Rabbitmq | mysql
    - k2asset-eureka    服务中心
    - k2asset-config    配置中心
    - k2asset-zipkin    链路监控
    - k2asset-zuul      网关路由
    - k2asset-turbine   断路器监控中心
    - k2asset-***       其他服务


