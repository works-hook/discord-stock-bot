package com.discode.stock.bot.slash

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object BotSlashAdmin: ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val exchange = ExchangeObject()

        when (event.name) {
            "ping" -> {
                val time = System.currentTimeMillis()
                event.reply("What will the bot say?").setEphemeral(true) // reply or acknowledge
                    .flatMap { event.hook.editOriginalFormat("Pong! : %d ms", System.currentTimeMillis() - time) } // then edit original
                    .queue() // Queue both reply and edit
            }
            "exchange" -> {
                event.reply("환율 수집 중이에요!").setEphemeral(true)
                    .flatMap { event.channel.sendMessageEmbeds(exchange()) }
                    .queue()
            }
        }
    }
}