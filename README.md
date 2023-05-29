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

