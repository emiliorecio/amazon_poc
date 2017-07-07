@ECHO OFF
SET URL1="https://www.amazon.es/s/ref=sr_pg_2?fst=as%3Aoff&rh=n%3A667049031%2Cn%3A938008031%2Ck%3Aordenador+portatil%2Cp_n_feature_fifteen_browse-bin%3A8178957031%2Cp_n_shipping_option-bin%3A2019494031&page=2&keywords=ordenador+portatil&ie=UTF8&qid=1499455001"
SET URL2=""
SET URL3=""
SET URL4=""
SET URL5=""
SET STOP1=1
SET STOP2=1
SET STOP3=1
SET STOP4=1
SET STOP5=1

SET USER=
SET PASSWORD=
SET DELAY=6

ECHO ================================================== Amazon Search #1 ==================================================
mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

ECHO ================================================== Amazon Search #2 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

REM ECHO ================================================== Amazon Search #3 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

REM ECHO ================================================== Amazon Search #4 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

REM ECHO ================================================== Amazon Search #5 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

