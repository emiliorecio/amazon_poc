@ECHO OFF
SET URL1="https://www.amazon.de/s/ref=sr_nr_p_n_shipping_option-_0?fst=as%3Aoff&rh=n%3A300992%2Cn%3A2583844031%2Ck%3Aplaystation+4+konsole%2Cp_n_availability%3A181870031%2Cp_n_shipping_option-bin%3A2019341031&keywords=playstation+4+konsole&ie=UTF8&qid=1498835132&rnid=2019340031"
SET URL2=""
SET URL3=""
SET URL4=""
SET URL5=""
SET STOP1=1
SET STOP2=1
SET STOP3=1
SET STOP4=1
SET STOP5=1

SET DELAY=6

ECHO ================================================== Amazon Search #1 ==================================================
mvn clean test -Damazon.password=PASSWORD -Damazon.username=AMAZON_USERNAME -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows
ECHO ================================================== Amazon Search #2 ==================================================
mvn clean test -Damazon.password=PASSWORD -Damazon.username=AMAZON_USERNAME -Dproduct.list.url=%URL1% -Dstop.until.page=%STOP1% -Ddelay.seconds=%DELAY% -P windows
REM ECHO ================================================== Amazon Search #3 ==================================================

REM ECHO ================================================== Amazon Search #4 ==================================================

REM ECHO ================================================== Amazon Search #5 ==================================================


