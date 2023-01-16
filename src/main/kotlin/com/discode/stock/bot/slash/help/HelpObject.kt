package com.discode.stock.bot.slash.help

import com.discode.stock.bot.slash.common.nowDate
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

object HelpObject {

    fun help(): MessageEmbed {
        return EmbedBuilder()
            .setTitle(":bulb: 도움말")
            .setDescription(nowDate())
            .setColor(Color(77, 120, 78))
            .addField("ping", "heath check를 하는 명령어입니다.", false)
            .addField("help", "커멘드 옵션 및 가이드를 조회하는 명령어입니다.", false)
            .addField("exchange", "나라를 선택해 해당 나라의 환률을 조회하는 명령어입니다. 옵션을 선택하지 않으면 기본 환률 정보들을 조회합니다. \n options : 미국 USD, 일본 JPY, 유럽연합 EUR, 중국 CNY", false)
            .addField("top-search", "현재 인기 종목을 조회하는 명령어입니다.", false)
            .addField("top-global", "나라를 선택해 해당 나라의 top 종목을 조회하는 명령어 입니다. \n options : 미국, 중국, 홍콩, 일본, 베트남", false)
            .addField("main-news", "주식 관련 주요 뉴스를 조회하는 명령어입니다.", false)
            .build()
    }

}