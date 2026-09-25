package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnimeCharacter
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
fun SquadResultScreen(
    title: String,
    userSquad: List<AnimeCharacter>,
    opponentSquad: List<AnimeCharacter>,
    userName: String,
    opponentName: String,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userTotal = userSquad.sumOf { it.powerRating }
    val opponentTotal = opponentSquad.sumOf { it.powerRating }
    val userWon = userTotal >= opponentTotal

    val mvp = (userSquad + opponentSquad).maxByOrNull { it.powerRating }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(AnimeNavyMedium, AnimeNavyDark, Color(0xFF03050C))
                )
            )
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Trophy & Celebration Header
        Surface(
            shape = CircleShape,
            color = if (userWon) AnimeGold.copy(alpha = 0.2f) else AnimeRed.copy(alpha = 0.2f),
            border = BorderStroke(2.dp, if (userWon) AnimeGold else AnimeRed),
            modifier = Modifier.size(74.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = if (userWon) AnimeGold else AnimeRed,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (userWon) "🏆 فوز ساحق لـ $userName!" else "🤖 تفوق فريق $opponentName!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = if (userWon) AnimeGoldLight else Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = title,
            fontSize = 13.sp,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Total Rating Comparison Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.5.dp, AnimeGold),
            colors = CardDefaults.cardColors(containerColor = AnimeNavyCard)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(userName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AnimeCyan)
                    Text("$userTotal", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("${userSquad.size} شخصيات", fontSize = 11.sp, color = Color(0xFF94A3B8))
                }

                Text("VS", fontSize = 20.sp, fontWeight = FontWeight.Black, color = AnimeGold)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(opponentName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF5252))
                    Text("$opponentTotal", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("${opponentSquad.size} شخصيات", fontSize = 11.sp, color = Color(0xFF94A3B8))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // MVP Highlight
        if (mvp != null) {
            Text(
                text = "⭐ نجم الجولة الأسطوري (MVP):",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AnimeGold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimeTradingCard(
                character = mvp,
                isCompact = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // User Squad Horizontal Gallery
        Text(
            text = "تشكيلة $userName:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = AnimeCyan,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(userSquad) { character ->
                AnimeTradingCard(
                    character = character,
                    isCompact = true,
                    modifier = Modifier.width(150.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Actions: Replay or Home
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("play_again_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AnimeGold)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Replay, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("العب مجدداً", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }

            Button(
                onClick = onHome,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("home_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AnimeNavyCard)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("الرئيسية", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
