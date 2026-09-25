package com.example.model

enum class GameMode(
    val titleAr: String,
    val subtitleAr: String,
    val isLocked: Boolean = false,
    val lockReason: String = ""
) {
    HIDDEN_CHARACTER(
        titleAr = "الشخصية الخفية",
        subtitleAr = "تاخد الشخصية اللي قدامك.. ولا تخاطر بالشخصية الخفية؟"
    ),
    AUCTION(
        titleAr = "المزاد",
        subtitleAr = "مزايدات بيري حماسية، ميزانية محسوبة، واقتناص أساطير الأنمي!"
    ),
    GUILD_LINEUP(
        titleAr = "اقف في صفي عشان بصفي",
        subtitleAr = "تشكيلة التناغم الأسطوري: ارفع نقاطك بتناغم العشائر والأنميات!"
    ),
    ANIME_BLUFF(
        titleAr = "أنت هتحور؟",
        subtitleAr = "حقائق وتزييفات في حبكات الأنمي.. كشف الكذب واختبار الأوتاكو!"
    ),
    EXACT_BUDGET(
        titleAr = "يا دوبك",
        subtitleAr = "شكّل فريقك الخارق دون تجاوز الميزانية المحددة بالمليم!",
        isLocked = false
    ),
    CARD_PACKS(
        titleAr = "باكات الأنمي",
        subtitleAr = "افتح باكات أسطورية واجمع بطاقات نادرة في ألبومك الخاص!"
    )
}

enum class AuctionType(val titleAr: String, val slots: Int, val initialBudgetM: Int) {
    CLASSIC("كلاسيك (5 شخصيات)", 5, 100),
    PRO_MAX("برو ماكس (8 شخصيات)", 8, 160),
    BILLION_AUCTION("مزاد المليار بيري", 10, 1000)
}

enum class CardRuleType(val titleAr: String, val descAr: String) {
    STANDARD("عادي", "مزايدة كلاسيكية مع عداد بيري"),
    LAST_BID("لاست بيد", "المزايدة في الثواني الأخيرة تمدد الوقت 5 ثوان"),
    WILD_CARD("وايلد كارد", "كروت حظ وقدرات سرية خلال المزاد")
}

data class AnimeTriviaQuestion(
    val id: Int,
    val animeTitle: String,
    val statementAr: String,
    val isTrue: Boolean,
    val explanationAr: String
)

object AnimeTriviaDatabase {
    val questions: List<AnimeTriviaQuestion> = listOf(
        AnimeTriviaQuestion(
            id = 1,
            animeTitle = "ون بيس",
            statementAr = "مكافأة باغي المهرج بعد أن أصبح يونكو تجاوزت 3 مليار بيري.",
            isTrue = true,
            explanationAr = "صحيح! مكافأة باغي بلغت 3.189 مليار بيري بعد تأسيس الكروس غيلد."
        ),
        AnimeTriviaQuestion(
            id = 2,
            animeTitle = "هجوم العمالقة",
            statementAr = "ليفاي أكرمان تحول إلى عملاق في الموسم الأخير من الأنمي.",
            isTrue = false,
            explanationAr = "خطأ! أفراد عشيرة الأكرمان محصنون ولا يتحولون لعمالقة إطلاقاً."
        ),
        AnimeTriviaQuestion(
            id = 3,
            animeTitle = "ناروتو شيبودن",
            statementAr = "إيتاتشي أوتشيها أهدى ساسكي عين الشارينغان وهو مبتسم قبل موته.",
            isTrue = true,
            explanationAr = "صحيح! لمس جبهته وقال كلمته الشهيرة: 'سامحني ساسكي، هذه هي المرة الأخيرة'."
        ),
        AnimeTriviaQuestion(
            id = 4,
            animeTitle = "جوجيتسو كايسن",
            statementAr = "غوجو ساتورو يستخدم طاقة ملعونة لا نهائية من دون أي استنزاف لعينيه الست.",
            isTrue = true,
            explanationAr = "صحيح! العيون الست تجعل استهلاك الطاقة الملعونة لديه يقارب الصفر المطلق."
        ),
        AnimeTriviaQuestion(
            id = 5,
            animeTitle = "ديث نوت",
            statementAr = "لايت ياغامي نجح في كتابة اسم إل الحقيقي بمفرده في الحلقة 25.",
            isTrue = false,
            explanationAr = "خطأ! الشينيغامي ريم هي من كتبت اسم إل الحقيقي (إل لولايت) للتضحية بحياتها من أجل ميسا."
        ),
        AnimeTriviaQuestion(
            id = 6,
            animeTitle = "هنتر x هنتر",
            statementAr = "ميريوم ملك النمل مات متأثراً بضربة نيتيرو المباشرة وليس بالسم.",
            isTrue = false,
            explanationAr = "خطأ! نجا ميريوم من الانفجار لكنه مات بالسم القاتل المنبعث من قنبلة الوردة الصغيرة."
        )
    )
}
