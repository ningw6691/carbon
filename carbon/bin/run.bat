@echo off
echo.
echo 运行双碳管理系统项目
echo.

cd %~dp0
cd ../neu-admin/target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% carbon.jar

cd bin
pause