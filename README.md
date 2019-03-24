# Wal Accounting Backend

## Prerequisites

 - Scala
 - sbt
 - react/redux

## Running the Server

The sbt-revolver plugin provides two commands to start and stop the application as a background process:

```
sbt:wal-accounting-backend> reStart
sbt:wal-accounting-backend> reStop
```

Of course you can also use `sbt`'s triggered execution to restart the server on file changes:

```
sbt:typelevel-workshop> ~reStart
``


