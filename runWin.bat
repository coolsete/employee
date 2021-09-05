set PWD=%~dp0
set TEST_JAR="%PWD%build\libs\framey-0.0.1-SNAPSHOT.jar"
set SPRING_CONFIG_NAME="--spring.config.name=application"
set JAVA_OPTS= -Xmx128M -Dspring.profiles.active=prod -agentlib:jdwp=transport=dt_socket,server=y,address=10000,suspend=n

SET TEST_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
%TEST_JAVA_EXE% %JAVA_OPTS% -jar %TEST_JAR% %SPRING_CONFIG_NAME%
