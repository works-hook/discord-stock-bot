package com.discode.stock.bot.slash.exchange

import com.discode.stock.bot.slash.common.nowDate
import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.net.URL

object ExchangesObject {

    private const val M_NAVER_STOCK_EXCHANGE_MAIN = "https://m.stock.naver.com/api/home/majors"

    private data class ExchangeResponse(
        val marketIndexInfos: List<MarketIndexInfos>
    )

    private data class MarketIndexInfos(
        val name: String,
        val closePrice: String,
        val compareToPreviousClosePrice: String,
        val fluctuationsRatio: String,
    ) {
        fun toExchangeValue(): String {
            val state = if (fluctuationsRatio.toDouble() > 0) ":red_circle:" else ":blue_circle:"
            return "$state $compareToPreviousClosePrice%"
        }
    }

    private fun getMainExchanges(): ExchangeResponse {
        val responseString = URL(M_NAVER_STOCK_EXCHANGE_MAIN).readText()
        return Gson().fromJson(responseString, ExchangeResponse::class.java)
    }

    fun exchanges(): MessageEmbed {
        val exchange = getMainExchanges()
        val builder = EmbedBuilder()
            .setTitle(":robot_face: 현재 환율은?")
            .setDescription(nowDate())
            .setColor(Color(6, 85, 53))
        exchange.marketIndexInfos.forEach {
            builder.addField(it.name, it.closePrice, true)
                .addField("\u200b", "\u200b", true)
                .addField("\u200b", it.toExchangeValue(), true)
        }
        return builder.build()
    }

}
