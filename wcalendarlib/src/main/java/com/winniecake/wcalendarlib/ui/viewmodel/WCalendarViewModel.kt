package com.winniecake.wcalendarlib.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.winniecake.wcalendarlib.ui.theme.WCalendarPeriod
import com.winniecake.wcalendarlib.ui.theme.WCalendarStartDayOfWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WCalendarViewModel@Inject constructor() : ViewModel() {

    var state by mutableStateOf(
        WCalendarContract.State(
            period = WCalendarPeriod.Month,
            startDayOfWeek = WCalendarStartDayOfWeek.SUNDAY,
            selectedDate = LocalDate.now(),
            currentPageStartDate = LocalDate.now().withDayOfMonth(1),
            nextPageStartDate = getNextMonth(LocalDate.now(), 1),
            previousPageStartDate = getPreviousMonth(LocalDate.now(), 1),
        )
    )

    fun setPeriodMode(period: WCalendarPeriod) {
        if (period == state.period) {
            return
        }

        val currentPageStartDate: LocalDate
        val nextPageStartDate: LocalDate
        val previousPageStartDate: LocalDate

        when (period) {
            WCalendarPeriod.OneWeek -> {
                currentPageStartDate = getCurrentWeekStartDay(state.currentPageStartDate)
                nextPageStartDate = getNextWeekStartDay(state.currentPageStartDate)
                previousPageStartDate = getPreviousWeekStartDay(state.currentPageStartDate)

            }
            WCalendarPeriod.TwoWeek -> {
                currentPageStartDate = getCurrentWeekStartDay(state.currentPageStartDate)
                nextPageStartDate = getNextTwoWeekStartDay(state.currentPageStartDate)
                previousPageStartDate = getPreviousTwoWeekStartDay(state.currentPageStartDate)
            }
            WCalendarPeriod.Month -> {
                if (state.startDayOfWeek == WCalendarStartDayOfWeek.SUNDAY) {
                    currentPageStartDate =
                        state.currentPageStartDate.minusDays(-5).withDayOfMonth(1)
                    nextPageStartDate = getNextMonth(currentPageStartDate, 1)
                    previousPageStartDate = getPreviousMonth(currentPageStartDate, 1)
                } else {
                    currentPageStartDate =
                        state.currentPageStartDate.minusDays(-6).withDayOfMonth(1)
                    nextPageStartDate = getNextMonth(currentPageStartDate, 1)
                    previousPageStartDate = getPreviousMonth(currentPageStartDate, 1)
                }
            }
        }
        state = state.copy(
            period = period,
            currentPageStartDate = currentPageStartDate,
            nextPageStartDate = nextPageStartDate,
            previousPageStartDate = previousPageStartDate
        )
    }

    fun setStartOfWeek(startDayOfWeek: WCalendarStartDayOfWeek) {
        state = state.copy(startDayOfWeek = startDayOfWeek)
    }

    fun onDaySelected(date: LocalDate) {
        state = state.copy(selectedDate = date)
    }

    fun onNextPeriod() {
        when (state.period) {
            WCalendarPeriod.Month -> {
                val currentMonth = getNextMonth(state.currentPageStartDate, 1)
                val nextMonth = getNextMonth(currentMonth, 1)
                state = state.copy(
                    currentPageStartDate = currentMonth,
                    nextPageStartDate = nextMonth,
                    previousPageStartDate = state.currentPageStartDate,
                )
            }
            WCalendarPeriod.OneWeek -> {
                val currentWeek = getNextWeekStartDay(state.currentPageStartDate)
                val nextWeek = getNextWeekStartDay(currentWeek)
                state = state.copy(
                    currentPageStartDate = currentWeek,
                    nextPageStartDate = nextWeek,
                    previousPageStartDate = state.currentPageStartDate,
                )
            }
            WCalendarPeriod.TwoWeek -> {
                val currentWeek = getNextTwoWeekStartDay(state.currentPageStartDate)
                val nextWeek = getNextTwoWeekStartDay(currentWeek)
                state = state.copy(
                    currentPageStartDate = currentWeek,
                    nextPageStartDate = nextWeek,
                    previousPageStartDate = state.currentPageStartDate,
                )
            }
        }
    }

    fun onPreviousPeriod() {
        when (state.period) {
            WCalendarPeriod.Month -> {
                val currentMonth = getPreviousMonth(state.currentPageStartDate, 1)
                val previousMonth = getPreviousMonth(currentMonth, 1)

                state = state.copy(
                    currentPageStartDate = currentMonth,
                    nextPageStartDate = state.currentPageStartDate,
                    previousPageStartDate = previousMonth,
                )
            }
            WCalendarPeriod.OneWeek -> {
                val currentWeek = getPreviousWeekStartDay(state.currentPageStartDate)
                val previousWeek = getPreviousWeekStartDay(currentWeek)
                state = state.copy(
                    currentPageStartDate = currentWeek,
                    nextPageStartDate = state.currentPageStartDate,
                    previousPageStartDate = previousWeek,
                )
            }
            WCalendarPeriod.TwoWeek -> {
                val currentWeek = getPreviousTwoWeekStartDay(state.currentPageStartDate)
                val previousWeek = getPreviousTwoWeekStartDay(currentWeek)
                state = state.copy(
                    currentPageStartDate = currentWeek,
                    nextPageStartDate = state.currentPageStartDate,
                    previousPageStartDate = previousWeek,
                )
            }
        }
    }

    private fun getNextMonth(date: LocalDate, dayOfMonth: Int): LocalDate {
        val nextMonth = LocalDate.of(date.year, date.month, dayOfMonth)
        return nextMonth.minusMonths(-1)
    }

    private fun getPreviousMonth(date: LocalDate, dayOfMonth: Int): LocalDate {
        val nextMonth = LocalDate.of(date.year, date.month, dayOfMonth)
        return nextMonth.minusMonths(1)
    }

    private fun getCurrentWeekStartDay(date: LocalDate): LocalDate {
        val offset: Int = date.dayOfWeek.value - 1
        return date.minusDays(offset.toLong())
    }

    private fun getNextWeekStartDay(date: LocalDate): LocalDate {
        val offset: Int = date.dayOfWeek.value -1 - 7
        return date.minusDays(offset.toLong())
    }

    private fun getPreviousWeekStartDay(date: LocalDate): LocalDate {
        val offset: Int = date.dayOfWeek.value -1 + 7
        return date.minusDays(offset.toLong())
    }

    private fun getNextTwoWeekStartDay(date: LocalDate): LocalDate {
        val offset: Int = date.dayOfWeek.value -1 - 14
        return date.minusDays(offset.toLong())
    }

    private fun getPreviousTwoWeekStartDay(date: LocalDate): LocalDate {
        val offset: Int = date.dayOfWeek.value -1 + 14
        return date.minusDays(offset.toLong())
    }
}