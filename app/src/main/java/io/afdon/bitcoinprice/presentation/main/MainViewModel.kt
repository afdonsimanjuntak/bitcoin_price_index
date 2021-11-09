package io.afdon.bitcoinprice.presentation.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.afdon.bitcoinprice.di.AssistedViewModelFactory
import io.afdon.bitcoinprice.domain.entity.BitcoinDataEntity
import io.afdon.bitcoinprice.domain.usecase.GetBitcoinDataUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    getBitcoinDataUseCase: GetBitcoinDataUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel>

    private val _chartData = MutableStateFlow<LineData?>(null)
    val chartData : StateFlow<LineData?> = _chartData

    private val _listData = MutableStateFlow<List<BitcoinDataAdapter.Item>>(arrayListOf())
    val listData : StateFlow<List<BitcoinDataAdapter.Item>> = _listData

    init {
        viewModelScope.launch {
            getBitcoinDataUseCase.getBitcoinData().collect {
                _chartData.value = getChartData(it)
                _listData.value = getListData(it)
                Log.d("afdon", "listdata.size: ${listData.value.size}")
            }
        }
    }

    private fun getChartData(data: List<BitcoinDataEntity>) : LineData {
        val values = data.map {
            Entry(it.timeFloat, it.rateFloat)
        }
        val dataSet = LineDataSet(values, "USD")
        val dataSets = arrayListOf<ILineDataSet>().apply {
            add(dataSet)
        }
        return LineData(dataSets)
    }

    private fun getListData(data: List<BitcoinDataEntity>) : List<BitcoinDataAdapter.Item> {
        return data.map {
            BitcoinDataAdapter.Item(it.time, it.rate, it.latitude, it.longitude)
        }.takeLast(5)
    }
}