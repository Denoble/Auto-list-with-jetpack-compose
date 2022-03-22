package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FacetCountMap(
    val bedType: BedType = BedType(),
    val bodyStyle: BodyStyle = BodyStyle(),
    val cabType: CabType = CabType(),
    val dealerIndustry: DealerIndustry = DealerIndustry(),
    val driveType: DriveType = DriveType(),
    val engines: Engines = Engines(),
    val exteriorColor: ExteriorColor = ExteriorColor(),
    val fuelType: FuelType = FuelType(),
    val interiorColor: InteriorColor = InteriorColor(),
    val make: Make = Make(),
    val mileageRange: MileageRange = MileageRange(),
    val model: Model = Model(),
    val noAccidents: NoAccidents = NoAccidents(),
    val oneOwner: OneOwner = OneOwner(),
    val options: Options = Options(),
    val percentilePrices: PercentilePrices = PercentilePrices(),
    val personalUse: PersonalUse = PersonalUse(),
    val pillarCombos: PillarCombos = PillarCombos(),
    val popularOptions: PopularOptions = PopularOptions(),
    val price: Price = Price(),
    val recordType: RecordType = RecordType(),
    val serviceRecords: ServiceRecords = ServiceRecords(),
    val transmission: Transmission = Transmission(),
    val trim: Trim = Trim(),
    val yearRange: YearRange = YearRange()
)