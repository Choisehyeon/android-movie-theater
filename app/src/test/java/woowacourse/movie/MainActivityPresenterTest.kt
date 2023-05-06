package woowacourse.movie

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.main.contract.MainActivityContract
import woowacourse.movie.activity.main.contract.presenter.MainActivityPresenter
import woowacourse.movie.util.preference.DataPreference

internal class MainActivityPresenterTest {
    private lateinit var presenter: MainActivityPresenter
    private lateinit var view: MainActivityContract.View
    private lateinit var dataPreference: DataPreference

    @Before
    fun setUp() {
        view = mockk()
        dataPreference = mockk()
        presenter = MainActivityPresenter(view, dataPreference)
    }

    @Test
    fun `데이터가 잘 저장되었는지 확인`() {
        val slot = slot<Boolean>()
        every { dataPreference.saveData(capture(slot)) } answers { println("slot = ${slot.captured}") }

        presenter.saveSettingData(true)

        assertEquals(true, slot.captured)
        verify { dataPreference.saveData(slot.captured) }
    }
}
