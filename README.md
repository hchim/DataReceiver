# DataReceiver

DataReceiver is designed as a cron job scheduler. It is composed by a group of Job objects.

The SyncCronJob periodically synchronizes with database, which stores the configuration of
 other jobs. For example, the daily job that download the prices of NASDAQ stocks is stored
 like this in the database.
 
 ```
 { "_id" : ObjectId("56ab86d4ea0e7973ce2d2fa5"), "name" : "daily-NASDAQ", "cron_expr" : "0 30 16 ? * MON-FRI", "class_name" : "im.hch.datareceiver.jobs.DailyQuotesUpdateJob", "args" : [ "NASDAQ" ], "timezone" : "America/New_York", "status" : true, "create_time" : ISODate("2016-01-29T15:35:48.769Z") }
 ```

This job use the DailyQuotesUpdateJob class to retrieve symbol prices from the data source.

# Dependencies

- Log4j
- Junit
- Json
- Quartz
- Unirest
- Morphia

# Publish & Deployment

## packaging

mvn package

## Init DB

$./InitDB.sh

## Start

$./run.sh