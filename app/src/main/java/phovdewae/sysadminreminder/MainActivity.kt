package phovdewae.sysadminreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.view_activities.BottomNavigationViewActivity
import phovdewae.sysadminreminder.view_activities.CardViewActivity
import phovdewae.sysadminreminder.view_activities.RecyclerViewActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val taskAdapter = TaskAdapter(this)
    private lateinit var cardViewActivity: CardViewActivity
    private lateinit var recyclerViewActivity: RecyclerViewActivity
    private lateinit var bottomNavigationViewActivity: BottomNavigationViewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            cardViewActivity = CardViewActivity(cvNewTaskMain)
            recyclerViewActivity = RecyclerViewActivity(rvMain)
            bottomNavigationViewActivity = BottomNavigationViewActivity(bnvMain)

            bottomNavigationViewActivity.onCreate(binding,
                this@MainActivity,
                cardViewActivity,
                taskAdapter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> Toast.makeText(this, R.string.refresh, Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, R.string.settings, Toast.LENGTH_SHORT).show()
        }
        return true
    }
}