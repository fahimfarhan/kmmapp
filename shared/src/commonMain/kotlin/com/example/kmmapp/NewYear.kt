package com.example.kmmapp

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn


fun daysUntilNewYear(): Int {
    val today = Clock.System.todayIn(kotlinx.datetime.TimeZone.currentSystemDefault())
    val closestNewYear = LocalDate(today.year + 1, 1, 1)
    return today.daysUntil(closestNewYear)
}