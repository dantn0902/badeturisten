package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository
import no.uio.ifi.in2000.team37.badeturisten.model.Beach.Beach

data class BeachUIState(val beach: Beach? = null)

//Viewmodel som henter strom fra kun en strand
class BeachViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val beachName : String = checkNotNull(savedStateHandle["beachName"])
    private val waterTempRepository : WaterTemperatureRepository = WaterTemperatureRepository(
        WaterTemperatureDataSource()
    )

    private val _beachUIState = MutableStateFlow(BeachUIState())
    val beachUIState: StateFlow<BeachUIState> = _beachUIState.asStateFlow()



    /*val waterTemperatureState: StateFlow<WaterTemperatureUIState> = waterTempRepository.getObservations()
        .map { WaterTemperatureUIState(beaches = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WaterTemperatureUIState()
        )*/



    init {
        viewModelScope.launch (Dispatchers.IO) {
            val beachinfo = waterTempRepository.getBeach(beachName)
            _beachUIState.update {beachUIState ->
                beachUIState.copy(beach = beachinfo)
            }
        /*waterTempRepository.loadBeach(beachName)*/
        }
    }
}