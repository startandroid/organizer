package ru.startandroid.organizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


private const val ARG_WIDGET_ID = "widget_id"

class WidgetConfigContainerFragment : Fragment() {
    private var widgetId: Int? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var configValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("qweee", "cont onCreate ${hashCode()} $savedInstanceState")
        arguments?.let {
            widgetId = it.getInt(ARG_WIDGET_ID)
        }

        configValue = "config value 1"

        if (savedInstanceState == null) {
            Log.d("qweee", "cont add frag ${hashCode()} $savedInstanceState")
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, TestWidget1ConfigFragment.newInstance(configValue), "config")
                    .commit()
        }

    }

    fun onBackPressed(): Boolean {
        Log.d("qweee", "cont onBackPressed")
        return false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("qweee", "cont onCreateView ${hashCode()} $savedInstanceState")
        val view = inflater.inflate(R.layout.fragment_widget_config_container, container, false)
        view.findViewById<TextView>(R.id.widgetId).text = "Widget ID = $widgetId"
        view.findViewById<Button>(R.id.save).setOnClickListener {
            save()
        }
        return view
    }

    private fun save() {
        (childFragmentManager.findFragmentById(R.id.container) as? TestWidget1ConfigFragment)?.let {
            val c = it.getConfig()
            Log.d("qweee", "new config $c")
        }
    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.d("qweee", "cont onDestroyView ${hashCode()}")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("qweee", "cont onDestroy ${hashCode()}")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d("qweee", "cont onStart ${hashCode()}")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("qweee", "cont onStop ${hashCode()}")
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Log.d("qweee", "cont onAttach ${hashCode()}")
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

//    override fun onDetach() {
//        super.onDetach()
//        Log.d("qweee", "cont onDetach ${hashCode()}")
//        listener = null
//    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(widgetId: Int) =
                WidgetConfigContainerFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_WIDGET_ID, widgetId)
                    }
                }
    }
}
