package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.SettingPreference

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val switch = view.findViewById<SwitchCompat>(R.id.push_alarm_switch)
        setSwitchState(switch)
        onSwitchChangeListener(switch)
        return view
    }

    fun setSwitchState(switch: SwitchCompat) {
        switch.isChecked = this.activity?.let { SettingPreference.getSetting(it) }
            ?: throw IllegalArgumentException()
    }

    fun onSwitchChangeListener(switch: SwitchCompat) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            SettingPreference.setSetting(this.requireActivity(), isChecked)
        }
    }
}
