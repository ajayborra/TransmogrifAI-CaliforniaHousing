# California Housing Data Set 
Apache Spark Vs TransmogrifAI 

This repo provides simple steps to automatically generate a [TransmogrifAI](https://github.com/salesforce/TransmogrifAI) app/project and train a model using its AutoML code generation capabilities.

### Dataset
Housing data set from [http://lib.stat.cmu.edu/datasets/houses.zip](http://lib.stat.cmu.edu/datasets/houses.zip) is used for this code generation example.

### Steps

- Clean up the downloaded CSV file and add header/column names.
- Clone TransmogrifAI & Build CLI module from latest tag.


```
git clone https://github.com/salesforce/TransmogrifAI.git
cd ./TransmogrifAI && git checkout 0.4.0 && ./gradlew cli:shadowJar
alias transmogrifai="java -cp `pwd`/cli/build/libs/\* com.salesforce.op.cli.CLI"
```

- Fetch Dataset & Generate Real Estate App
```
cd .. && mkdir -p blog/data && cd blog/data
wget https://raw.githubusercontent.com/ajayborra/TransmogrifAI-CaliforniaHousing/master/data/cadata.csv && cd ..
transmogrifai gen --input data/cadata.csv --id houseId --response medianHouseValue --overwrite --auto HouseObject RealEstateApp
cd realestateapp && sed -i 's/1-SNAPSHOT/0/g' build.gradle && ./gradlew compileTestScala installDist
```

Generate and build the real estate app using the California housing dataset.

- Training the models with TransmogrifAI AutoML.

```
./gradlew sparkSubmit -Dmain=com.salesforce.app.RealEstateApp -Dargs="--run-type=train --model-location=./house-model --read-location HouseObject=`pwd`/../data/cadata.csv"
```


### AutoML Generate Model Stats
```
"bestModelUID" : "gbtr_3e954745fd39",
"testSetEvaluationResults" : {
  "(regEval)_R2" : 0.7730732853257829,
  "(regEval)_RootMeanSquaredError" : 55985.45154762118,
  "(regEval)_MeanAbsoluteError" : 38265.15644522252,
  "(regEval)_MeanSquaredError" : 3.1343707849910393E9
},
"bestModelName" : "gbtr_3e954745fd39_15",
"trainingSetEvaluationResults" : {
  "(regEval)_R2" : 0.8191089857403431,
  "(regEval)_RootMeanSquaredError" : 49013.585519052445,
  "(regEval)_MeanAbsoluteError" : 33672.17530515888,
  "(regEval)_MeanSquaredError" : 2.402331565433468E9
}
```
