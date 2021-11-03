# PIPS-Admin-Server

### Environment Settings.

#### 로컬환경

##### Development Spec
    ```text
    Java: jdk1.8.0_192
    Tomcat: apache-tomcat-8.5.42 (Installer or Portable Version) 
    Maven: apache-maven-3.6.0
    Svn: TortoiseSVN windows installer
    ```
##### Install 순서
    java -> TortoiseSVN -> tomcat -> intelliJ -> tomcat plugin 설치
    
    1. TortoiseSVN 설치 후 소스 체크 아웃 (https://172.20.2.192/svn/pips)
      - 대상 : admin-server & service-server
    2. intelliJ 실행 후 tomcat plugin(intelliJ 설치폴더/lib에 tomcat plugin 압축 풀어서 복사) 설정
    3. intelliJ 에서 Project Import
    4. file - open module setting - project - jdk 버전 1.8
    5. host 파일 수정
        172.20.0.78 pips-db pips-message pips-cache pips-nosql pips-monitor
        112.175.10.219 pips-service   
         
    >>> Admin Server
    #1. maven 
      - profile : local
      - pips-admin-lifecycle
        clean 실행
        install 실행
    #2. 서버 설정(edit configuration) - tomcat runner install 
          Name: Proejct Name
          Tomcat Installation: Installed Tomcat Directory (Recommend: Tomcat 8.5.24)
          Modules: Context: /, DocBase: /target/ROOT Folder
    
    *  암복호화 처리  
      cd C:\workPJ\암복호화처리\jasypt-1.9.3\bin
      decrypt input="복호화대상 문자열" password="kHG+8KTJnkBq73vTx0PleUdD7NUqMo+gRXwm9RBYMGA=" algorithm="PBEWITHMD5ANDDES"
      encrypt input="암호화대상 문자열" password="kHG+8KTJnkBq73vTx0PleUdD7NUqMo+gRXwm9RBYMGA=" algorithm="PBEWITHMD5ANDDES"
      
    >>> service Server
    #1. maven 
      - profile : local
      - pips-admin-lifecycles
        clean 실행
        install 실행
    #2. src/main/java/com/pip/service/LocalDeploy.java 우클릭 후 Run LocalDeploy.main()   
    


##### Database
 
  - 사용할 Database 및 Table, Data 생성
    
    ```text
    pips Database와 Table이 생성되어있다면 넘어가도 됨.
    admin server는 pips 데이터베이스를 기반으로 사용하고 nisf에서 제공되는 일부 테이블을 추가하여 사용된다.
    만약 database가 생성되지 않았거나, 테이블이 일부 누락되어있다면 아래의 스크립트를 전체 실행하거나 일부 실행하여 해결한다.
    
    푸르지오 스마트홈 플랫폼 테이블설계서 SQL Script 사용자앱_20191128_v1.0.sql 실행
    푸르지오 스마트홈 플랫폼 테이블설계서 SQL Script 관리자웹_20191128_v1.0.sql 실행
    ```

  - 사용할 Database 정보 설정
    개발환경: local, dev, dev-azure, production
    src/main/resources/{개발환경}/application.properties

    ```text
    application.properties는 암호화 되어있는 정보이다.
    암호화 관련 처리는 아래 파일을 참고하여, 사용할 property 키를 암복호화 후 사용한다. 
    
    푸르지오 스마트홈 플랫폼 관리자 웹 암호화가이드_20191210_v1.1.docx
 
    예시) 암호화할 키 정보
    jdbc.url=jdbc:mysql://{host}:{port}/{db_name}?useUniCode=true&characterEncoding=utf8&autoReconnect=true
    jdbc.username=username
    jdbc.password=password
    ```

##### Server

1. Intellij (Version: 2019.1.3 Community, Recommend: run as Administrator Mode: Tomcat Run Access Failed Issue)
  
      ```text
      - VCS -> Checkout From Version Control -> SVN -> add pips svn url, user authentication -> PIPS-Admin-Server/trunk/{repo}
      - [Tomcat runner plugin for IntelliJ](https://plugins.jetbrains.com/plugin/8266-tomcat-runner-plugin-for-intellij)에서 Download Plugin 최신것 받기.
      - 2018.11.21 기준: plugin version 1.4, Intellij IDEA Community 2018.2.6
      ```
  
      ```text
      아래 두가지 중 한가지 방법 선택
      - 다운로드 받은 파일을 복사해서 Intellij 설치된 폴더의 plugin 폴더 아래 복사 붙여넣기 후 Intellij Restart
      - File -> Settings -> Plugin -> Install Plugin From Disk 선택 후 압축푼 plusin폴더에서 tomcat.jar 선택 후 Restart
      ```
        
      - Run/Debug Configuration에서 Tomcat Runner 생성확인 후 링크의 스크린샷 참조 및 아래 명령어대로 입력 후 실행
  
      ```text
      Name: Proejct Name
      Tomcat Installation: Installed Tomcat Directory (Recommend: Tomcat 8.5.24)
      Modules: Context: /, DocBase: /target/{Project Name} Folder
      ```
  
2. Eclipse (Test Version: Neon.3 Release (4.6.3))

    ```text
    - Window -> Show View -> Select Other -> SVN -> add pips svn url, and user authentication -> check out PIPS-Admin-Server/trunk/{repo}
    - Project 우클릭 configure - convert to maven project -> Project 우클릭 maven - update project
    - Window -> Show View -> Servers -> Add Tomcat (Recommend: Tomcat 8.5.24) Server 
    - Select Server -> Add and Remove -> PIPS-Admin-Server
    - Config Server -> Change Timeouts start value: 90
    - Click Modules Tab -> Edit Web Modules Path: /
    ```
   
* Issue

    ```text
    - 위 가이드에서는 해당 프로젝트를 svn에서 import후 java, maven, svn를 설정하는 과정을 생략되었다.
    - 위 3가지에서 문제가 발생할 경우 아래와 같은 설정을 확인한다.
    1. svn
    * file -> version control -> subversion
    설치한 svn경로가 use custom configuration directory에 설정되어있는지 확인
    
    2. maven
    * file -> build, execution, deployment -> build tools -> maven
    Maven home directory에 설치한 maven 경로 혹은 Bundled (maven 3)이상으로 설정되어있는지 확인
    이 부분이 문제가 없다면, use settings file, local repository도 확인이 필요함.
    
    3. java
    * 해당 root 프로젝트 클릭 후 Project Settings의 Modules에 Module SDK에 설치한 Java버전이 맞게 설정되어있는지
    확인
    * 해당 root 프로젝트 클릭 후 Platform Settings의 SDKs에 JDK home path에 설치한 Java버전이 맞게 설정되어있는지
    확인
    ```