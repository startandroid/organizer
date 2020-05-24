package ru.startandroid.organizer.exchange.presentation.widget.config

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.widget_exchange_config_fragment.*
import ru.startandroid.device.delegation.viewModel
import ru.startandroid.organizer.exchange.presentation.R
import ru.startandroid.organizer.exchange.presentation.databinding.WidgetExchangeConfigFragmentBinding
import ru.startandroid.organizer.exchange.presentation.widget.config.adapter.ExchangeConfigRateListAdapter
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment

// TODOL add UNDO snackbar for deletion
class ExchangeWidgetConfigFragment : BaseWidgetConfigFragment<ExchangeWidgetConfig>() {

    private val model by viewModel(ExchangeWidgetConfigModel::class.java)

    private val currencyList = listOf("USD", "EUR", "RUB", "BYN")
    private val ratesAdapter = ExchangeConfigRateListAdapter {
        model.deleteRateAtPosition(it)
    }

    override fun getLayoutId(): Int = R.layout.widget_exchange_config_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initModel()
        initView(view)
    }

    override fun fillNewConfig(): WidgetConfig {
        return model.fillNewConfig()
    }

    override fun checkIfNewConfigIsValid(): Boolean {
        return model.checkIfNewConfigIsValid()
    }

    private fun initModel() {
        model.init(getOriginalConfig())

        model.getRates().observe(viewLifecycleOwner, Observer {
            ratesAdapter.submitList(it)
        })

        model.showToast.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun initView(view: View) {
        val binding = DataBindingUtil.bind<WidgetExchangeConfigFragmentBinding>(view)
        binding?.model = model

        spinnerCurrencyTo.adapter = createCurrencySpinnerAdapter()
        spinnerCurrencyFrom.adapter = createCurrencySpinnerAdapter()
        if (currencyList.size > 1) {
            spinnerCurrencyTo.setSelection(1)
        }

        listData.layoutManager = LinearLayoutManager(requireContext())
        listData.adapter = ratesAdapter

        buttonAdd.setOnClickListener {
            addRate()
        }
    }

    private fun addRate() {
        val currencyFrom = spinnerCurrencyFrom.selectedItem as String
        val currencyTo = spinnerCurrencyTo.selectedItem as String
        model.addRate(currencyFrom, currencyTo)
    }

    private fun createCurrencySpinnerAdapter(): SpinnerAdapter {
        return ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item,
                currencyList
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

}