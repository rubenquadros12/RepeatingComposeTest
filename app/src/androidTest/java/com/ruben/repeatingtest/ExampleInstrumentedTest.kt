package com.ruben.repeatingtest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import com.adevinta.android.barista.rule.flaky.FlakyTestRule
import com.adevinta.android.barista.rule.flaky.Repeat
import com.google.accompanist.insets.ProvideWindowInsets
import com.ruben.repeatingtest.ui.theme.RepeatingTestTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @get:Rule
    val flakyRule = FlakyTestRule()

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    @Repeat(times = 100)
    fun checkKeyboard() {
        setupCompose()
        with(composeTestRule) {
            onNodeWithText("Hello man!").assertIsDisplayed()
            onNodeWithText("Hey there").assertIsDisplayed()
        }
    }

    private fun setupCompose() {
        scenario.onActivity {
            it.setContent {
                RepeatingTestTheme {
                    ProvideWindowInsets {
                        MyScreen()
                    }
                }
            }
        }
    }
}