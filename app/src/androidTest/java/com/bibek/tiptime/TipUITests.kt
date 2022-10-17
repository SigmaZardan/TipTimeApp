package com.bibek.tiptime

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.bibek.tiptime.ui.theme.TipTimeTheme
import org.junit.Rule
import org.junit.Test

class TipUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip_no_round_up() {

        composeTestRule.setContent {
            TipTimeTheme {
                TipTimeScreen()
            }

        }

        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10.00")

        composeTestRule.onNodeWithText("Tip percent(%)")
            .performTextInput("20.00")

        composeTestRule.onNodeWithText("Tip amount: $2.00").assertExists()
    }

}
