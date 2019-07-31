package ru.startandroid.widgetsbase.ui.widgets

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_widgets.*
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.ui.widgets.adapter.WidgetAdapter
import javax.inject.Inject

// TODO implement MV*, task 48

class WidgetsFragment : Fragment() {

    companion object {
        fun newInstance(): WidgetsFragment = WidgetsFragment()
    }

    @Inject
    lateinit var widgetAdapter: WidgetAdapter

    @Inject
    lateinit var widgetsRepository: WidgetDataRepository

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_widgets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = widgetAdapter

        widgetsRepository.getEnabledWidgets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            widgetAdapter.setWidgets(it)
                        },
                        {
                            Log.e("qweee", "getEnabledWidgets error", it)
                        }
                )

        floatingActionButton.setOnClickListener {
            var myAction = Uri.parse("app://organizer/widgets/config")
            var intent = Intent(Intent.ACTION_VIEW, myAction)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}