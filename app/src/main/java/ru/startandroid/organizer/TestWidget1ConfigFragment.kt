package ru.startandroid.organizer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView


private const val ARG_CONFIG = "config"

class TestWidget1ConfigFragment : Fragment() {
    private var config: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var editText: EditText
    lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("qweee", "config onCreate ${hashCode()} $savedInstanceState")
        arguments?.let {
            config = it.getString(ARG_CONFIG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("qweee", "config onCreateView ${hashCode()} $savedInstanceState")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_test_widget1_config, container, false)
        view.findViewById<TextView>(R.id.widgetConfig).text = "widget config $config"

        editText = view.findViewById(R.id.widgetConfigEditText)
        checkBox = view.findViewById(R.id.widgetConfigCheckBox)

        editText.setText("test initial value")
        checkBox.isChecked = true

        return view
    }

    fun getConfig(): String {
        return editText.text.toString()
    }



//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.d("qweee", "config onDestroyView ${hashCode()}")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("qweee", "config onDestroy ${hashCode()}")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d("qweee", "config onStart ${hashCode()}")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("qweee", "config onStop ${hashCode()}")
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Log.d("qweee", "config onAttach ${hashCode()}")
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

//    override fun onDetach() {
//        super.onDetach()
//        Log.d("qweee", "config onDetach ${hashCode()}")
//        listener = null
//    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(config: String) =
                TestWidget1ConfigFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CONFIG, config)
                    }
                }
    }
}
