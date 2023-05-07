package woowacourse.movie.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.fragment.setting.contract.SettingFragmentContract
import woowacourse.movie.fragment.setting.contract.presenter.SettingFragmentPresenter
import woowacourse.movie.util.preference.SettingPreference

class SettingFragment : Fragment(), SettingFragmentContract.View {

    override val presenter: SettingFragmentContract.Presenter by lazy {
        SettingFragmentPresenter(this, SettingPreference(requireContext()))
    }
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onLoadData()
    }

    override fun setSwitchState(value: Boolean) {
        binding.pushAlarmSwitch.isChecked = value
    }

    override fun onSwitchChangeListener() {
        binding.pushAlarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onSaveData(isChecked)
        }
    }

    companion object {
        const val TAG = "setting_fragment"
    }
}
