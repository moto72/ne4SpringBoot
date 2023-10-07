ne4SpringBoot开发框架是一个基于springboot Framework为基础的开发框架，避免了重复发明轮子的同时，也根据自身的业务进行个性化的拓展。

当前ne4SpringBoot的版本为：
```
<dependency>
    <groupId>com.tmsps</groupId>
    <artifactId>ne4SpringBoot</artifactId>
    <version>3.1.3.1</version>
</dependency>
```


ne4SpringBoot升级至springboot 3，同时同步了springboot的版本号，创建了新的分支。

包结构：
![输入图片说明](img%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20231007111631.png)

| 名称    | 名称                                 | 说明                                  |
|-------|------------------------------------|-------------------------------------|
| 注解    | com.tmsps.ne4springboot.annotation | annotation中是Ne框架根据业务定义的一些注解         |
| 基础    | com.tmsps.ne4springboot.base       | base中主要为Ne框架的基础方法定义                 |
| 异常    | com.tmsps.ne4springboot.exception  | exception，这个在实际业务中使用的场景较少           |
| 数据持久  | com.tmsps.ne4springboot.orm        | orm是Ne框架持久层的核心工具包                   |
| Token | com.tmsps.ne4springboot.token      | token用于一些数据的安全机制                    |
| 工具    | com.tmsps.ne4springboot.util       | utils集合了一些常用的工具，起始是想着规避太多的第三方工具包的引用 |


### 最佳实践

构建一个maven项目（根据个人的习惯选择eclipse或者IDEA），在pom.xml中添加引用的库：

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.1.3</version>
	</parent>

	<groupId>com.xingge</groupId>
	<artifactId>xg-erp</artifactId>
	<version>v1.0.0</version>
	<packaging>jar</packaging>

	<name>service-boot</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.version>17</java.version>
		<maven.compiler.source>17</maven.compiler.source>
		<maven.compiler.target>17</maven.compiler.target>
		<spring.boot>3.1.3</spring.boot>
		<ne4SpringBoot.version>3.1.3.1</ne4SpringBoot.version>
		<druid.version>1.2.18</druid.version>
		<java-jwt.version>4.0.0</java-jwt.version>
		<captcha.version>2.2.2</captcha.version>
		<qiniu.version>7.11.0</qiniu.version>
		<easypoi.version>4.4.0</easypoi.version>
		<rocketmq.version>5.0.5</rocketmq.version>
		<ip2region.version>2.6.6</ip2region.version>
		<tio.version>3.8.5.v20230901-RELEASE</tio.version>
		<redisson.version>3.17.7</redisson.version>
		<skipTests>true</skipTests>
	</properties>

	<dependencies>
		<!-- Spring.boot -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>

		<!-- ORM -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-3-starter</artifactId>
			<version>${druid.version}</version>
		</dependency>
		<dependency>
			<groupId>com.tmsps</groupId>
			<artifactId>ne4SpringBoot</artifactId>
			<version>${ne4SpringBoot.version}</version>
		</dependency>

		<!-- 缓存 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		<dependency>
                    <groupId>org.apache.commons</groupId>
                    <artifactId>commons-pool2</artifactId>
		</dependency>

		<!-- websocket 
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-websocket</artifactId>
		</dependency> -->

		<!-- 工具集 -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<!-- 热部署 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>

		<!-- 权限控制 -->
		<dependency>
			<groupId>com.auth0</groupId>
			<artifactId>java-jwt</artifactId>
			<version>${java-jwt.version}</version>
		</dependency>

		<!-- 验证码 -->
		<dependency>
			<groupId>com.pig4cloud.plugin</groupId>
			<artifactId>captcha-spring-boot-starter</artifactId>
			<version>${captcha.version}</version>
		</dependency>

		<!-- poi -->
		<dependency>
			<groupId>cn.afterturn</groupId>
			<artifactId>easypoi-base</artifactId>
			<version>${easypoi.version}</version>
		</dependency>
		<!-- 七牛云存储 -->
		<dependency>
			<groupId>com.qiniu</groupId>
			<artifactId>qiniu-java-sdk</artifactId>
			<version>${qiniu.version}</version>
		</dependency>
		<!-- rocketmq -->
		<dependency>
			<groupId>org.apache.rocketmq</groupId>
			<artifactId>rocketmq-client-java</artifactId>
			<version>${rocketmq.version}</version>
		</dependency>
		<!-- MQTT 
		<dependency>
			<groupId>org.eclipse.paho</groupId>
			<artifactId>org.eclipse.paho.client.mqttv3</artifactId>
			<version>1.2.5</version>
		</dependency>-->
		<dependency>
			<groupId>org.eclipse.paho</groupId>
			<artifactId>org.eclipse.paho.mqttv5.client</artifactId>
			<version>1.2.5</version>
		</dependency>
		<!-- ip2region -->
		<dependency>
			<groupId>org.lionsoul</groupId>
			<artifactId>ip2region</artifactId>
			<version>${ip2region.version}</version>
		</dependency>

		<!-- redisson -->
		<dependency>
			<groupId>org.redisson</groupId>
			<artifactId>redisson</artifactId>
			<version>${redisson.version}</version>
		</dependency>

		<dependency>
			<groupId>org.t-io</groupId>
			<artifactId>tio-core</artifactId>
			<version>${tio.version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <scope>test</scope>
		</dependency>
	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<mainClass>com.xingge.pro.Application</mainClass>
					<addResources>true</addResources>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>repackage</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>${maven.compiler.source}</source>
					<target>${maven.compiler.target}</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>

```

Ne4Spring框架还有很多有趣的方法供发掘，也欢迎共同完善开发框架，毕竟框架选得好，头发掉的少！