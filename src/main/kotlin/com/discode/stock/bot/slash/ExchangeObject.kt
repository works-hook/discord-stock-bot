package com.discode.stock.bot.slash

import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExchangeObject {

    companion object {
        private const val M_NAVER_STOCK_MAIN = "https://m.stock.naver.com/api/home/majors"

        private data class ExchangeResponse(
            val marketIndexInfos: List<MarketIndexInfos>
        )

        private data class MarketIndexInfos(
            val categoryType: String,
            val reutersCode: String,
            val name: String,
            val closePrice: String,
            val compareToPreviousClosePrice: String,
            val compareToPreviousPrice: CompareToPreviousPrice,
            val fluctuationsRatio: String,
            val localTradedAt: String
        ) {
            fun toExchangeValue(): String {
                val state = if (fluctuationsRatio.toDouble() > 0) ":red_circle:" else ":blue_circle:"
                return "${closePrice}원 $state $compareToPreviousClosePrice%"
            }
        }

        private data class CompareToPreviousPrice(
            val code: String,
            val text: String,
            val name: String
        )
    }

    private fun getData(): ExchangeResponse {
        val responseString = URL(M_NAVER_STOCK_MAIN).readText()
        return Gson().fromJson(responseString, ExchangeResponse::class.java)
    }

    private fun nowDate(date: LocalDateTime = LocalDateTime.now()) = run { date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) }

    operator fun invoke(): MessageEmbed {
        val exchange = getData()
        val builder = EmbedBuilder()
            .setTitle(":robot_face: 현재 환율은?")
            .setDescription(nowDate())
            .setColor(Color(6, 85, 53))
        exchange.marketIndexInfos.forEach {
            builder.addField(it.name, it.toExchangeValue(), false)
        }
        return builder.build()
    }

}
