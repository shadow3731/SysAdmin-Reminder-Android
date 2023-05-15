package phovdewae.sysadminreminder.view_activities

import android.view.View
import androidx.cardview.widget.CardView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.util.disable
import phovdewae.sysadminreminder.util.enable
import phovdewae.sysadminreminder.util.setDefault

class CardViewActivity(private val cardView: CardView) {

    fun onCreate(binding: ActivityMainBinding, mainActivity: MainActivity) {
        cardView.visibility = View.VISIBLE

        binding.clInnerMain.disable()
        binding.bnvMain.disable()

        binding.bNewTaskCancel.setOnClickListener {
            cardView.visibility = View.GONE

            binding.clInnerMain.enable()
            binding.bnvMain.enable(mainActivity)
            binding.bnvMain.setDefault(mainActivity)
        }
    }
}