# Fast-Architecture系统概览

## 系统集成

- mybatis-plus 3.x
- redis 6.x
- swagger 3.x
- mysql 8.x

## 项目结构

- advice: 异常处理
- aspect: 切面处理
- cache: 内存缓存
- config: 系统及组件配置
- constant: 常量类
- context: 线程上下文等
- controller: 暴露http接口
- domain: 数据库实体
- enums: 枚举类
- interceptor: 鉴权拦截等
- mapper: 数据库实体操作接口
- model: 系统流转pojo
- service: 业务处理
- task: 定时任务
- utils: 工具类

## 接口路径划分

- /open/**: 免鉴权免登录接口，比如登录接口之类
- /common/**: 要求登录免鉴权接口，比如文件上传之类
- /admin/**: 要求鉴权要求登录相关接口，比如各种数据管理操作接口
- /api/**: 要求加签验签接口，用于系统内部使用
- /test/**: 免鉴权免登录接口，比如各种数据测试之类

## 业务集成

- RBAC权限模型
- 自定义权限拦截
- AccessLog访问日志
- OperationLog操作日志
- RedisLockUtil分布式锁工具类
- i18n国际化