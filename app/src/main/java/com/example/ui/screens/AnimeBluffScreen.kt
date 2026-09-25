package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Psychology
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun AnimeBluffScreen(
    viewModel: AnimeGameViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.bluffState.collectAsState()
    val q = state.currentQuestion

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(AnimeNavyMedium, AnimeNavyDark, Color(0xFF060914))
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
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AnimeGold,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "نقاطك: ${state.userScore} | الخصم: ${state.opponentScore}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Text(
                text = "أنت هتحور؟",
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

        // Center Question Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, AnimeGold),
            colors = CardDefaults.cardColors(containerColor = AnimeNavyCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Anime tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AnimeCyan.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, AnimeCyan)
                ) {
                    Text(
                        text = "أنمي: ${q.animeTitle}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AnimeCyan,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    tint = AnimeGold,
                    modifier = Modifier.size(54.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "« ${q.statementAr} »",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "هل هذه المعلومة حقيقة في أحداث الأنمي أم تحوير وافتراء؟",
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Answer Explanation Reveal
        AnimatedVisibility(visible = state.hasAnswered) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (state.userAnswerWasCorrect) Color(0xFF1B5E20) else Color(0xFFB71C1C),
                border = BorderStroke(1.5.dp, if (state.userAnswerWasCorrect) AnimeGreen else AnimeRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (state.userAnswerWasCorrect) "🎯 إجابة صحيحة! (+10 نقاط)" else "❌ إجابة خاطئة!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = q.explanationAr,
                        fontSize = 12.sp,
                        color = Color(0xFFECEFF1),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Action Buttons: Truth vs Bluff
        if (!state.hasAnswered) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Truth Button (Green)
                Button(
                    onClick = { viewModel.answerBluff(userSaysTrue = true) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .testTag("answer_true_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AnimeGreen)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color(0xFF003314))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("حقيقة", fontSize = 17.sp, fontWeight = FontWeight.Black, color = Color(0xFF003314))
                    }
                }

                // Bluff Button (Red)
                Button(
                    onClick = { viewModel.answerBluff(userSaysTrue = false) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .testTag("answer_false_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AnimeRed)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("تحوير / كذب", fontSize = 17.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                }
            }
        } else {
            // Next Question Button
            Button(
                onClick = { viewModel.nextBluffQuestion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("next_question_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AnimeGold)
            ) {
                Text(
                    text = if (state.isGameOver) "العودة للرئيسية" else "السؤال التالي ➡️",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
        }
    }
}
