package no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.JsonToKotlinLocationForecast


import com.google.gson.annotations.SerializedName

data class Timesery(
    @SerializedName("data")
    val data: Data,
    @SerializedName("time")
    val time: String
)