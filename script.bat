@ECHO OFF
SET URL1="https://www.amazon.co.uk/s/ref=sr_nr_p_n_shipping_option-_0?fst=as%%3Aoff&rh=n%%3A65801031%%2Ck%%3APhilips+Lumea%%2Cp_n_shipping_option-bin%%3A2023186031&keywords=Philips+Lumea&ie=UTF8&qid=1499666198&rnid=2023185031"
REM SET URL1="https://www.amazon.es/s/ref=sr_nr_p_n_availability_1?rh=n%%3A599382031%%2Ck%%3Axbox+one%%2Cp_36%%3A20000-99999999%%2Cp_n_shipping_option-bin%%3A2019494031%%2Cp_n_availability%%3A831278031&keywords=xbox+one&ie=UTF8&qid=1499661712"
SET URL2=""
SET URL3=""
SET URL4=""
SET URL5=""
SET STOP1=3
SET STOP2=1
SET STOP3=1
SET STOP4=1
SET STOP5=1

SET USER=
SET PASSWORD=
SET DELAY=6

ECHO ================================================== Amazon Search #1 ==================================================
REM echo %URL1%
REM echo URL
mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

ECHO ================================================== Amazon Search #2 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

REM ECHO ================================================== Amazon Search #3 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

REM ECHO ================================================== Amazon Search #4 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

REM ECHO ================================================== Amazon Search #5 ==================================================
REM mvn clean test -Damazon.password=%PASSWORD% -Damazon.username=%USER% -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows

