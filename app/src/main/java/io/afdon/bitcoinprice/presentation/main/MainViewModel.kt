package io.afdon.bitcoinprice.presentation.main

import android.util.Log
import android.view.View
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
import io.afdon.bitcoinprice.domain.entity.RequestState
import io.afdon.bitcoinprice.domain.usecase.GetBitcoinDataUseCase
import io.afdon.bitcoinprice.domain.usecase.RefreshBitcoinDataUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getBitcoinDataUseCase : GetBitcoinDataUseCase,
    private val refreshBitcoinDataUseCase: RefreshBitcoinDataUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel>

    private val data = getBitcoinDataUseCase.getAll()

    private val _chartData = MutableStateFlow<LineData?>(null)
    val chartData : StateFlow<LineData?> = _chartData

    private val _listData = MutableStateFlow<List<BitcoinDataAdapter.Item>>(arrayListOf())
    val listData : StateFlow<List<BitcoinDataAdapter.Item>> = _listData

    private val _loadingVisibility = MutableStateFlow(View.GONE)
    val loadingVisibility : StateFlow<Int> = _loadingVisibility

    init {
        viewModelScope.launch {
            data.collect {
                _chartData.value = getChartData(it)
                _listData.value = getListData(it)
            }
        }
    }

    fun requestNewData() {
        viewModelScope.launch {
            refreshBitcoinDataUseCase.requestNewBitcoinData().collect {
                when (it) {
                    is RequestState.Loading -> {
                        _loadingVisibility.value = if (it.isLoading) View.VISIBLE else View.GONE
                    }
                    is RequestState.Success -> {
                        Log.d("afdon", "request success")
                    }
                    is RequestState.Error -> {
                        Log.d("afdon", "request error")
                    }
                }
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
        }.takeLast(5).reversed()
    }
}