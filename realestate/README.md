# RealEstate

This is an TransmogrifAI project created with the 'simple' template.

## Prerequisites

- Java 1.8
- Scala 2.11.12
- Spark 2.2.1
- IntelliJ Idea 2017+ recommended
- OTransmogrifAI 0.3.4


## Structure

The primary build file is in `build.gradle`.
This file defines dependencies on Scala, Spark, and TransmogrifAI, and also defines how the project will be built
and deployed.

The primary sources for your project live in `src/main/scala`.
The spark application that you should run whenever you want to train/score/evaluate/etc. is the RealEstate
file in `src/main/scala/com/salesforce/app`.
Definitions for your features should reside in `src/main/scala/com/salesforce/app/Features.scala`, while the code that defines
where to get feature data from, what models to use, and any evaluation metrics lives in the application file.

## Workflow

You can run build commands by running `./gradlew` in this directory. Make sure that you have Spark installed, and that your
`SPARK_HOME` environment variable set to where you installed Spark.

### Building
To build the project, run `./gradlew build`. This will compile your sources and tell you of any compile errors.

### Training

Note: this platform runs on Spark, so you must download Spark 2.2.1 (prebuilt against hadoop 2.7), unpack and export `SPARK_HOME` before trying to run.

To train your project, run

```
./gradlew -q sparkSubmit -Dmain=com.salesforce.app.RealEstate -Dargs="--run-type=train --model-location /Users/aborra/ep/CaliforniaHousing/./realestate/build/spark/model --read-location House=/Users/aborra/ep/CaliforniaHousing/data/cadata.csv"
```

### Scoring
To score your project, run

```
./gradlew -q sparkSubmit -Dmain=com.salesforce.app.RealEstate -Dargs="--run-type=score --model-location /Users/aborra/ep/CaliforniaHousing/./realestate/build/spark/model --read-location House=/Users/aborra/ep/CaliforniaHousing/data/cadata.csv
--write-location /Users/aborra/ep/CaliforniaHousing/./realestate/build/spark/scores"
```

Replace the `read-location` parameter with whatever file you want to read for scoring.

## Evaluation
To evaluate your project, run

```
./gradlew -q sparkSubmit -Dmain=com.salesforce.app.RealEstate -Dargs="--run-type=evaluate --model-location /Users/aborra/ep/CaliforniaHousing/./realestate/build/spark/model
 --read-location House=/Users/aborra/ep/CaliforniaHousing/data/cadata.csv
 --write-location /Users/aborra/ep/CaliforniaHousing/./realestate/build/spark/eval
 --metrics-location /Users/aborra/ep/CaliforniaHousing/./realestate/build/spark/metrics"
```

## Read More

- [TransmogrifAI](https://github.com/salesforce/TransmogrifAI)
- [Wiki](https://github.com/salesforce/TransmogrifAI/wiki)
- [Hello World examples](https://github.com/salesforce/TransmogrifAI/tree/master/helloworld)
