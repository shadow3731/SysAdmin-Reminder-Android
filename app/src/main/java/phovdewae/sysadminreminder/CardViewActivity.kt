package phovdewae.sysadminreminder

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

class CardViewActivity(private val cardView: CardView) {

    fun onCreate(binding: ActivityMainBinding, context: Context) {
        cardView.visibility = View.VISIBLE

        binding.clInnerMain.isClickable = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.clInnerMain.foreground = ColorDrawable(ContextCompat.getColor(context, R.color.transparent_grey))
        }

        binding.bNewTaskCancel.setOnClickListener {
            cardView.visibility = View.GONE

            binding.clInnerMain.isClickable = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.clInnerMain.foreground = ColorDrawable(ContextCompat.getColor(context, R.color.transparent))
            }
        }
    }
}