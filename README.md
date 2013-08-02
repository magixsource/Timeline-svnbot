Timeline-svnbot
===============

A simple hook application beside magixsource/Timeline

Makesure
--------
  + You are using SVN
  + SVN server has install JDK and can be use (at least JDK1.4 and JDK1.6 is recommanded)
  + Last but not least, you like magixsource/Timeline web application and wanna use

Quickstart
----------
  + jar timeline-svnbot and take timeline-svnbot.jar to $SVN_REPO/your_repo/hooks/
  + copy /resources/post-commit.bat to &SVN_REPO/your_repo/hooks
  + copy /resources/async-http-client-1.6.3.jar and /resources/log4j-1.2.15.jar to $SVN_REPO/your_repo/hooks
  + config post-commit hook program and save it (SEE NEXT)
  + Ok success 

Configuration
-------------

<h3>post-commit.bat</h3>
<pre><code>DETAILS_FILE=E:\Repositories\svnbot\log\svnfile_%REV%</code></pre>
Detail file:  Where commit logfile save forder.
    
<pre><code>LOG_FILE=E:\Repositories\svnbot\log\svnfile_%REV%_Log</code></pre> 
Log file: Where application runtime log
    
<pre><code>JAVA_HOME=C:\Java</code></pre>
Java Home : Are you kidding me ? you don't know it ! ^_^
    
<pre><code>CLASSPATH=E:\Repositories\svnbot\hooks\log4j-1.2.15.jar;E:\Repositories\svnbot\hooks\svnbot.jar</code></pre>
Class path: in application demo,just tell java env know,Where timeline-svnbot.jar and log.jar was
    

<pre><code>svnlook log -r %REV% %REPOS%>>%DETAILS_FILE%
svnlook author -r %REV% %REPOS% >>%DETAILS_FILE%
svnlook date -r %REV% %REPOS%>>%DETAILS_FILE%
svnlook diff -r %REV% %REPOS%>>%DETAILS_FILE%</code></pre>
SVN program APIs was

<pre><code>java -classpath %CLASSPATH% com.linpeng.svnbot.hooks.PostCommintHook %REV% %DETAILS_FILE%</code></pre>
Program timeline-svnhook main entrance.
  
<h3>PostCommintHook.java</h3>
Main configuration in PostCommitHook is the method called "invoke"
You can extends it and do what your wanna,in application demo,I just make commit information translate as a simple text file
 And you can do :
  + Call magixsource/Timeline WebService to tell it some guy is commit right now.
  + Save it as logfile (as default) and magixsource/Timeline scan logfile forder interval
  + And so on...
 
