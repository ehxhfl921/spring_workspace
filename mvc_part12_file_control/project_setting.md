# File Upload Project Setting

## Servlet Multi-Part Setting

1. web.xml(배포 서술자) DispatcherServlet에 multipart-config 설정 추가

2. servlet-context.xml
	
	- class : org.springframework.web.multipart.support.StandardServletMultipartResolver
	- id 	: multipartResolver
									=> bean 추가
									
## 외부 Library를 활용하여 Spring Container에서 처리
### Server(Tomcat)에 의존적이지 않게 처리하는 방법

1. pom.xml dependency 의존성 추가
	+ commons-fileupload
	
2. servlet-context.xml multipartResolver 추가