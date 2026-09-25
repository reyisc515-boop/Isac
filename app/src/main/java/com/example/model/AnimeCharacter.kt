package com.example.model

enum class CharacterRarity(val labelAr: String, val badgeColorHex: Long) {
    SSS("خارق للعادة SSS", 0xFFFF2A6D),
    SS("أسطوري SS", 0xFFFFD700),
    S("ملحمي S", 0xFF9D4EDD),
    A("نادر A", 0xFF00E5FF),
    TROLL("طريف / حظ", 0xFF78909C)
}

enum class AnimeCategory(val titleAr: String, val subtitleAr: String) {
    ALL("كل الشخصيات", "جميع الأنميات بدون قيود"),
    SHONEN("عالم الشونين", "ون بيس، ناروتو، بليتش، دراغون بول"),
    DARK("عالم الظلام والغموض", "ديث نوت، هجوم العمالقة، بيرسيرك"),
    CLASSIC("أساطير التسعينات", "هنتر، دراغون بول، سلام دانك"),
    MAGIC("قوى السحر والظلال", "سولو ليفلينغ، جوجيتسو، ديمون سلاير")
}

data class CharacterStats(
    val attack: Int,
    val speed: Int,
    val iq: Int,
    val special: Int
)

data class AnimeCharacter(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val animeTitleAr: String,
    val category: AnimeCategory,
    val roleAr: String,
    val powerRating: Int, // Overall Rating (60 - 99)
    val rarity: CharacterRarity,
    val basePriceM: Int, // in Millions (e.g. 15M)
    val signatureSkillAr: String,
    val clanOrAffiliation: String,
    val imageUrl: String = "",
    val stats: CharacterStats = CharacterStats(95, 90, 88, 97),
    val primaryColorHex: Long = 0xFFD4AF37,
    val secondaryColorHex: Long = 0xFF1A1A2E,
    val quoteAr: String = ""
)
