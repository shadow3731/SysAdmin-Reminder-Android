package phovdewae.sysadminreminder.view_activities

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.util.disable
import phovdewae.sysadminreminder.util.enable
import phovdewae.sysadminreminder.util.getPriorities
import phovdewae.sysadminreminder.util.makeLinearBorder
import phovdewae.sysadminreminder.util.setDefault

class CardViewActivity(private val cardView: CardView) {

    class SpinnerAdapter(context: Context, items: Array<String>):
        ArrayAdapter<String>(context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                view.background = ContextCompat.getDrawable(context, R.drawable.spinner_background)
                (view as TextView).setTextColor(Color.BLACK)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.setBackgroundColor(Color.WHITE)
                (view as TextView).setTextColor(Color.BLACK)
                return view
            }
        }

    fun onCreate(binding: ActivityMainBinding, mainActivity: MainActivity) {
        cardView.visibility = View.VISIBLE

        binding.apply {
            clInnerMain.disable()
            bnvMain.disable()

            val adapter = SpinnerAdapter(mainActivity, getPriorities(mainActivity))
            //adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
            sNewTaskPrior.adapter = adapter

            etNewTaskDesc.setOnClickListener {
                makeLinearBorder(it)
            }

            bNewTaskCancel.setOnClickListener {
                cardView.visibility = View.GONE

                clInnerMain.enable()
                bnvMain.enable(mainActivity)
                bnvMain.setDefault(mainActivity)
            }
        }
    }
}