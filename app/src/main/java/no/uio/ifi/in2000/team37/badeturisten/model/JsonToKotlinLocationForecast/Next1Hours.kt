package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinLocationForecast


import com.google.gson.annotations.SerializedName

data class Next1Hours(
    @SerializedName("details")
    val details: DetailsXX,
    @SerializedName("summary")
    val summary: Summary
)