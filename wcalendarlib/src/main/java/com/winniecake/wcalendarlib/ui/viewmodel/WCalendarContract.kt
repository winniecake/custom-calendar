package com.winniecake.wcalendarlib.ui.viewmodel

import com.winniecake.wcalendarlib.ui.theme.WCalendarPeriod
import com.winniecake.wcalendarlib.ui.theme.WCalendarStartDayOfWeek
import java.time.LocalDate

class WCalendarContract {
    data class State(
        val period: WCalendarPeriod,
        val startDayOfWeek: WCalendarStartDayOfWeek,
        val selectedDate: LocalDate,
        val currentPageStartDate:LocalDate,
        val nextPageStartDate:LocalDate,
        val previousPageStartDate:LocalDate
    )
}

