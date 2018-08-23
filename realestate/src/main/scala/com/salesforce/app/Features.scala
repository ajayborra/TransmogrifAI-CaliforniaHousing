package com.salesforce.app

import salesforce.House
import com.salesforce.op.features.{FeatureBuilder => FB}
import com.salesforce.op.features.types._
import FeatureOps._

trait Features extends Serializable {

  val medianhousevalue = FB
    .Real[House]
    .extract(_.getMedianHouseValue.toReal)
    .asResponse

  val medianincome = FB
    .Real[House]
    .extract(_.getMedianIncome.toReal)
    .asPredictor

  val housingmedianage = FB
    .Real[House]
    .extract(_.getHousingMedianAge.toReal)
    .asPredictor

  val totalrooms = FB
    .Real[House]
    .extract(_.getTotalRooms.toReal)
    .asPredictor

  val totalbedrooms = FB
    .Real[House]
    .extract(_.getTotalBedrooms.toReal)
    .asPredictor

  val population = FB
    .Real[House]
    .extract(_.getPopulation.toReal)
    .asPredictor

  val households = FB
    .Real[House]
    .extract(_.getHouseholds.toReal)
    .asPredictor

  val latitude = FB.Text[House].extract(_.getLatitude.toText).asPredictor

  val longitude = FB.Text[House].extract(_.getLongitude.toText).asPredictor

}

object FeatureOps {
  def asPickList[T](f: T => Any): T => PickList = x => Option(f(x)).map(_.toString).toPickList
}
