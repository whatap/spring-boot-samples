# spring-boot-samples

와탭랩스의 자바 애플리케이션 모니터링을 위한 샘플 코드입니다.  

<br/>

## 호환 버전

다음 와탭 자바 에이전트 이후의 버전이 필요합니다.

```
whatap.agent-2.2.5.jar
```

<br/>

## 적용방법

### spring-boot-2.7.x 버전

#### 에이전트 설정

[whatap.conf]
```
mtrace_rate=100
trace_basetime=0
trace_http_header_enabled=true
trace_kafka_header_enabled=true
trace_sql_param_enabled=true
weaving=spring-boot-2.7.x,tomcat9
```


### spring-boot-3.x.x 버전

#### 에이전트 설정

[whatap.conf]
```
mtrace_rate=100
springboot_tomcat10_enabled=true
trace_basetime=0
trace_http_header_enabled=true
trace_kafka_header_enabled=true
trace_sql_param_enabled=true
weaving=spring-boot-3.x,tomcat10
```

<br/>

## 자바 설정

### Java 17

자바 옵션 추가
```
--add-opens=java.base/java.lang=ALL-UNNAMED
```

<br/>

## 테스트 

### spring-gateway

```
curl http://localhost:8080

curl http://localhost:8080/redirect
```


### spring-kafka

```
curl http://localhost:8080/api/user/kafka/test?message={message}
```


### spring-r2dbc

테이블 생성  
```
CREATE TABLE tb_post(id BIGINT AUTO_INCREMENT, title varchar(255), author varchar(255), content TEXT, PRIMARY KEY (id));
CREATE TABLE tb_user(id BIGINT AUTO_INCREMENT, name varchar(45) NOT NULL, age INT NOT NULL, info  TEXT, ord decimal, created_at datetime, PRIMARY KEY (id));
```

tb_post 
```
curl http://localhost:8080/api/posts

curl http://localhost:8080/api/posts/find?title={title}

curl http://localhost:8080/api/posts/{id}

curl http://localhost:8080/api/posts/save-one

curl http://localhost:8080/api/posts/save-all

curl http://localhost:8080/api/posts/delete/{id}

curl http://localhost:8080/api/posts/update/{id}
```

tb_user
```
curl http://localhost:8080/api/users

curl http://localhost:8080/api/users/{id}

curl http://localhost:8080/api/users/save-one

curl http://localhost:8080/api/users/save-all

curl http://localhost:8080/api/users/delete/{id}

curl http://localhost:8080/api/users/update/{id}
```


### spring-webflux

```
curl http://localhost:8082/api/request
```
