package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.AnimeTradingCard
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimeGold
import com.example.ui.theme.AnimeGoldLight
import com.example.ui.theme.AnimeGreen
import com.example.ui.theme.AnimeNavyCard
import com.example.ui.theme.AnimeNavyDark
import com.example.ui.theme.AnimeNavyMedium
import com.example.ui.theme.AnimeRed
import com.example.viewmodel.AnimeGameViewModel
import com.example.viewmodel.AppScreen

@Composable
fun HiddenCharacterSetupScreen(
    viewModel: AnimeGameViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(AnimeNavyMedium, AnimeNavyDark, Color(0xFF04060E))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                text = "الشخصية الخفية",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Surface(
                shape = CircleShape,
                color = AnimeNavyCard,
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { viewModel.navigateTo(AppScreen.HOME) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "رجوع",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big Hero Preview Card matching Screenshot 4
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(260.dp),
            shape = RoundedCornerShape(22.dp),
            border = BorderStroke(2.5.dp, AnimeGold),
            colors = CardDefaults.cardColors(containerColor = AnimeNavyCard)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background visual
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF0D47A1), AnimeNavyDark)
                            )
                        )
                )

                // Top Checkmark
                Surface(
                    shape = CircleShape,
                    color = AnimeGold,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(26.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "الشخصية الخفية",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "تاخد الشخصية اللي قدامك.. ولا تخاطر بالشخصية الخفية؟",
                        fontSize = 12.sp,
                        color = AnimeGoldLight,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Dual mini cards representation
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Revealed dummy
                        Surface(
                            modifier = Modifier
                                .width(70.dp)
                                .height(95.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.5.dp, Color(0xFF00E5FF)),
                            color = Color(0xFF002244)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("94", fontWeight = FontWeight.Black, color = Color.White, fontSize = 16.sp)
                                Text("ليفاي", fontWeight = FontWeight.Bold, color = AnimeCyan, fontSize = 10.sp)
                            }
                        }

                        Text("VS", fontSize = 16.sp, fontWeight = FontWeight.Black, color = AnimeGold)

                        // Mystery dummy
                        Surface(
                            modifier = Modifier
                                .width(70.dp)
                                .height(95.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.5.dp, AnimeGold),
                            color = Color(0xFF331A00)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.QuestionMark,
                                    contentDescription = null,
                                    tint = AnimeGold,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Rule selector: 1 vs 1 matching Screenshot 4
        Text(
            text = "اختر نمط اللعب",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AnimeNavyCard,
            border = BorderStroke(2.dp, AnimeGold),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(80.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "1 ضد 1 (مواجهة كروت)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AnimeGoldLight
                )
                Text(
                    text = "5 جولات حاسمة لاختيار الأقوى",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Buttons matching Screenshot 4: Play (Blue) vs Challenge AI (Green)
        Button(
            onClick = { viewModel.startHiddenCharacterGame(versusAi = false) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("hidden_play_local"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("العب (لاعب ضد لاعب)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { viewModel.startHiddenCharacterGame(versusAi = true) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("hidden_challenge_ai"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AnimeGreen)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Memory, contentDescription = null, tint = Color(0xFF003314))
                Spacer(modifier = Modifier.width(8.dp))
                Text("تحدّي الـ AI", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF003314))
            }
        }
    }
}

@Composable
fun HiddenCharacterPlayScreen(
    viewModel: AnimeGameViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.hiddenState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF070E1C), Color(0xFF0A152A), Color(0xFF03060C))
                )
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Round Badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AnimeGold,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "الجولة ${state.currentRound} من ${state.totalRounds}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Text(
                text = "الشخصية الخفية",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            Surface(
                shape = CircleShape,
                color = AnimeNavyCard,
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier
                    .size(38.dp)
                    .clickable { viewModel.navigateTo(AppScreen.HOME) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "رجوع",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Instruction
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF101B34),
            border = BorderStroke(1.dp, Color(0xFF1E3A6E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (state.isMysteryRevealed) {
                    if (state.userChosenHidden)
                        "⚡ خاطرت بالخفية وحصلت على: ${state.userPick?.nameAr} (${state.userPick?.powerRating} OVR)!"
                    else
                        "🛡️ اخترت المكشوفة: ${state.userPick?.nameAr} (${state.userPick?.powerRating} OVR)!"
                } else {
                    "هل تضمن الشخصية المكشوفة.. أم تقامر بالخفية؟"
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AnimeGoldLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Side-by-side Dual Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Left Card: The Revealed Character
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "الشخصية المكشوفة",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AnimeCyan
                )
                Spacer(modifier = Modifier.height(6.dp))
                if (state.revealedCharacter != null) {
                    AnimeTradingCard(
                        character = state.revealedCharacter!!,
                        isCompact = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Right Card: The Mystery Character
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "الشخصية الخفية",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AnimeGold
                )
                Spacer(modifier = Modifier.height(6.dp))

                if (state.isMysteryRevealed && state.mysteryCharacter != null) {
                    // Revealed Anime Card
                    AnimeTradingCard(
                        character = state.mysteryCharacter!!,
                        isCompact = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    // Mystery Face-Down Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(290.dp),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(3.dp, AnimeGold),
                        colors = CardDefaults.cardColors(containerColor = AnimeNavyDark)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_card_back),
                                contentDescription = "كارت مخفي",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.35f))
                            )
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = AnimeGold.copy(alpha = 0.85f),
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.QuestionMark,
                                            contentDescription = null,
                                            tint = Color.Black,
                                            modifier = Modifier.size(34.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "؟؟؟",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = AnimeGoldLight
                                )
                                Text(
                                    text = "مفاجأة الجولة",
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Choice Action Buttons
        if (!state.isRoundFinished) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Button 1: Safe Pick (Revealed)
                Button(
                    onClick = { viewModel.chooseCharacterInHiddenMode(takeHidden = false) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("choose_revealed_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "اختيار المكشوفة",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "(خيار آمن)",
                            fontSize = 10.sp,
                            color = Color(0xFFB3E5FC)
                        )
                    }
                }

                // Button 2: Gamble Pick (Mystery)
                Button(
                    onClick = { viewModel.chooseCharacterInHiddenMode(takeHidden = true) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("choose_hidden_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AnimeGold)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "المخاطرة بالخفية",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF2E1C00)
                        )
                        Text(
                            text = "(حظ / خارق أو مقلب)",
                            fontSize = 10.sp,
                            color = Color(0xFF4A3700)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Squads summary bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "تشكيلتك: ${state.userSquad.size} شخصيات (مجموع: ${state.userSquad.sumOf { it.powerRating }})",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AnimeCyan
            )
            Text(
                text = "تشكيلة الخصم: ${state.opponentSquad.size} شخصيات (مجموع: ${state.opponentSquad.sumOf { it.powerRating }})",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5252)
            )
        }
    }
}
