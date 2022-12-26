package com.discode.stock.bot

import com.discode.stock.bot.slash.BotSlashAdmin
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData

fun main() {
    val jda = JDABuilder.createDefault(
        "")
        .setActivity(Activity.playing("종목 수집"))
        .build()

    jda.presence.setStatus(OnlineStatus.ONLINE)
    jda.addEventListener(BotSlashAdmin)

    // Sets the global command list to the provided commands (removing all others)
    jda.updateCommands().addCommands(
            Commands.slash("ping", "Calculate ping of the bot"),
        ).queue();
}
