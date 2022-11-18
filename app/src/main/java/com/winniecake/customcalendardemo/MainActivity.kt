package com.winniecake.customcalendardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.winniecake.customcalendardemo.ui.theme.CustomCalendarDemoTheme
import com.winniecake.wcalendarlib.WCalendar
import com.winniecake.wcalendarlib.ui.theme.WCalendarPeriod
import com.winniecake.wcalendarlib.ui.theme.WCalendarStartDayOfWeek
import com.winniecake.wcalendarlib.ui.theme.WCalendarThemeName
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomCalendarDemoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    val scaffoldState = rememberScaffoldState()
    var calendarTheme by remember { mutableStateOf(WCalendarThemeName.EarthTones) }
    var calendarPeriod by remember { mutableStateOf(WCalendarPeriod.Month)}
    var calendarStartDayOfWeek by remember { mutableStateOf(WCalendarStartDayOfWeek.SUNDAY)}
    var selectedDate by remember { mutableStateOf(LocalDate.now())}

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                scaffoldState,
                calendarPeriod,
                onCalendarPeriodClick = {
                    if (calendarPeriod == WCalendarPeriod.Month) {
                        calendarPeriod = WCalendarPeriod.OneWeek
                    } else if (calendarPeriod == WCalendarPeriod.OneWeek) {
                        calendarPeriod = WCalendarPeriod.TwoWeek
                    } else if (calendarPeriod == WCalendarPeriod.TwoWeek) {
                        calendarPeriod = WCalendarPeriod.Month
                    }
                }
            ) },
        drawerContent = {},
        drawerShape = MaterialTheme.shapes.small,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    WCalendar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        type = calendarPeriod,
                        startDayOfWeek = calendarStartDayOfWeek,
                        theme = calendarTheme,
                        onDaySelected = { date ->
                            selectedDate = date
                        }
                    )

                    DayInfo(selectedDate)

                    SettingsScreen(
                        onThemeChange = {
                            calendarTheme = it
                        },
                        onStartDayOfWeekChange = {
                            calendarStartDayOfWeek = it
                        }
                    )


                }
                GithubInfo()
            }
        }
    }
}

@Composable
fun TopBar(
    scaffoldState: ScaffoldState,
    calendarPeriod: WCalendarPeriod,
    onCalendarPeriodClick: () -> Unit) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(
                text = "",
                color = Color.White
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(
                onClick = {
                    // 開啟選單
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu icon")
            }
        },
        actions = {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                onClick = {
                    onCalendarPeriodClick()
                }
            ) {
                if (calendarPeriod == WCalendarPeriod.Month) {
                    Image(
                        painter = painterResource(id = R.drawable.month_calendar_icon),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        colorFilter =  ColorFilter.tint(color = Color.White)
                    )
                } else if (calendarPeriod == WCalendarPeriod.OneWeek) {
                    Image(
                        painter = painterResource(id = R.drawable.week_calendar_icon),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        colorFilter =  ColorFilter.tint(color = Color.White)
                    )
                } else if (calendarPeriod == WCalendarPeriod.TwoWeek) {
                    Image(
                        painter = painterResource(id = R.drawable.two_week_calendar_icon),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        colorFilter =  ColorFilter.tint(color = Color.White)
                    )
                }
            }
        }
    )
}

@Composable
fun DayInfo(
    selectedDate: LocalDate,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val dayOfWeekName = stringArrayResource(id = com.winniecake.wcalendarlib.R.array.dayOfWeek)
        // title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 10.dp)
                .border(BorderStroke(1.dp, SolidColor(Color(0xFFBCBCBC)))),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(8.dp),
                text = "${selectedDate.year}/${selectedDate.month.value}/${selectedDate.dayOfMonth} " +
                        "${dayOfWeekName[selectedDate.dayOfWeek.value-1]}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SettingsScreen(
    onThemeChange: (theme: WCalendarThemeName) -> Unit,
    onStartDayOfWeekChange: (startDayOfWeek: WCalendarStartDayOfWeek) -> Unit
) {
    val themeOptions = listOf(WCalendarThemeName.EarthTones, WCalendarThemeName.Light, WCalendarThemeName.Dark)
    val startDayOfWeekOptions = listOf(WCalendarStartDayOfWeek.SUNDAY, WCalendarStartDayOfWeek.MONDAY)
    var selectedTheme by remember { mutableStateOf(themeOptions[0])}
    var selectedStartDayOfWeek by remember { mutableStateOf(startDayOfWeekOptions[0])}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // theme setting
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFFF3F6F4)),
            text = "Theme Setting",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            themeOptions.forEach { theme ->
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        selected = (theme == selectedTheme),
                        onClick = {
                            selectedTheme = theme
                            onThemeChange(selectedTheme)
                        }
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        text = theme.name
                    )
                }
            }
        }

        // startDayOfWeek setting
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFFF3F6F4)),
            text = "Start Day Of Week Setting",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            startDayOfWeekOptions.forEach { startDayOfWeek ->
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        selected = (startDayOfWeek == selectedStartDayOfWeek),
                        onClick = {
                            selectedStartDayOfWeek = startDayOfWeek
                            onStartDayOfWeekChange(selectedStartDayOfWeek)
                        }
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        text = startDayOfWeek.name
                    )
                }
            }
        }
    }
}

@Composable
fun GithubInfo() {
    Divider(
        color = Color.LightGray,
        modifier = Modifier
            .padding(2.dp)
    )

    Text(
        text ="A Calendar Library Demo by Elaine",
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        color = Color.Gray)



    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val githubLinkString = buildAnnotatedString {
            val str = "download source code from github"
            val startIndex = str.indexOf("source")
            val endIndex = startIndex + 11

            append(str)
            addStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                ),
                start = startIndex,
                end = endIndex
            )

            addStringAnnotation(
                tag = "URL",
                annotation = "https://github.com/winniecake/custom-calendar",
                start = startIndex,
                end = endIndex
            )
        }

        val uriHandler = LocalUriHandler.current

        ClickableText(
            text = githubLinkString,
            style = TextStyle(textAlign = TextAlign.Center),
            onClick = {
                githubLinkString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CustomCalendarDemoTheme {
        WCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            type = WCalendarPeriod.Month,
            startDayOfWeek = WCalendarStartDayOfWeek.SUNDAY,
            theme = WCalendarThemeName.EarthTones,
            onDaySelected = {

            })
    }
}