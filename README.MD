chiwoo-web 모듈은 다음과 같은 기능적 Feature 를 지원 합니다.

- 다국어 및 메시지 컴포넌트 
  - 다국어 로케일 식별
  - 다국어 메시지 DataSource 지원 (file, DBMS, CacheDB)
  - 다국어 메시지 Refresh

- Controller Layer 에 대한 예외 클래스와 예외 처리 핸들러 
- Request 요청 메시지에 대한 Validation 처리 (JSR-303 Bean Validation 스펙 준수)

* Next 버전에 추가될 Feature  
- 파일첨부기능
- 사용자 세션 및 인증 처리


## 사전 준비사항 
MARIADB, REDIS 캐시 서버를 사전에 구성 해야 합니다.

- 로컬 maven repository 에 설치 하려면 아래 명령어를 실행해 주세요.
mvn clean source:jar javadoc:jar install