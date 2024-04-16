package no.uio.ifi.in2000.team37.badeturisten.domain

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

@RequiresApi(Build.VERSION_CODES.O)
class CombineBeachesUseCase (
    private val beachRepository : BeachRepositoryImp,
    private val osloKommuneRepository : OsloKommuneRepositoryImp
){
    private val defaultDispatcher = Dispatchers.Default

    suspend operator fun invoke(): List<Beach> = withContext(defaultDispatcher) {
        val beachesFromMet = beachRepository.getBeachObservations().value
        val beachesFromOsloKommune = osloKommuneRepository.makeBeaches(0.0, 0.0)

        combineBeaches(beachesFromMet = beachesFromMet, beachesFromOsloKommune = beachesFromOsloKommune)
    }

    /**
     * This function removes duplicates by overwriting beaches from MET with beaches from Oslo Kommune.
     * In the case of two beaches having the same name, they are combined by taking the temperature
     * from MET, and adding it to the beach from O-K, which has facilities
     */
    fun combineBeaches(beachesFromMet: List<Beach>, beachesFromOsloKommune: List<Beach>): List<Beach> {
        val combinedMap = beachesFromMet.associateBy { it.name }.toMutableMap()

        beachesFromOsloKommune.forEach { beach ->
            if (combinedMap[beach.name] != null && combinedMap[beach.name]?.waterTemp != null) {
                beach.waterTemp = combinedMap[beach.name]?.waterTemp
            }
            combinedMap[beach.name] = beach
        }
        return combinedMap.values.toList()
    }

}