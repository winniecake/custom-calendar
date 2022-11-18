package com.winniecake.wcalendarlib.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
internal fun TwoWeekScreen(
    modifier: Modifier,
    week: LocalDate,
    state: WCalendarContract.State,
    dayViewSize: Int,
    theme: WCalendarTheme,
    onDaySelected: (LocalDate) -> Unit
) {
    FlowRow(modifier = modifier) {

        // current month
        for (day in 0..13) {
            val date = week.minusDays(if(state.startDayOfWeek == WCalendarStartDayOfWeek.SUNDAY)((-day+1).toLong())else(-day).toLong())
            val dayOfWeek = date.dayOfWeek
            val bgColor: Color
            val fontColor: Color
            val borderColor: Color
            if(date == state.selectedDate) {
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
                        indication = null) {
                        onDaySelected(date)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = date.dayOfMonth.toString(), color = fontColor)
            }
        }
    }
}