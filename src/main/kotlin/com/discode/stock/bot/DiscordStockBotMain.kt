package com.discode.stock.bot

import com.discode.stock.bot.slash.BotSlashAdmin
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData

fun main() {
    val jda = JDABuilder.createDefault(
        "")
        .setActivity(Activity.playing("종목 수집"))
        .build()

    jda.presence.setStatus(OnlineStatus.ONLINE)
    jda.addEventListener(BotSlashAdmin)

    jda.updateCommands().addCommands(
        Commands.slash("ping", "What will the bot say?"),
        Commands.slash("help", "도움말"),
        Commands.slash("exchange", "환률을 검색하세요!")
            .addOptions(
                OptionData(OptionType.STRING, "search", "조회할 환률을 선택해주세요.")
                    .addChoice("미국 USD", "USD")
                    .addChoice("일본 JPY", "JPY")
                    .addChoice("유럽연합 EUR", "EUR")
                    .addChoice("중국 CNY", "CNY")
            ),
        Commands.slash("top-search", "현재 인기 종목은?"),
        Commands.slash("top-global", "글로벌 TOP 종목은?")
            .addOptions(
                OptionData(OptionType.STRING, "search", "나라를 선택해주세요!")
                    .addChoice("미국", "USA")
                    .addChoice("중국", "CHN")
                    .addChoice("홍콩", "HKG")
                    .addChoice("일본", "JPN")
                    .addChoice("베트남", "VNM")
            ),
        Commands.slash("main-news", "주요 뉴스")
    ).queue();
}
