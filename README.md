Amazon Test Products
====================

Configuration
-------------
Use ***config.properties*** to set some properties.

How to run
----------
Use ***mvn clean test -Damazon.password=PASSWORD -Damazon.username=AMAZON_USER
                      -Dproduct.list.url=URL_AMAZON -Dstop.until.page=PAGE_NUM 
                      -Ddelay.seconds=SECONDS -P windows***