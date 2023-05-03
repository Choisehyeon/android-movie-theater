package woowacourse.movie.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.SettingPreference

class SettingFragment : Fragment(R.layout.fragment_setting), SettingFragmentContract.View {

    override val presenter: SettingFragmentContract.Presenter by lazy {
        SettingFragmentPresenter(this, SettingPreference(requireContext()))
    }
    private lateinit var switch: SwitchCompat
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
}
