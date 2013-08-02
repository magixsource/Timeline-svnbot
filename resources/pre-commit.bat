@echo off
setlocal
set REPOS=%1
set TXN=%2
rem check that log message contains at least 5 characters
svnlook log "%REPOS%" -t "%TXN%" | findstr ".........." > nul
if %errorlevel% gtr 0 goto err
exit 0
:err
echo ===========================Empty log message not allowed (at least 5 characters) ! 1>&2
exit 1