package no.uio.ifi.in2000.team37.badeturisten.data.beach

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

@RequiresApi(Build.VERSION_CODES.O)
class BeachRepository {
    //henter fra oslo kommune repository
    val osloKommuneRepository: OsloKommuneRepository = OsloKommuneRepository()

    //water temp
    private val waterTempDataSource: WaterTemperatureDataSource = WaterTemperatureDataSource()

    suspend fun waterTempGetData(): List<Tsery> {
        return waterTempDataSource.getData(59.91, 10.74, 10, 50)
    }

    //flows
    private val beachObservations = MutableStateFlow<List<Beach>>(listOf())
    private val favouriteObservations = MutableStateFlow<List<Beach>>(listOf())
    //henter flows
    fun getBeachObservations() = beachObservations.asStateFlow()
    fun getFavouriteObservations() = favouriteObservations.asStateFlow()
    //oppdaterer flows
    suspend fun loadBeaches() {
        val observationsFromDataSource = waterTempGetData()

        //oppdaterer i homeviewmodel i stedet
        beachObservations.update {
            makeBeaches(observationsFromDataSource)
        }
    }

    suspend fun makeBeaches(data: List<Tsery>): List<Beach> {
        return try {
            //gjoer data om til liste med strender
            val liste: MutableList<Beach> = mutableListOf<Beach>()
            data.forEach { data ->
                val beachName = data.header.extra.name
                // oppretter strand objekter og legger til i liste
                val waterTemperature = data.observations.first().body.value.toDoubleOrNull() ?: 0.0
                val position = data.header.extra.pos

                liste.add(Beach(beachName, position, waterTemperature, false))
            }

            return liste
        } catch (e: Exception) {
            Log.d("beach repository", "failed to make beaches")
            Log.e("beach repository", e.message.toString())
            emptyList<Beach>()
        }
    }

    suspend fun getVannkvalitet(lat: Double?, lon: Double?): BadevannsInfo? {
        return osloKommuneRepository.getVannkvalitetLoc(lat = lat, lon = lon)
    }

    suspend fun getBeach(beachName: String): Beach? {
        //METODE FOR AA HENTE EN STRAND BASERT PAA LOC ELLER NAVN?
        //val observationsFromDataSource = datasource.getData(59.91, 10.74)
        val observationsFromStateFlow = getBeachObservations()
        var beachlist: List<Beach> = observationsFromStateFlow.value
        beachlist = beachlist.filter { beach -> beach.name == beachName }
        return beachlist.firstOrNull()
    }

    suspend fun getFavourites(): List<Beach> {
        val observationsFromStateFlow = getBeachObservations()
        var beachlist: List<Beach> = observationsFromStateFlow.value
        //beachlist.forEach { beach -> Log.d("BeRepo, getFavourites", "${beach.name}: ${beach.favorite}") }
        //favourites ble ikke endret paa etter at jeg trykket paa knapp i card
        Log.d("BeRepo, getFavourites", "for filter: $beachlist")
        beachlist = beachlist.filter { beach -> beach.favorite }
        //alltid tom etter
        Log.d("BeRepo, getFavourites", "etter filter: $beachlist")
        favouriteObservations.update {
            beachlist
        }
        return beachlist
    }

}