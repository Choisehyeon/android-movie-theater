package woowacourse.movie.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.PermissionManager
import woowacourse.movie.R
import woowacourse.movie.SettingPreference
import woowacourse.movie.fragment.HistoryFragment
import woowacourse.movie.fragment.HomeFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean -> SettingPreference.setSetting(this, isGranted) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionManager.requestNotificationPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
                requestPermissionLauncher,
            )
        }
        onClickBottomNavItem()
    }

    private fun onClickBottomNavItem() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.navigation_bar)
        bottomNav.run {
            val fragments = mapOf(
                R.id.history to HistoryFragment(),
                R.id.home to HomeFragment(),
                R.id.setting to SettingFragment(),
            )
            setOnItemSelectedListener { item ->
                fragments[item.itemId]?.let { setFragment(it) }
                true
            }
            selectedItemId = R.id.home
        }
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }
}
