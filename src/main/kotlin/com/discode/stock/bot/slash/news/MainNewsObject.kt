package com.discode.stock.bot.slash.news

import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder
import net.dv8tion.jda.api.utils.messages.MessageEditData
import java.awt.Color
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object MainNewsObject {

    private const val M_NAVER_STOCK_MAIN_NEWS = "https://m.stock.naver.com/api/news/list?category=mainnews&page=1&pageSize=5"
    private const val NAVER_STOCK_DETAIL_NEWS = "https://finance.naver.com/news/news_read.naver?mode=mainnews&office_id="

    private data class MainNewsResponse(
        val dt: String,
        val tit: String,
        val aid: String,
        val oid: String,
        val ohnm: String ,
        val thumbUrl: String?,
        val subcontent: String,
    ) {
        fun getDate(): String = getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        private fun getLocalDateTime() = LocalDateTime.of(getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond())

        private fun getYear() = dt.subSequence(0, 4).toString().toInt()
        private fun getMonth() = dt.subSequence(4, 6).toString().toInt()
        private fun getDay() = dt.subSequence(6, 8).toString().toInt()
        private fun getHour() = dt.subSequence(8, 10).toString().toInt()
        private fun getMinute() = dt.subSequence(10, 12).toString().toInt()
        private fun getSecond() = dt.subSequence(12, 14).toString().toInt()

    }

    private fun getMainNews(): List<MainNewsResponse> {
        val responseString = URL(M_NAVER_STOCK_MAIN_NEWS).readText()
        return Gson().fromJson(responseString, Array<MainNewsResponse>::class.java).asList()
    }

    fun mainNews(): MessageEditData {
        val mainNews = getMainNews()

        val content = StringBuilder()
            .append("** :newspaper: 주요 뉴스 **\n")

        val builder = EmbedBuilder()
        val embeds = mutableListOf<MessageEmbed>()

        mainNews.forEach {
            builder.setTitle(it.tit)
                .setColor(Color(66, 135, 245))
                .setThumbnail(it.thumbUrl)
                .setDescription("$NAVER_STOCK_DETAIL_NEWS${it.oid}&article_id=${it.aid}")
                .addField("${it.ohnm}${it.getDate()}", it.subcontent, false)
            embeds.add(builder.build())
            builder.clear()
        }

        return MessageEditBuilder()
            .setContent(content.toString())
            .setEmbeds(embeds)
            .build()
    }


}