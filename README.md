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
"bestModelUID" : "gbtr_338af55daf6d",
"testSetEvaluationResults" : {
  "(regEval)_R2" : 0.6638612219644777,
  "(regEval)_RootMeanSquaredError" : 67746.7035913606,
  "(regEval)_MeanAbsoluteError" : 49895.365002267376,
  "(regEval)_MeanSquaredError" : 4.58961584749567E9
},
"bestModelName" : "gbtr_338af55daf6d_5",
"trainingSetEvaluationResults" : {
  "(regEval)_R2" : 0.6755974123855195,
  "(regEval)_RootMeanSquaredError" : 65379.01496137572,
  "(regEval)_MeanAbsoluteError" : 47949.27394384655,
  "(regEval)_MeanSquaredError" : 4.2744155973197894E9
}
```