package com.discode.stock.bot.slash

import com.discode.stock.bot.slash.exchange.ExchangesObject.exchanges
import com.discode.stock.bot.slash.exchange.SearchExchangeObject.searchExchange
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object BotSlashAdmin: ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "ping" -> {
                val time = System.currentTimeMillis()
                event.reply("What will the bot say?").setEphemeral(false) // reply or acknowledge
                    .flatMap { event.hook.editOriginalFormat("Pong! : %d ms", System.currentTimeMillis() - time) } // then edit original
                    .queue() // Queue both reply and edit
            }
            "exchanges" -> {
                event.reply("환율 수집 중이에요!").setEphemeral(true)
                    .flatMap { event.channel.sendMessageEmbeds(exchanges()) }
                    .queue()
            }
            "exchange" -> {
                val value = event.options[0].asString
                event.reply("환율 수집 중이에요!").setEphemeral(true)
                    .flatMap { event.channel.sendMessageEmbeds(searchExchange(value)) }
                    .queue()
            }
        }
    }
}