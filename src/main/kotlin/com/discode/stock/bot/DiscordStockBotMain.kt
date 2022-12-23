package com.discode.stock.bot

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag

fun main() {
    val jda = JDABuilder.createDefault(
        "")
        // Disable parts of the cache
        .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
        // Enable the bulk delete event
        .setBulkDeleteSplittingEnabled(false)
        // Set activity (like "playing Something")
        .setActivity(Activity.playing("종목 수집"))
        // enables explicit access to message.getContentDisplay()
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build()

    jda.presence.setStatus(OnlineStatus.ONLINE)
}
