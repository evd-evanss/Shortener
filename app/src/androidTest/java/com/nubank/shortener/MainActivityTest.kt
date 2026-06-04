package com.nubank.shortener

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.nubank.shortener.feature.shortener.presentation.R as ShortenerR
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun opensShortenerScreenAfterSplash() {
        val title = composeRule.activity.getString(ShortenerR.string.shortener_title)
        val recentTitle = composeRule.activity.getString(ShortenerR.string.shortener_recent_title)

        composeRule.waitUntil(timeoutMillis = 3_000) {
            composeRule.onAllNodesWithText(title).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText(title).assertIsDisplayed()
        composeRule.onNodeWithText(recentTitle).assertIsDisplayed()
    }
}
