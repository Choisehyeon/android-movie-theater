package woowacourse.movie.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.util.preference.SettingPreference
import woowacourse.movie.fragment.setting.contract.SettingFragmentContract
import woowacourse.movie.fragment.setting.contract.presenter.SettingFragmentPresenter

class SettingFragment : Fragment(), SettingFragmentContract.View {

    override val presenter: SettingFragmentContract.Presenter by lazy {
        SettingFragmentPresenter(this, SettingPreference(requireContext()))
    }
    private lateinit var switch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switch = view.findViewById(R.id.push_alarm_switch)
        presenter.onLoadData()
    }

    override fun setSwitchState(value: Boolean) {
        switch.isChecked = value
    }

    override fun onSwitchChangeListener() {
        switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onSaveData(isChecked)
        }
    }

    companion object {
        const val TAG = "setting_fragment"
    }
}
