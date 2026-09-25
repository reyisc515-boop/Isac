package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AnimeCategory
import com.example.model.AnimeCharacter
import com.example.model.AnimeDatabase
import com.example.model.AnimeTriviaDatabase
import com.example.model.AnimeTriviaQuestion
import com.example.model.AuctionType
import com.example.model.CardRuleType
import com.example.model.CharacterRarity
import com.example.model.GameMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
    HOME,
    AUCTION_SETUP,
    LIVE_AUCTION,
    HIDDEN_CHARACTER_SETUP,
    HIDDEN_CHARACTER_PLAY,
    ANIME_BLUFF,
    CARD_PACKS,
    AUCTION_RESULTS,
    HIDDEN_RESULTS
}

data class AuctionGameState(
    val currentRound: Int = 1,
    val totalRounds: Int = 5,
    val timeRemainingSeconds: Int = 30,
    val currentCharacter: AnimeCharacter? = null,
    val currentHighestBidM: Int = 0,
    val currentHighestBidder: String? = null, // "USER" or "AI" / "P2"
    val userProposedBidM: Int = 1,
    val userBudgetM: Int = 100,
    val opponentBudgetM: Int = 100,
    val userName: String = "ISC_REY (أنت)",
    val opponentName: String = "فريق الـ AI",
    val userSquad: List<AnimeCharacter> = emptyList(),
    val opponentSquad: List<AnimeCharacter> = emptyList(),
    val userPassed: Boolean = false,
    val opponentPassed: Boolean = false,
    val bannerStatusText: String = "الدور الافتتاحي: زايد أو مزايدة تلقائية 1M عند انتهاء المؤقت.",
    val isRoundFinished: Boolean = false,
    val isGameFinished: Boolean = false,
    val lastOpponentBidAmountM: Int = 0,
    val opponentBidAlertTrigger: Long = 0L
)

data class HiddenCharacterRoundState(
    val currentRound: Int = 1,
    val totalRounds: Int = 5,
    val revealedCharacter: AnimeCharacter? = null,
    val mysteryCharacter: AnimeCharacter? = null,
    val isMysteryRevealed: Boolean = false,
    val userPick: AnimeCharacter? = null,
    val opponentPick: AnimeCharacter? = null,
    val userChosenHidden: Boolean = false,
    val userSquad: List<AnimeCharacter> = emptyList(),
    val opponentSquad: List<AnimeCharacter> = emptyList(),
    val isRoundFinished: Boolean = false,
    val isGameFinished: Boolean = false
)

data class PackOpeningState(
    val isOpening: Boolean = false,
    val openedCards: List<AnimeCharacter> = emptyList(),
    val collectedCards: List<AnimeCharacter> = emptyList(),
    val packCount: Int = 3
)

data class BluffGameState(
    val currentQuestionIndex: Int = 0,
    val currentQuestion: AnimeTriviaQuestion = AnimeTriviaDatabase.questions[0],
    val userScore: Int = 0,
    val opponentScore: Int = 0,
    val hasAnswered: Boolean = false,
    val userAnswerWasCorrect: Boolean = false,
    val isGameOver: Boolean = false
)

class AnimeGameViewModel(application: Application) : AndroidViewModel(application) {

    private val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    // Navigation & Screen
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Mode Configuration
    private val _selectedMode = MutableStateFlow(GameMode.AUCTION)
    val selectedMode: StateFlow<GameMode> = _selectedMode.asStateFlow()

    private val _auctionType = MutableStateFlow(AuctionType.CLASSIC)
    val auctionType: StateFlow<AuctionType> = _auctionType.asStateFlow()

    private val _cardRule = MutableStateFlow(CardRuleType.STANDARD)
    val cardRule: StateFlow<CardRuleType> = _cardRule.asStateFlow()

    private val _selectedCategory = MutableStateFlow(AnimeCategory.ALL)
    val selectedCategory: StateFlow<AnimeCategory> = _selectedCategory.asStateFlow()

    private val _isVersusAi = MutableStateFlow(true)
    val isVersusAi: StateFlow<Boolean> = _isVersusAi.asStateFlow()

    // Game States
    private val _auctionState = MutableStateFlow(AuctionGameState())
    val auctionState: StateFlow<AuctionGameState> = _auctionState.asStateFlow()

    private val _hiddenState = MutableStateFlow(HiddenCharacterRoundState())
    val hiddenState: StateFlow<HiddenCharacterRoundState> = _hiddenState.asStateFlow()

    private val _packState = MutableStateFlow(PackOpeningState())
    val packState: StateFlow<PackOpeningState> = _packState.asStateFlow()

    private val _bluffState = MutableStateFlow(BluffGameState())
    val bluffState: StateFlow<BluffGameState> = _bluffState.asStateFlow()

    private var timerJob: Job? = null
    private var availableCharactersPool: MutableList<AnimeCharacter> = mutableListOf()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun selectGameMode(mode: GameMode) {
        _selectedMode.value = mode
        when (mode) {
            GameMode.AUCTION -> navigateTo(AppScreen.AUCTION_SETUP)
            GameMode.HIDDEN_CHARACTER -> navigateTo(AppScreen.HIDDEN_CHARACTER_SETUP)
            GameMode.ANIME_BLUFF -> startBluffGame()
            GameMode.CARD_PACKS -> navigateTo(AppScreen.CARD_PACKS)
            GameMode.GUILD_LINEUP -> navigateTo(AppScreen.AUCTION_SETUP)
            GameMode.EXACT_BUDGET -> navigateTo(AppScreen.AUCTION_SETUP)
        }
    }

    fun setAuctionType(type: AuctionType) { _auctionType.value = type }
    fun setCardRule(rule: CardRuleType) { _cardRule.value = rule }
    fun setCategory(category: AnimeCategory) { _selectedCategory.value = category }

    private fun triggerHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(45, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(45)
            }
        } catch (_: Exception) {}
    }

    private fun triggerAlertHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 80, 60, 120)
                val amplitudes = intArrayOf(0, 220, 0, 255)
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(longArrayOf(0, 80, 60, 120), -1)
            }
        } catch (_: Exception) {}
    }

    // ==========================================
    // AUCTION GAMEPLAY
    // ==========================================

    fun startAuction(versusAi: Boolean) {
        _isVersusAi.value = versusAi
        val type = _auctionType.value
        val pool = AnimeDatabase.getCharactersForPool(_selectedCategory.value).shuffled().toMutableList()
        availableCharactersPool = pool

        val firstCharacter = pool.removeFirstOrNull() ?: AnimeDatabase.allCharacters.first()

        _auctionState.value = AuctionGameState(
            currentRound = 1,
            totalRounds = type.slots,
            timeRemainingSeconds = 30,
            currentCharacter = firstCharacter,
            currentHighestBidM = 0,
            currentHighestBidder = null,
            userProposedBidM = 1,
            userBudgetM = type.initialBudgetM,
            opponentBudgetM = type.initialBudgetM,
            userName = "ISC_REY",
            opponentName = if (versusAi) "فريق الـ AI" else "اللاعب 2",
            bannerStatusText = "الدور الافتتاحي: زايد أو تخطَّ الشخصية لاختيار غيرها."
        )

        navigateTo(AppScreen.LIVE_AUCTION)
        startAuctionTimer()
    }

    private fun startAuctionTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _auctionState.value
                if (current.isRoundFinished || current.isGameFinished) break

                val newTime = current.timeRemainingSeconds - 1
                if (newTime <= 0) {
                    // Time up! End round and decide winner
                    endAuctionRound()
                    break
                } else {
                    _auctionState.update { it.copy(timeRemainingSeconds = newTime) }

                    // AI Bidding Chance if timer is running and vs AI
                    if (_isVersusAi.value && !current.opponentPassed && current.currentHighestBidder != "AI") {
                        val shouldAiBid = evaluateAiBidDecision(current)
                        if (shouldAiBid != null) {
                            delay(600)
                            aiPlaceBid(shouldAiBid)
                        }
                    }
                }
            }
        }
    }

    private fun evaluateAiBidDecision(state: AuctionGameState): Int? {
        val character = state.currentCharacter ?: return null
        val aiBudget = state.opponentBudgetM
        val currentBid = state.currentHighestBidM

        // AI maximum valuation based on character rating
        val maxAiWillPay = when {
            character.powerRating >= 97 -> minOf(aiBudget - 10, 45)
            character.powerRating >= 93 -> minOf(aiBudget - 10, 30)
            character.powerRating >= 90 -> minOf(aiBudget - 10, 20)
            character.powerRating >= 75 -> minOf(aiBudget - 10, 12)
            else -> 6 // low tier / troll
        }

        if (currentBid >= maxAiWillPay || aiBudget < currentBid + 1) {
            return null // AI will pass / skip
        }

        // Random chance based on time remaining to make bidding feel natural
        val bidProbability = if (state.timeRemainingSeconds in 3..25) 45 else 80
        if (Random.nextInt(100) < bidProbability) {
            val increment = when {
                character.rarity == CharacterRarity.SSS -> if (Random.nextBoolean()) 5 else 3
                character.rarity == CharacterRarity.SS -> 2
                else -> 1
            }
            val targetBid = minOf(currentBid + increment, maxAiWillPay)
            if (targetBid > currentBid && targetBid <= aiBudget) {
                return targetBid
            }
        }
        return null
    }

    private fun aiPlaceBid(bidAmount: Int) {
        triggerAlertHaptic()
        val extraTime = if (_cardRule.value == CardRuleType.LAST_BID && _auctionState.value.timeRemainingSeconds <= 5) 5 else 0

        _auctionState.update { current ->
            current.copy(
                currentHighestBidM = bidAmount,
                currentHighestBidder = "AI",
                userProposedBidM = bidAmount + 1,
                timeRemainingSeconds = current.timeRemainingSeconds + extraTime,
                bannerStatusText = "🚨 مزايدة جديدة: الـ AI رفع المزاد إلى $bidAmount بيري!",
                lastOpponentBidAmountM = bidAmount,
                opponentBidAlertTrigger = System.currentTimeMillis()
            )
        }
    }

    fun adjustUserProposedBid(delta: Int) {
        triggerHaptic()
        val current = _auctionState.value
        val minAllowed = current.currentHighestBidM + 1
        val maxAllowed = current.userBudgetM
        val newBid = (current.userProposedBidM + delta).coerceIn(minAllowed, maxAllowed.coerceAtLeast(minAllowed))
        _auctionState.update { it.copy(userProposedBidM = newBid) }
    }

    fun confirmUserBid() {
        val current = _auctionState.value
        val bid = current.userProposedBidM

        if (bid <= current.currentHighestBidM || bid > current.userBudgetM) {
            return
        }

        triggerHaptic()
        val extraTime = if (_cardRule.value == CardRuleType.LAST_BID && current.timeRemainingSeconds <= 5) 5 else 0

        _auctionState.update { state ->
            state.copy(
                currentHighestBidM = bid,
                currentHighestBidder = "USER",
                userProposedBidM = bid + 1,
                userPassed = false,
                timeRemainingSeconds = state.timeRemainingSeconds + extraTime,
                bannerStatusText = "🔥 أنت صاحب أعلى مزايدة حالياً بـ $bid بيري!"
            )
        }

        // Trigger immediate AI consideration
        if (_isVersusAi.value) {
            viewModelScope.launch {
                delay(700)
                val updatedState = _auctionState.value
                if (updatedState.isRoundFinished || updatedState.isGameFinished) return@launch
                val aiBid = evaluateAiBidDecision(updatedState)
                if (aiBid != null) {
                    aiPlaceBid(aiBid)
                } else {
                    // AI withdraws / folds immediately! Do NOT wait for timer!
                    triggerHaptic()
                    timerJob?.cancel()
                    _auctionState.update {
                        it.copy(
                            opponentPassed = true,
                            bannerStatusText = "🤖 انسحب الـ AI من المزاد! فزت بالصفقة فوراً!"
                        )
                    }
                    delay(800)
                    endAuctionRound()
                }
            }
        }
    }

    fun passCurrentCharacter() {
        triggerHaptic()
        timerJob?.cancel()
        val current = _auctionState.value
        val banner = if (current.currentHighestBidder == "AI" || current.currentHighestBidder == "P2") {
            "انسحبت من المزاد! فاز ${current.opponentName} بـ ${current.currentCharacter?.nameAr}!"
        } else {
            "تم تخطي الشخصية فوراً لانسحابك."
        }
        _auctionState.update { state ->
            state.copy(
                userPassed = true,
                bannerStatusText = banner
            )
        }
        viewModelScope.launch {
            delay(600)
            endAuctionRound()
        }
    }

    private fun endAuctionRound() {
        timerJob?.cancel()
        val current = _auctionState.value
        val character = current.currentCharacter ?: return

        var newUserBudget = current.userBudgetM
        var newOpponentBudget = current.opponentBudgetM
        val newUserSquad = current.userSquad.toMutableList()
        val newOpponentSquad = current.opponentSquad.toMutableList()
        val winnerText: String

        if (current.currentHighestBidder == "USER") {
            newUserBudget -= current.currentHighestBidM
            newUserSquad.add(character)
            winnerText = "🎉 مبروك! فزت بـ ${character.nameAr} مقابل ${current.currentHighestBidM} بيري!"
        } else if (current.currentHighestBidder == "AI" || current.currentHighestBidder == "P2") {
            newOpponentBudget -= current.currentHighestBidM
            newOpponentSquad.add(character)
            winnerText = "🤖 الـ AI حسم مزاد ${character.nameAr} مقابل ${current.currentHighestBidM} بيري!"
        } else {
            winnerText = "لم يزايد أحد على ${character.nameAr} وتم تخطيه."
        }

        val isFinished = current.currentRound >= current.totalRounds || availableCharactersPool.isEmpty()

        _auctionState.update {
            it.copy(
                isRoundFinished = true,
                isGameFinished = isFinished,
                userBudgetM = newUserBudget,
                opponentBudgetM = newOpponentBudget,
                userSquad = newUserSquad,
                opponentSquad = newOpponentSquad,
                bannerStatusText = winnerText
            )
        }

        viewModelScope.launch {
            delay(2500)
            if (isFinished) {
                navigateTo(AppScreen.AUCTION_RESULTS)
            } else {
                nextAuctionRound()
            }
        }
    }

    private fun nextAuctionRound() {
        val nextCharacter = availableCharactersPool.removeFirstOrNull() ?: AnimeDatabase.allCharacters.shuffled().first()
        _auctionState.update { state ->
            state.copy(
                currentRound = state.currentRound + 1,
                timeRemainingSeconds = 30,
                currentCharacter = nextCharacter,
                currentHighestBidM = 0,
                currentHighestBidder = null,
                userProposedBidM = 1,
                userPassed = false,
                opponentPassed = false,
                isRoundFinished = false,
                bannerStatusText = "الجولة ${state.currentRound + 1}: من يظفر بـ ${nextCharacter.nameAr}؟"
            )
        }
        startAuctionTimer()
    }

    // ==========================================
    // HIDDEN CHARACTER GAMEPLAY
    // ==========================================

    fun startHiddenCharacterGame(versusAi: Boolean) {
        _isVersusAi.value = versusAi
        val pool = AnimeDatabase.allCharacters.shuffled().toMutableList()
        availableCharactersPool = pool

        val revealed = pool.removeFirst()
        val mystery = pool.removeFirst()

        _hiddenState.value = HiddenCharacterRoundState(
            currentRound = 1,
            totalRounds = 5,
            revealedCharacter = revealed,
            mysteryCharacter = mystery,
            isMysteryRevealed = false,
            userSquad = emptyList(),
            opponentSquad = emptyList()
        )

        navigateTo(AppScreen.HIDDEN_CHARACTER_PLAY)
    }

    fun chooseCharacterInHiddenMode(takeHidden: Boolean) {
        triggerHaptic()
        val current = _hiddenState.value
        val revealed = current.revealedCharacter ?: return
        val mystery = current.mysteryCharacter ?: return

        val userPick = if (takeHidden) mystery else revealed
        val opponentPick = if (takeHidden) revealed else mystery

        val newUserSquad = current.userSquad + userPick
        val newOpponentSquad = current.opponentSquad + opponentPick
        val isFinished = current.currentRound >= current.totalRounds

        _hiddenState.update {
            it.copy(
                isMysteryRevealed = true,
                userPick = userPick,
                opponentPick = opponentPick,
                userChosenHidden = takeHidden,
                userSquad = newUserSquad,
                opponentSquad = newOpponentSquad,
                isRoundFinished = true,
                isGameFinished = isFinished
            )
        }

        viewModelScope.launch {
            delay(2800)
            if (isFinished) {
                navigateTo(AppScreen.HIDDEN_RESULTS)
            } else {
                nextHiddenRound()
            }
        }
    }

    private fun nextHiddenRound() {
        val pool = availableCharactersPool
        val revealed = if (pool.isNotEmpty()) pool.removeFirst() else AnimeDatabase.allCharacters.shuffled().first()
        val mystery = if (pool.isNotEmpty()) pool.removeFirst() else AnimeDatabase.allCharacters.shuffled().last()

        _hiddenState.update { current ->
            current.copy(
                currentRound = current.currentRound + 1,
                revealedCharacter = revealed,
                mysteryCharacter = mystery,
                isMysteryRevealed = false,
                userPick = null,
                opponentPick = null,
                isRoundFinished = false
            )
        }
    }

    // ==========================================
    // BLUFF GAMEPLAY ("أنت هتحور؟")
    // ==========================================

    private fun startBluffGame() {
        val questions = AnimeTriviaDatabase.questions.shuffled()
        _bluffState.value = BluffGameState(
            currentQuestionIndex = 0,
            currentQuestion = questions.first(),
            userScore = 0,
            opponentScore = 0,
            hasAnswered = false,
            isGameOver = false
        )
        navigateTo(AppScreen.ANIME_BLUFF)
    }

    fun answerBluff(userSaysTrue: Boolean) {
        triggerHaptic()
        val current = _bluffState.value
        val isCorrect = userSaysTrue == current.currentQuestion.isTrue
        val aiSaysTrue = if (Random.nextInt(100) < 65) current.currentQuestion.isTrue else !current.currentQuestion.isTrue
        val aiIsCorrect = aiSaysTrue == current.currentQuestion.isTrue

        val newUserScore = current.userScore + (if (isCorrect) 10 else 0)
        val newOpponentScore = current.opponentScore + (if (aiIsCorrect) 10 else 0)

        _bluffState.update {
            it.copy(
                hasAnswered = true,
                userAnswerWasCorrect = isCorrect,
                userScore = newUserScore,
                opponentScore = newOpponentScore
            )
        }
    }

    fun nextBluffQuestion() {
        val current = _bluffState.value
        val questions = AnimeTriviaDatabase.questions
        val nextIndex = current.currentQuestionIndex + 1

        if (nextIndex >= questions.size) {
            _bluffState.update { it.copy(isGameOver = true) }
        } else {
            _bluffState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    currentQuestion = questions[nextIndex],
                    hasAnswered = false
                )
            }
        }
    }

    // ==========================================
    // CARD BOOSTER PACKS
    // ==========================================

    fun openCardPack() {
        triggerHaptic()
        _packState.update { it.copy(isOpening = true) }

        viewModelScope.launch {
            delay(1200)
            val pulledCards = AnimeDatabase.allCharacters.shuffled().take(3)
            triggerHaptic()

            _packState.update { state ->
                state.copy(
                    isOpening = false,
                    openedCards = pulledCards,
                    collectedCards = (state.collectedCards + pulledCards).distinctBy { it.id },
                    packCount = (state.packCount - 1).coerceAtLeast(0)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
