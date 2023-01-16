package com.discode.stock.bot.slash.globalSearch

import com.discode.stock.bot.slash.common.Country
import com.discode.stock.bot.slash.common.nowDate
import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.net.URL

object GlobalSearchObject {

    private const val M_NAVER_STOCK_GLOBAL_BEFORE = "https://api.stock.naver.com/stock/nation/"
    private const val M_NAVER_STOCK_GLOBAL_AFTER = "/priceTop?page=1&pageSize=10"

    private data class GlobalResponse(
        val stocks: List<Stocks>,
    )

    private data class Stocks (
        val fluctuationsRatio: String,
        val compareToPreviousClosePrice: String,
        val closePrice: String,
        val stockName: String,
    ) {
        fun toFluctuationsRatioValue(): String {
            val state = if (fluctuationsRatio.toDouble() > 0) ":red_circle:" else ":blue_circle:"
            return "$state $fluctuationsRatio%"
        }
    }

    private fun getGlobalSearch(code: String): GlobalResponse {
        val url = "$M_NAVER_STOCK_GLOBAL_BEFORE$code$M_NAVER_STOCK_GLOBAL_AFTER"
        val responseString = URL(url).readText()
        return Gson().fromJson(responseString, GlobalResponse::class.java)
    }

    fun globalSearch(country: Country): MessageEmbed {
        val result = getGlobalSearch(country.toString())
        val builder = EmbedBuilder()
            .setTitle("${country.getIcon()} 의 TOP 종목은? / 거래대금(현지통화) 기준")
            .setDescription(nowDate())
            .setColor(Color(240, 226, 98))
        result.stocks.subList(0, 7).forEach {
            builder.addField("종목", "**${it.stockName}**", true)
                .addField("종가", it.closePrice, true)
                .addField("변동률", it.toFluctuationsRatioValue(), true)
        }
        return builder.build()
    }

}