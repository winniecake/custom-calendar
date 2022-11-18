package com.winniecake.wcalendarlib

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.winniecake.wcalendarlib.ui.theme.*
import com.winniecake.wcalendarlib.ui.view.*
import com.winniecake.wcalendarlib.ui.view.OneWeekScreen
import com.winniecake.wcalendarlib.ui.view.TwoWeekScreen
import com.winniecake.wcalendarlib.ui.view.component.CalendarPager
import com.winniecake.wcalendarlib.ui.viewmodel.WCalendarContract
import com.winniecake.wcalendarlib.ui.viewmodel.WCalendarViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun WCalendar(
    modifier: Modifier,
    type: WCalendarPeriod,
    startDayOfWeek: WCalendarStartDayOfWeek,
    theme: WCalendarThemeName = WCalendarThemeName.EarthTones,
    onDaySelected: (LocalDate) -> Unit
) {
    val vm: WCalendarViewModel = hiltViewModel()
    vm.setPeriodMode(type)
    vm.setStartOfWeek(startDayOfWeek)

    val themeColor: WCalendarTheme = when (theme) {
        WCalendarThemeName.Light -> {
            SimpleLight
        }
        WCalendarThemeName.Dark -> {
            SimpleDark
        }
        WCalendarThemeName.EarthTones -> {
            EarthTones
        }
    }

    CalendarScreen(
        modifier = modifier,
        theme = themeColor,
        state = vm.state,
        onNextPeriod = {
            vm.onNextPeriod()
        },
        onPreviousPeriod = {
            vm.onPreviousPeriod()
        },
        onDaySelected = {
            vm.onDaySelected(it)
            onDaySelected(it)
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarScreen(
    modifier: Modifier,
    theme: WCalendarTheme = EarthTones,
    state: WCalendarContract.State,
    onNextPeriod: () -> Unit,
    onPreviousPeriod: () -> Unit,
    onDaySelected: (LocalDate) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 1)
    val coroutineScope = rememberCoroutineScope()

    val calendarWidth = LocalConfiguration.current.screenWidthDp - 10
    val dayOfWeekName = stringArrayResource(id = R.array.dayOfWeek)
    val dayViewSize = calendarWidth / 7

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(theme.bgColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // title
        Row(
            Modifier
                .width(calendarWidth.dp)
                .wrapContentHeight()
                .padding(4.dp)
                .background(theme.headerBgColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = 0)
                    }
                }
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = theme.headerFontColor,
                )
            }

            when (state.period) {
                WCalendarPeriod.Month -> {
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = "${state.currentPageStartDate.year}/${state.currentPageStartDate.month.value}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = theme.headerFontColor
                    )
                }
                WCalendarPeriod.OneWeek -> {
                    val startOfWeek: LocalDate
                    val endOfWeek: LocalDate
                    if(state.startDayOfWeek == WCalendarStartDayOfWeek.SUNDAY) {
                        startOfWeek = state.currentPageStartDate.minusDays(1)
                        endOfWeek = state.currentPageStartDate.minusDays(-5)
                    } else {
                        startOfWeek = state.currentPageStartDate
                        endOfWeek = state.currentPageStartDate.minusDays(-6)
                    }

                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = "${startOfWeek.year}/${startOfWeek.month.value}/${startOfWeek.dayOfMonth}" +
                                " - ${endOfWeek.year}/${endOfWeek.month.value}/${endOfWeek.dayOfMonth}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = theme.headerFontColor
                    )
                }
                WCalendarPeriod.TwoWeek -> {
                    val startOfWeek: LocalDate
                    val endOfWeek: LocalDate
                    if(state.startDayOfWeek == WCalendarStartDayOfWeek.SUNDAY) {
                        startOfWeek = state.currentPageStartDate.minusDays(1)
                        endOfWeek = state.currentPageStartDate.minusDays(-12)
                    } else {
                        startOfWeek = state.currentPageStartDate
                        endOfWeek = state.currentPageStartDate.minusDays(-13)
                    }

                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = "${startOfWeek.year}/${startOfWeek.month.value}/${startOfWeek.dayOfMonth}" +
                                " - ${endOfWeek.year}/${endOfWeek.month.value}/${endOfWeek.dayOfMonth}"
                        ,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = theme.headerFontColor
                    )
                }
            }

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = 2)
                    }
                }
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    tint = theme.headerFontColor,
                )
            }
        }

        // dayOfWeek
        FlowRow(
            modifier = Modifier
                .width(calendarWidth.dp)
                .wrapContentHeight()) {
            for (i in 0..6) {
                Box(
                    modifier = Modifier
                        .width(dayViewSize.dp)
                        .background(theme.dayOfWeekBgColor)
                        .border(BorderStroke(1.dp, SolidColor(theme.dayOfWeekBorderColor))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayOfWeekName[if(state.startDayOfWeek == WCalendarStartDayOfWeek.SUNDAY)((i + 6) % 7)else(i)],
                        fontWeight = FontWeight.Bold,
                        color = theme.dayOfWeekFontColor
                    )
                }
            }
        }

        CalendarPager(
            state = pagerState,
            currentPageStartDate = state.currentPageStartDate,
            nextPageStartDate = state.nextPageStartDate,
            previousPageStartDate = state.previousPageStartDate,
            onPreviousPage = {onPreviousPeriod()},
            onNextPage = {onNextPeriod()}
        ) { month ->
            when (state.period) {
                WCalendarPeriod.Month -> {
                    MonthScreen(
                        modifier = Modifier.width(calendarWidth.dp).wrapContentHeight(),
                        month = month,
                        state = state,
                        dayViewSize = dayViewSize,
                        theme = theme,
                        onDaySelected = { onDaySelected(it) }
                    )
                }
                WCalendarPeriod.OneWeek -> {
                    OneWeekScreen(
                        modifier = Modifier.width(calendarWidth.dp).wrapContentHeight(),
                        week = month,
                        state = state,
                        dayViewSize = dayViewSize,
                        theme = theme,
                        onDaySelected = { onDaySelected(it) }
                    )
                }
                WCalendarPeriod.TwoWeek -> {
                    TwoWeekScreen(
                        modifier = Modifier.width(calendarWidth.dp).wrapContentHeight(),
                        week = month,
                        state = state,
                        dayViewSize = dayViewSize,
                        theme = theme,
                        onDaySelected = { onDaySelected(it) }
                    )
                }
            }
        }
    }
}






