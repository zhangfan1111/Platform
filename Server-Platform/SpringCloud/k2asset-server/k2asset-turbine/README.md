# k2asset-turbine

#### 项目介绍
k2asset-turbine将每个服务Hystrix Dashboard数据进行了整合，可查看所有断路器信息

#### 软件架构
软件架构说明



#### 使用说明

1. 集成Hystrix Dashboard，可访问其他断路器界面http://127.0.0.1:8811/hystrix/查看。输入：http://127.0.0.1:8767/turbine.stream，点击monitor stream。
2. 需要在application.yml文件中配置需要监控的服务id