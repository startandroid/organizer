package ru.startandroid.organizer.exchange.presentation.widget.content

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.widget_exchange_contents.*
import ru.startandroid.organizer.exchange.presentation.R
import ru.startandroid.organizer.exchange.presentation.widget.content.adapter.ExchangeRateListAdapter
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.BaseWidgetContent
import javax.inject.Inject

class ExchangeWidgetContent @Inject constructor(val adapter: ExchangeRateListAdapter)
    : BaseWidgetContent<ExchangeWidgetData>() {

    override fun getLayoutId(): Int = R.layout.widget_exchange_contents

    override fun onDataSet(widgetData: ExchangeWidgetData) {
        if (widgetData.rates.isEmpty()) {
            showNoData()
        } else {
            showData(widgetData)
        }
    }

    private fun showNoData() {
        groupNoData.visibility = View.VISIBLE
        groupData.visibility = View.GONE
    }

    private fun showData(widgetData: ExchangeWidgetData) {
        groupNoData.visibility = View.GONE
        groupData.visibility = View.VISIBLE
        textUpdateTime.text = widgetData.updateDate
        textErrorMessage.text = widgetData.errorMessage
        adapter.submitList(widgetData.rates)
    }

    override fun onViewInflated(widgetView: View) {
        super.onViewInflated(widgetView)
        listData.layoutManager = LinearLayoutManager(context)
        listData.adapter = adapter
    }

}