package com.discode.stock.bot.slash

import com.discode.stock.bot.slash.exchange.ExchangesObject.exchanges
import com.discode.stock.bot.slash.exchange.SearchExchangeObject.searchExchange
import com.discode.stock.bot.slash.globalSearch.GlobalSearchObject.globalSearch
import com.discode.stock.bot.slash.news.MainNewsObject.mainNews
import com.discode.stock.bot.slash.topSearch.Country
import com.discode.stock.bot.slash.topSearch.TopSearchObject.topSearch
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
            "exchange" -> {
                if (event.options.isEmpty()) {
                    event.reply("환율 수집 중이에요!").setEphemeral(false)
                        .flatMap { event.hook.editOriginalEmbeds(exchanges()) }
                        .queue()
                    return
                } else {
                    val value = event.options[0].asString
                    event.reply("환율 수집 중이에요!").setEphemeral(false)
                        .flatMap { event.hook.editOriginalEmbeds(searchExchange(value)) }
                        .queue()
                }
            }
            "top-search" -> {
                event.reply("인기 종목 수집중이에요!").setEphemeral(false)
                    .flatMap { event.hook.editOriginal(topSearch()) }
                    .queue()
            }
            "top-global" -> {
                if (event.isCheckOption()) return
                val value = Country.valueOf(event.options[0].asString)
                event.reply("TOP 종목 수집중이에요!").setEphemeral(false)
                    .flatMap { event.hook.editOriginalEmbeds(globalSearch(value)) }
                    .queue()
            }
            "main-news" -> {
                event.reply("주요 뉴스 수집중이에요!").setEphemeral(false)
                    .flatMap { event.hook.editOriginal(mainNews()) }
                    .queue()
            }
        }
    }

    private fun SlashCommandInteractionEvent.isCheckOption(): Boolean {
        return if (this.options.isEmpty()) {
            this.reply(":warning: 옵션을 선택해주세요!").setEphemeral(false).queue()
            true
        } else {
            false
        }
    }
}