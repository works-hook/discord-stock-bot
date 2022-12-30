package com.discode.stock.bot.slash.topSearch

enum class Country {

    USA {
        override fun getIcon() = "미국 :flag_um:"
    },

    CHN {
        override fun getIcon() = "중국 :flag_cn:"
    },

    HKG {
        override fun getIcon() = "홍콩 :flag_hk:"
    },

    JPN {
        override fun getIcon() = "일본 :flag_jp:"
    },

    VNM {
        override fun getIcon() = "베트남 :flag_vn:"
    };

    abstract fun getIcon(): String

}