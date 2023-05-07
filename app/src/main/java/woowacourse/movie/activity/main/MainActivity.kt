package woowacourse.movie.activity.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.util.permission.PermissionManager
import woowacourse.movie.R
import woowacourse.movie.util.preference.SettingPreference
import woowacourse.movie.activity.main.contract.MainActivityContract
import woowacourse.movie.activity.main.contract.presenter.MainActivityPresenter
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.fragment.history.HistoryFragment
import woowacourse.movie.fragment.home.HomeFragment
import woowacourse.movie.fragment.setting.SettingFragment

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    override val presenter: MainActivityContract.Presenter by lazy {
        MainActivityPresenter(
            this,
            SettingPreference(this),
        )
    }
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean -> presenter.saveSettingData(isGranted) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        checkPermisson()
        showHomeFragment()
        onClickBottomNavItem()
    }

    private fun onClickBottomNavItem() {
        binding.navigationBar.selectedItemId = R.id.home
        binding.navigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.history -> showHistoryFragment()
                R.id.home -> showHomeFragment()
                R.id.setting -> showSettingFragment()
            }
            true
        }
    }

    private fun checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionManager.requestNotificationPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
                requestPermissionLauncher,
            )
        }
    }

    fun showHomeFragment() {
        setFragment<HomeFragment>(HomeFragment.TAG)
    }

    fun showHistoryFragment() {
        setFragment<HistoryFragment>(HistoryFragment.TAG)
    }

    fun showSettingFragment() {
        setFragment<SettingFragment>(SettingFragment.TAG)
    }

    inline fun <reified T : Fragment> setFragment(tag: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.fragments.forEach(::hide)
            val fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
            if (fragment == null) {
                add<T>(R.id.fragment_container_view, tag)
            } else {
                show(fragment)
            }
        }
    }
}
