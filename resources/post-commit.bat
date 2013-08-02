@ECHO OFF
SET REPOS=%1
SET REV=%2
SET DETAILS_FILE=E:\Repositories\svnbot\log\svnfile_%REV%
SET LOG_FILE=E:\Repositories\svnbot\log\svnfile_%REV%_Log
SET JAVA_HOME=C:\Java
set CLASSPATH=E:\Repositories\svnbot\hooks\log4j-1.2.15.jar;E:\Repositories\svnbot\hooks\svnbot.jar
svnlook log -r %REV% %REPOS%>>%DETAILS_FILE%
svnlook author -r %REV% %REPOS% >>%DETAILS_FILE%
svnlook date -r %REV% %REPOS%>>%DETAILS_FILE%
svnlook diff -r %REV% %REPOS%>>%DETAILS_FILE%

java -classpath %CLASSPATH% com.linpeng.svnbot.hooks.PostCommintHook %REV% %DETAILS_FILE% 

rem erase/f/q %DETAILS_FILE%

