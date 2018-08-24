# TransmogrifAI-CaliforniaHousing

This repo provides simple steps to automatically generate a [TransmogrifAI](https://github.com/salesforce/TransmogrifAI) app/project and train a model using its AutoML code generation capabilities.

### Dataset
Housing data set from [http://lib.stat.cmu.edu/datasets/houses.zip](http://lib.stat.cmu.edu/datasets/houses.zip) is used for this code generation example.

### Steps

- Clean up the downloaded CSV file and add header/column names.
- Run the following command to automatically infer the column data type and generate an avro schema `transmogrifai gen --input data/cadata.csv --id houseId --response medianHouseValue --auto House RealEstate --overwrite`
- Build the app/project with following command `./gradlew compileTestScala installDist`
- Start training the model with the following command `./gradlew sparkSubmit -Dmain=com.salesforce.app.House -Dargs="--run-type=train --model-location=/tmp/house-model --read-location House=data/cadata.csv"`

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