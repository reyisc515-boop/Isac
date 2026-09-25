package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AnimeBluffScreen
import com.example.ui.screens.AuctionSetupScreen
import com.example.ui.screens.HiddenCharacterPlayScreen
import com.example.ui.screens.HiddenCharacterSetupScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LiveAuctionScreen
import com.example.ui.screens.PackOpeningScreen
import com.example.ui.screens.SquadResultScreen
import com.example.ui.theme.AnimeNavyDark
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AnimeGameViewModel
import com.example.viewmodel.AppScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val gameViewModel: AnimeGameViewModel = viewModel()
                val currentScreen by gameViewModel.currentScreen.collectAsState()

                // Hardware / gesture back handling
                if (currentScreen != AppScreen.HOME) {
                    BackHandler {
                        gameViewModel.navigateTo(AppScreen.HOME)
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = AnimeNavyDark
                ) {
                    when (currentScreen) {
                        AppScreen.HOME -> {
                            HomeScreen(viewModel = gameViewModel)
                        }
                        AppScreen.AUCTION_SETUP -> {
                            AuctionSetupScreen(viewModel = gameViewModel)
                        }
                        AppScreen.LIVE_AUCTION -> {
                            LiveAuctionScreen(viewModel = gameViewModel)
                        }
                        AppScreen.HIDDEN_CHARACTER_SETUP -> {
                            HiddenCharacterSetupScreen(viewModel = gameViewModel)
                        }
                        AppScreen.HIDDEN_CHARACTER_PLAY -> {
                            HiddenCharacterPlayScreen(viewModel = gameViewModel)
                        }
                        AppScreen.ANIME_BLUFF -> {
                            AnimeBluffScreen(viewModel = gameViewModel)
                        }
                        AppScreen.CARD_PACKS -> {
                            PackOpeningScreen(viewModel = gameViewModel)
                        }
                        AppScreen.AUCTION_RESULTS -> {
                            val auctionState by gameViewModel.auctionState.collectAsState()
                            SquadResultScreen(
                                title = "نتائج مزاد الأنمي الأسطوري",
                                userSquad = auctionState.userSquad,
                                opponentSquad = auctionState.opponentSquad,
                                userName = auctionState.userName,
                                opponentName = auctionState.opponentName,
                                onPlayAgain = { gameViewModel.navigateTo(AppScreen.AUCTION_SETUP) },
                                onHome = { gameViewModel.navigateTo(AppScreen.HOME) }
                            )
                        }
                        AppScreen.HIDDEN_RESULTS -> {
                            val hiddenState by gameViewModel.hiddenState.collectAsState()
                            SquadResultScreen(
                                title = "نتائج تحدي الشخصية الخفية",
                                userSquad = hiddenState.userSquad,
                                opponentSquad = hiddenState.opponentSquad,
                                userName = "أنت",
                                opponentName = if (gameViewModel.isVersusAi.value) "الـ AI" else "اللاعب 2",
                                onPlayAgain = { gameViewModel.navigateTo(AppScreen.HIDDEN_CHARACTER_SETUP) },
                                onHome = { gameViewModel.navigateTo(AppScreen.HOME) }
                            )
                        }
                    }
                }
            }
        }
    }
}
