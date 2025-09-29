https://projectlombok.org/

사이트 이동

Download ->  Download 1.18.38

lombok.jar 파일 다운로드

sts-bundle -> sts-3.9.18.RELEASE

설치 폴더로 복사 후 .jar 파일 실행

----------------------------------------------

ex) 더블 클릭 또는 실행이 안될때는 

해당 경로에서 cmd 창 open 한뒤

java -jar lombok.jar 실행

----------------------------------------------

실행 후 팝업된 창에서 

sts-bundle -> sts-3.9.18.RELEASE -> STS.exe 파일 선택 후 install 진행

STS 종료 후 재시작

----------------------------------------------

lombok library 를 사용하고 하는 프로젝트에 lombok 라이브러리 추가

https://mvnrepository.com/

메이븐리파지토리 사이트에서 lombok 검색

1. Project Lombok
org.projectlombok » lombok

설치한 버전 선택 후 dependency pom.xml에 추가

<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.38</version>
</dependency>

--------------------------------------------------------------------------













