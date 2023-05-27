package phovdewae.sysadminreminder.util

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.View
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.tasks.TaskAdapter

class LoadingBlocker {

    private var animation: AnimationDrawable? = null

    fun showLoading(binding: ActivityMainBinding, taskAdapter: TaskAdapter) {
        binding.apply {
            clInnerMain.disable(false)
            rvMain.disable(false, taskAdapter)
            bnvMain.disable()

            cvLoading.visibility = View.VISIBLE

            if (animation == null) animation = ivLoading.drawable as AnimationDrawable?
            animation?.start()
        }
    }

    fun hideLoading(binding: ActivityMainBinding, context: Context, taskAdapter: TaskAdapter) {
        binding.apply {
            clInnerMain.enable(false, context)
            rvMain.enable(false, context, taskAdapter)
            bnvMain.enable()

            cvLoading.visibility = View.GONE

            animation?.stop()
        }
    }
}