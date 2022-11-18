package com.winniecake.wcalendarlib.ui.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarPager(
    state: PagerState,
    currentPageStartDate: LocalDate,
    nextPageStartDate: LocalDate,
    previousPageStartDate: LocalDate,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    content: @Composable (pageStartDate: LocalDate) -> Unit
) {

    LaunchedEffect(state.currentPage) {
        if (state.currentPage == 0) { // previous
            onPreviousPage()
            state.scrollToPage(1)
        } else if (state.currentPage == 2) { // next
            onNextPage()
            state.scrollToPage(1)
        }
    }

    HorizontalPager(
        count = 3,
        state = state,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) { currentPage ->
        when (currentPage) {
            0 -> {
                content(previousPageStartDate)
            }
            2 -> {
                content(nextPageStartDate)
            }
            else -> {
                content(currentPageStartDate)
            }
        }
    }
}