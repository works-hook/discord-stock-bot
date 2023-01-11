package com.discode.stock.bot.slash.topSearch

import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder
import net.dv8tion.jda.api.utils.messages.MessageEditData
import java.awt.Color
import java.lang.StringBuilder
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TopSearchObject {

    private const val M_TOP_SEARCH_STOCK = "https://m.stock.naver.com/api/mystock/group/-2"

    private data class TopSearchResponse(
        val stocks: List<Stocks>,
    )

    private data class Stocks (
        val imageCharts: ImageCharts,
        val stockExchangeName: String,
        val fluctuationsRatio: String,
        val compareToPreviousClosePrice: String,
        val closePrice: String,
        val stockName: String,
        val itemCode: String,
    ) {
        fun toFluctuationsRatioValue(): String {
            val state = if (fluctuationsRatio.toDouble() > 0) ":red_circle:" else ":blue_circle:"
            return "$state $fluctuationsRatio%"
        }
    }

    private data class ImageCharts(
        val mini: String,
    )

    private fun getTopSearchStock(): TopSearchResponse {
        val responseString = URL(M_TOP_SEARCH_STOCK).readText()
        return Gson().fromJson(responseString, TopSearchResponse::class.java)
    }
    
    private fun nowDate(date: LocalDateTime = LocalDateTime.now()) =
        run { date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) }

    fun topSearch(): MessageEditData {
        val topSearch = getTopSearchStock()

        val content = StringBuilder()

        content.append("** :rabbit: 현재 인기 종목은? **\n")
        content.append("${nowDate()} \n")

        val embedBuilder = EmbedBuilder()
        val embeds = mutableListOf<MessageEmbed>()

        topSearch.stocks.forEach {
            embedBuilder.setTitle("${it.stockName} (${it.stockExchangeName})\t\t\t\t")
                .setColor(Color(156, 95, 207))

                .addField("현재가", "${it.closePrice}원", true)
                .addBlankField(true)
                .addField("변동률", it.toFluctuationsRatioValue(), true)

                .addField("변동가", it.compareToPreviousClosePrice, true)
                .addBlankField(true)
                .addField("종목 코드", it.itemCode, true)

                .setThumbnail(it.imageCharts.mini)
            embeds.add(embedBuilder.build())
            embedBuilder.clear()
        }

        return MessageEditBuilder()
            .setContent(content.toString())
            .setEmbeds(embeds.subList(0, 5))
            .build()
    }

}



