package com.discode.stock.bot.slash.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun nowDate(date: LocalDateTime = LocalDateTime.now()): String = run { date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) }