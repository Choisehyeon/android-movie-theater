package woowacourse.movie.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R

class SettingFragment : Fragment(), SettingFragmentContract.View {

    override lateinit var presenter: SettingFragmentContract.Presenter
    private lateinit var switch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        switch = view.findViewById<SwitchCompat>(R.id.push_alarm_switch)

        presenter = SettingFragmentPresenter(this)
        presenter.onLoadData()
        onSwitchChangeListener()
        return view
    }

    override fun setSwitchState(value: Boolean) {
        switch.isChecked = value
    }

    override fun getContext(): Context {
        return activity?.applicationContext ?: throw IllegalArgumentException()
    }

    fun onSwitchChangeListener() {
        switch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onSaveData(isChecked)
        }
    }
}
