-- 일반 사용자 계정을 만들 수 있도록 내부 설정 변경
ALTER session set "_ORACLE_SCRIPT"=TRUE;

-- Spring용 계정 생성
CREATE USER develop_spring IDENTIFIED BY 12345
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP
QUOTA UNLIMITED ON USERS;

-- 권한 부여
GRANT CONNECT, RESOURCE TO develop_spring;

-- 사용자 계정 제거 및 종속 오브젝트(개체) 제거
DROP USER develop_spring CASCADE;

