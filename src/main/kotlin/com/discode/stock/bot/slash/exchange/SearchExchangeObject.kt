package com.discode.stock.bot.slash.exchange

import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object SearchExchangeObject {

    private const val M_NAVER_STOCK_EXCHANGE_DETAIL = "https://m.stock.naver.com/front-api/v1/marketIndex/productDetail?category=exchange&reutersCode"

    private data class SearchExchangeResponse(
        val result: SearchExchangeResult,
    )

    private data class SearchExchangeResult(
        val closePrice: String,
        val fluctuations: String,
        val fluctuationsRatio: String,
        val imageCharts: ImageCharts,
        val calcPrice: String,
        val name: String,
    ) {
        fun toFluctuationsRatioValue(): String {
            val state = if (fluctuationsRatio.toDouble() > 0) ":red_circle:" else ":blue_circle:"
            return "$state $fluctuationsRatio%"
        }
    }

    private data class ImageCharts(
        val areaMonthThree: String,
    )

    private fun getDetailExchanges(code: String): SearchExchangeResponse {
        val url = "${M_NAVER_STOCK_EXCHANGE_DETAIL}=FX_${code}KRW"
        val responseString = URL(url).readText()
        return Gson().fromJson(responseString, SearchExchangeResponse::class.java)
    }

    private fun nowDate(date: LocalDateTime = LocalDateTime.now()) =
        run { date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) }

    fun searchExchange(code: String): MessageEmbed {
        val result = getDetailExchanges(code).result
        return EmbedBuilder()
            .setTitle(":robot_face: 현재 ${result.name} 환율은?")
            .setDescription(nowDate())
            .setColor(Color(14, 119, 142))
            .setImage(result.imageCharts.areaMonthThree)
            .addField("현재가", "${result.closePrice}원", true)
            .addField("변동", result.fluctuations, true)
            .addField("변동률", result.toFluctuationsRatioValue(), true)
            .build()
    }

}




