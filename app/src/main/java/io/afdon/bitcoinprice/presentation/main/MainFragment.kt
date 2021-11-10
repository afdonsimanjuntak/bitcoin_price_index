package io.afdon.bitcoinprice.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import io.afdon.bitcoinprice.R
import io.afdon.bitcoinprice.databinding.FragmentMainBinding
import io.afdon.bitcoinprice.di.SavedStateViewModelFactory
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainFragment @Inject constructor(
    private val factory: SavedStateViewModelFactory
) : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel> {
        factory.create(this@MainFragment)
    }
    private var binding: FragmentMainBinding? = null
    private val adapter = BitcoinDataAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view).apply {
            vm = viewModel
            initChart(lineChart)
            recyclerView.adapter = adapter
        }
        lifecycleScope.launchWhenStarted {
            viewModel.loadingVisibility.collect {
                binding?.bProgress?.visibility = it
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.chartData.collect {
                it?.let { lineData ->
                    binding?.lineChart?.run {
                        data = lineData
                        notifyDataSetChanged()
                        invalidate()
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.listData.collect {
                adapter.listDiffer.submitList(it)
            }
        }
    }

    private fun initChart(lineChart: LineChart) {
        lineChart.xAxis.axisMinimum = 0f
        lineChart.xAxis.axisMaximum = 24f
        lineChart.xAxis.setLabelCount(11, true)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setVisibleXRange(5f, 10f)
    }
}