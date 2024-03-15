package no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.JsonToKotlinLocationForecast


import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<Double>,
    @SerializedName("type")
    val type: String
)