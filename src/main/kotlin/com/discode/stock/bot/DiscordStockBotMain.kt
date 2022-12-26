package com.discode.stock.bot

import com.discode.stock.bot.slash.BotSlashAdmin
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.build.Commands

fun main() {
    val jda = JDABuilder.createDefault(
        "")
        .setActivity(Activity.playing("종목 수집"))
        .build()

    jda.presence.setStatus(OnlineStatus.ONLINE)
    jda.addEventListener(BotSlashAdmin)

    jda.updateCommands().addCommands(
        Commands.slash("ping", "What will the bot say?"),
        Commands.slash("exchange", "현재 환률은?"),
    ).queue();
}
