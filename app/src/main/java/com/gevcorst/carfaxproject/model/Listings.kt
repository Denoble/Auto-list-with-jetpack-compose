package com.gevcorst.carfaxproject.model


import android.media.Image
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonClass

@Entity(tableName = "carlistings")
@JsonClass(generateAdapter = true)
data class Listings(
    val accidentHistory: AccidentHistory = AccidentHistory(),

    val atomOtherOptions: List<String>,
    val dealer: Dealer = Dealer(),
    val images: Images = Images(),
    val advantage: Boolean = false,
    val backfill: Boolean = false,
    val badge: String = "",
    val bedLength: String = "",
    val bodytype: String = "",
    val cabType: String = "",
    val certified: Boolean = false,
    val currentPrice: Int = 0,
    val dealerType: String = "",
    val displacement: String = "",
    val distanceToDealer: Double = 0.0,
    val drivetype: String = "",
    val engine: String = "",
    val exteriorColor: String = "",
    val firstSeen: String = "",
    val followCount: Int = 0,
    val followedAt: Long = 0,
    val following: Boolean = false,
    val fuel: String = "",
    val hasViewed: Boolean = false,
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String = "",
    val imageCount: Int = 0,
    val interiorColor: String = "",
    val isEnriched: Boolean = false,
    val isOemPromoted: Boolean = false,
    val isOemRefundFlag: Boolean = false,
    val listPrice: Int = 0,
    val make: String = "",
    val mileage: Int = 0,
    val model: String = "",
    val mpgCity: Int = 0,
    val mpgHighway: Int = 0,
    val noAccidents: Boolean = false,
    val oneOwner: Boolean = false,
    val onePrice: Int = 0,
    val onlineOnly: Boolean = false,
    val personalUse: Boolean = false,
    val placed: Boolean = false,
    val recordType: String = "",
    val sentLead: Boolean = false,
    val serviceRecords: Boolean = false,
    val sortScore: Double = 0.0,
    val stockNumber: String = "",
    val subTrim: String = "",
    val tpCostPerVdp: Double = 0.0,
    val tpEligible: Boolean = false,
    val transmission: String = "",
    val trim: String = "",
    val vdpUrl: String = "",
    val vehicleCondition: String = "",
    val vin: String = "",
    val windowSticker: String = "",
    val monthlyPaymentEstimate: MonthlyPaymentEstimate = MonthlyPaymentEstimate(),
    val onePriceArrows: List<OnePriceArrow> = listOf(),
    val ownerHistory: OwnerHistory = OwnerHistory(),
    val serviceHistory: ServiceHistory = ServiceHistory(),
    val topOptions: List<String> = listOf(),
    val vehicleUseHistory: VehicleUseHistory = VehicleUseHistory(),
    val year: Int = 0,
    val listingStatus: String = ""
) {


}

class Converters {
    @TypeConverter
    fun accidentHistory(value:AccidentHistory) = Gson().toJson(value)

    @TypeConverter
    fun accidentHistory(json:String):AccidentHistory{
        val gson = Gson()
        val list = object:TypeToken<AccidentHistory>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun string(value:List<String>) = Gson().toJson(value)

    @TypeConverter
    fun string(json:String):List<String>{
        val gson = Gson()
        val list = object:TypeToken<List<String>>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun dealer(value:Dealer) = Gson().toJson(value)

    @TypeConverter
    fun dealer(json:String):Dealer{
        val gson = Gson()
        val list = object:TypeToken<Dealer>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun images(value: Images) = Gson().toJson(value)

    @TypeConverter
    fun images(json:String):Images{
        val gson = Gson()
        val list = object:TypeToken<Images>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun monthlyPaymentEstimate(value: MonthlyPaymentEstimate) = Gson().toJson(value)

    @TypeConverter
    fun monthlyPaymentEstimate(json:String):MonthlyPaymentEstimate{
        val gson = Gson()
        val list = object:TypeToken<MonthlyPaymentEstimate>(){}.type
        return gson.fromJson(json,list)
    }

    @TypeConverter
    fun onePriceArrow(value:List< OnePriceArrow>) = Gson().toJson(value)

    @TypeConverter
    fun onePriceArrow(json:String):List<OnePriceArrow>{
        val gson = Gson()
        val list = object:TypeToken<List<OnePriceArrow>>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun ownerHistory(value:OwnerHistory) = Gson().toJson(value)

    @TypeConverter
    fun ownerHistory(json:String):OwnerHistory{
        val gson = Gson()
        val list = object:TypeToken<OwnerHistory>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun serviceHistory(value:ServiceHistory) = Gson().toJson(value)

    @TypeConverter
    fun serviceHistory(json:String):ServiceHistory{
        val gson = Gson()
        val list = object:TypeToken<ServiceHistory>(){}.type
        return gson.fromJson(json,list)
    }
    @TypeConverter
    fun vehicleUseHistory(value:VehicleUseHistory) = Gson().toJson(value)

    @TypeConverter
    fun vehicleUseHistory(json:String):VehicleUseHistory{
        val gson = Gson()
        val list = object:TypeToken<VehicleUseHistory>(){}.type
        return gson.fromJson(json,list)
    }
}