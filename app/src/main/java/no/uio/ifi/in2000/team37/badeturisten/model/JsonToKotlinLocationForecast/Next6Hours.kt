package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinLocationForecast


import com.google.gson.annotations.SerializedName

data class Next6Hours(
    @SerializedName("details")
    val details: Details,
    @SerializedName("summary")
    val summary: Summary
)