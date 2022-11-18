package com.winniecake.wcalendarlib.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.winniecake.wcalendarlib.ui.theme.WCalendarTheme
import com.winniecake.wcalendarlib.ui.viewmodel.WCalendarContract
import com.winniecake.wcalendarlib.ui.theme.WCalendarStartDayOfWeek
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
internal fun MonthScreen(
    modifier: Modifier,
    month: LocalDate,
    state: WCalendarContract.State,
    dayViewSize: Int,
    theme: WCalendarTheme,
    onDaySelected: (LocalDate) -> Unit
) {
    FlowRow(modifier = modifier) {

        // previous month
        val previousMonth = month.minusMonths(1)
        val beginningSpaces = if(state.startDayOfWeek == WCalendarStartDayOfWeek.SUNDAY)(month.dayOfWeek.value % 7)else(month.dayOfWeek.value - 1)
        val end: Int = previousMonth.month.length(previousMonth.isLeapYear)
        val start: Int = end - beginningSpaces + 1
        for (day in start..end) {
            Box(
                modifier = Modifier
                    .width(dayViewSize.dp)
                    .height(dayViewSize.dp)
                    .background(theme.dayHideBgColor)
                    .border(BorderStroke(1.dp, SolidColor(theme.dayBorderColor))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.toString(),
                    color = theme.dayHideFontColor
                )
            }
        }

        // current month
        val daysByMonth = month.month.length(month.isLeapYear)
        for (day in 1..daysByMonth) {
            val dayOfWeek = month.withDayOfMonth(day).dayOfWeek
            val bgColor: Color
            val fontColor: Color
            val borderColor: Color
            if(month.withDayOfMonth(day) == state.selectedDate) {
                bgColor = theme.daySelectedBgColor
                fontColor = theme.daySelectedFontColor
                borderColor = theme.daySelectedBorderColor
            } else if (dayOfWeek == DayOfWeek.SATURDAY) {
                bgColor = theme.saturdayBgColor
                fontColor = theme.saturdayFontColor
                borderColor = theme.dayBorderColor
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                bgColor = theme.sundayBgColor
                fontColor = theme.sundayFontColor
                borderColor = theme.dayBorderColor
            } else {
                bgColor = theme.dayBgColor
                fontColor = theme.dayFontColor
                borderColor = theme.dayBorderColor
            }

            Box(
                modifier = Modifier
                    .width(dayViewSize.dp)
                    .height(dayViewSize.dp)
                    .background(bgColor)
                    .border(BorderStroke(1.dp, SolidColor(borderColor)))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onDaySelected(month.withDayOfMonth(day))
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = day.toString(), color = fontColor)
            }
        }

        // next month
        val endSpaces = 42 - daysByMonth - beginningSpaces
        for (day in 1..endSpaces) {
            Box(
                modifier = Modifier
                    .width(dayViewSize.dp)
                    .height(dayViewSize.dp)
                    .background(theme.dayHideBgColor)
                    .border(BorderStroke(1.dp, SolidColor(theme.dayBorderColor))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.toString(),
                    color = theme.dayHideFontColor
                )
            }
        }
    }
}


