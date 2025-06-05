@echo off
PROMPT PRJ-(Prof:Mozar)$G

set JAVA_HOME=C:\tools\jdk-24.0.1

set MAVEN_HOME=C:\tools\apache-maven-3.9.9
rem set MAVEN_OPTS="-s settings.xml"

set PATH=%MAVEN_HOME%\bin;%JAVA_HOME%\bin;%PATH%

cd /d "%~dp0"
cmd
@echo on
