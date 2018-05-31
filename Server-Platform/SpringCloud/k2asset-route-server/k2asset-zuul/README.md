# k2asset-zuul

#### 项目介绍
k2asset-zuul：网关服务。

#### 软件架构
软件架构说明


#### 安装教程

1. 需安装lombok插件

#### 使用说明

1. 日志采用lobback，生成的文件默认在项目目录：logs/项目名称
2. 其他服务的配置文件在resources/properties下，请注意遵守
3. 请将spring.application.name写入bootstrap.yml文件，否则会多生成一份日志。
4. logback-spring.xml在config服务中由于特殊原因需要将app_name写死，请勿修改。

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
