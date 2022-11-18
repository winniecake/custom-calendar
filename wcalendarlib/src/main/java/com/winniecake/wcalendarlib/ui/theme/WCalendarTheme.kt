package com.winniecake.wcalendarlib.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

data class WCalendarTheme(
    val bgColor: Color,
    val headerBgColor: Color,
    val headerFontColor: Color,
    val dayOfWeekBgColor: Color,
    val dayOfWeekFontColor: Color,
    val dayOfWeekBorderColor: Color,
    val daySelectedBgColor: Color,
    val daySelectedFontColor: Color,
    val daySelectedBorderColor: Color,
    val dayBgColor: Color,
    val dayFontColor: Color,
    val dayBorderColor: Color,
    val saturdayBgColor: Color,
    val saturdayFontColor: Color,
    val sundayBgColor: Color,
    val sundayFontColor: Color,
    val dayHideBgColor: Color,
    val dayHideFontColor: Color
)

enum class WCalendarThemeName {
    Light,
    Dark,
    EarthTones
}

val SimpleLight: WCalendarTheme
@Composable
@ReadOnlyComposable
get() = WCalendarTheme(
    bgColor = Color(0xFFFFFFFF),
    headerBgColor = Color(0xFFFFFFFF),
    headerFontColor = Color(0xFF000000),
    dayOfWeekBgColor = Color(0xFF999999),
    dayOfWeekFontColor = Color(0xFFFFFFFF),
    dayOfWeekBorderColor = Color(0xFFeeeeee),
    daySelectedBgColor = Color(0xFFFFF2CC),
    daySelectedFontColor = Color(0xFF000000),
    daySelectedBorderColor = Color(0xFF999999),
    dayBgColor = Color(0xFFFFFFFF),
    dayFontColor = Color(0xFF000000),
    dayBorderColor = Color(0xFFF3F6F4),
    saturdayBgColor = Color(0xFFCFE2F3),
    saturdayFontColor = Color(0xFF0B5394),
    sundayBgColor = Color(0xFFF4CCCC),
    sundayFontColor = Color(0xFF990000),
    dayHideBgColor = Color(0xFFF3F6F4),
    dayHideFontColor = Color(0xFFBCBCBC)
)

val SimpleDark: WCalendarTheme
@Composable
@ReadOnlyComposable
get() = WCalendarTheme(
    bgColor = Color(0xFF1F212B),
    headerBgColor = Color(0xFF1F212B),
    headerFontColor = Color(0xFFFFFFFF),
    dayOfWeekBgColor = Color(0xFF353E46),
    dayOfWeekFontColor = Color(0xFFFFFFFF),
    dayOfWeekBorderColor = Color(0xFF5B5B5B),
    daySelectedBgColor = Color(0xFFB45F06),
    daySelectedFontColor = Color(0xFFFFFFFF),
    daySelectedBorderColor = Color(0xFFBCBCBC),
    dayBgColor = Color(0xFF1F212B),
    dayFontColor = Color(0xFFFFFFFF),
    dayBorderColor = Color(0xFF5B5B5B),
    saturdayBgColor = Color(0xFF073763),
    saturdayFontColor = Color(0xFFCFE2F3),
    sundayBgColor = Color(0xFF660000),
    sundayFontColor = Color(0xFFF4CCCC),
    dayHideBgColor = Color(0xFF353E46),
    dayHideFontColor = Color(0xFFBCBCBC)
)

val EarthTones: WCalendarTheme
@Composable
@ReadOnlyComposable
get() = WCalendarTheme(
    bgColor = Color(0xFFFAF4F0),
    headerBgColor = Color(0xFFFAF4F0),
    headerFontColor = Color(0xFF786364),
    dayOfWeekBgColor = Color(0xFF938283),
    dayOfWeekFontColor = Color(0xFFF6E9E2),
    dayOfWeekBorderColor = Color(0xFFF6E9E2),
    daySelectedBgColor = Color(0xFFFFE599),
    daySelectedFontColor = Color(0xFF000000),
    daySelectedBorderColor = Color(0xFF786364),
    dayBgColor = Color(0xFFFAF4F0),
    dayFontColor = Color(0xFF786364),
    dayBorderColor = Color(0xFFDDD6D9),
    saturdayBgColor = Color(0xFF91ACC1),
    saturdayFontColor = Color(0xFFFFFFFF),
    sundayBgColor = Color(0xFFD2AEAD),
    sundayFontColor = Color(0xFFFFFFFF),
    dayHideBgColor = Color(0xFFE2DCDE),
    dayHideFontColor = Color(0xFFA79192)
)