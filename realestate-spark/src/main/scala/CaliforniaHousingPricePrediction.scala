import org.apache.spark.ml.feature.{StandardScaler, VectorAssembler}
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.ml.regression.LinearRegression


//Class to run the California House Prediction Dataset Using Spark
object CaliforniaHousingPricePrediction {

  def main(args: Array[String]): Unit = {

    // setup spark context
    val spark: SparkSession = SparkSession.builder.master("local")
      .appName("California Housing Dataset Prediction").getOrCreate

    //read the data as data frame
    val df = spark.read.format("csv").option("header", "true").load("../data/cadata.csv")

    //print the DF
    df.show()

    // cast all the strings to Double type in Data Frame
    val castedDF = df.columns.foldLeft(df)((current, c) => current.withColumn(c, current(c).cast(DoubleType)))

    castedDF.describe().show()

    // create features
    val featureCols = Array("medianIncome", "housingMedianAge", "totalRooms", "totalBedrooms",
      "population", "households", "latitude", "longitude")

    val vectorAssembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")

    //create Label and features
    val featuresDf = vectorAssembler.transform(castedDF).select("medianHouseValue", "features")

    //print label/feature
    featuresDf.show()

    //standard scaler
    val standardScaler = new StandardScaler()
      .setInputCol("features")
      .setOutputCol("scaledFeatures")
      .setWithStd(true)
      .setWithMean(true)

    val scaler = standardScaler.fit(featuresDf)
    val scaledFeatures = scaler.transform(featuresDf)

    scaledFeatures.show()

    //divide in test and train
    val split = scaledFeatures.randomSplit(Array(.8, .2))

    //linear regression
    val lr = new LinearRegression().setLabelCol("medianHouseValue").setFeaturesCol("scaledFeatures")
      .setMaxIter(100)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    val lrModel = lr.fit(split(0))

    // Print the coefficients and intercept for linear regression
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

    // Summarize the model over the training set and print out some metrics
    val trainingSummary = lrModel.summary
    println(s"numIterations: ${trainingSummary.totalIterations}")
    println(s"objectiveHistory: [${trainingSummary.objectiveHistory.mkString(",")}]")
    trainingSummary.residuals.show()
    println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
    println(s"r2: ${trainingSummary.r2}")

    //Check the prediction on test set
    val testModel = lrModel.transform(split(1))
    testModel.show()
  }
}
