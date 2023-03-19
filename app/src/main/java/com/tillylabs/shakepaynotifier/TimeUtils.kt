package com.tillylabs.shakepaynotifier

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

object TimeUtils {
    private val TIMEZONE = TimeZone.currentSystemDefault()

    fun timeUntilOneAm(): Duration {
        val now = Clock.System.now().toLocalDateTime(TIMEZONE)
        val oneAm = LocalDateTime(now.year, now.month.value, now.dayOfMonth, 1, 0, 0)
        val nextOneAm =
            if (now > oneAm) oneAm.toInstant(TIMEZONE).plus(1, DateTimeUnit.DAY,
                TIMEZONE
            ).toLocalDateTime(TIMEZONE)
            else oneAm

        return nextOneAm.toInstant(TIMEZONE).minus(now.toInstant(TIMEZONE))
    }

    fun formattedTimeUntilOneAm(): String =
        timeUntilOneAm().toIsoString().drop(2).dropLastWhile { it.toString() != "M" }.lowercase()
}
