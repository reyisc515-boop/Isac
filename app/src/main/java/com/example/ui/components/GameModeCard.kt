package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GameMode
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimeGold
import com.example.ui.theme.AnimeGoldLight
import com.example.ui.theme.AnimeNavyCard
import com.example.ui.theme.AnimeNavyDark
import com.example.ui.theme.AnimeNavyMedium
import com.example.ui.theme.AnimeRed

@Composable
fun GameModeCard(
    mode: GameMode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (primaryAccent, iconVector, badgeText) = when (mode) {
        GameMode.HIDDEN_CHARACTER -> Triple(AnimeCyan, Icons.Default.QuestionMark, "1 ضد 1")
        GameMode.AUCTION -> Triple(AnimeGold, Icons.Default.Gavel, "المزايدة")
        GameMode.GUILD_LINEUP -> Triple(Color(0xFF38BDF8), Icons.Default.GridView, "التناغم")
        GameMode.ANIME_BLUFF -> Triple(Color(0xFFFF5252), Icons.Default.Psychology, "حقائق وتزييف")
        GameMode.EXACT_BUDGET -> Triple(AnimeGold, Icons.Default.Savings, "تحدي الميزانية")
        GameMode.CARD_PACKS -> Triple(Color(0xFFE040FB), Icons.Default.AutoAwesome, "باكات")
    }

    val cardBorder = Brush.verticalGradient(
        colors = listOf(
            primaryAccent.copy(alpha = 0.8f),
            primaryAccent.copy(alpha = 0.3f),
            AnimeNavyCard
        )
    )

    Card(
        modifier = modifier
            .aspectRatio(0.88f)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(22.dp),
                spotColor = primaryAccent
            )
            .testTag("mode_card_${mode.name.lowercase()}")
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(2.dp, cardBorder),
        colors = CardDefaults.cardColors(containerColor = AnimeNavyDark)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background ambient glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                primaryAccent.copy(alpha = 0.22f),
                                AnimeNavyMedium,
                                AnimeNavyDark
                            )
                        )
                    )
            )

            // Lock overlay badge if locked
            if (mode.isLocked) {
                Surface(
                    shape = CircleShape,
                    color = AnimeGold.copy(alpha = 0.9f),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopStart)
                        .size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "مقفل",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Top-right pill tag
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = primaryAccent.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, primaryAccent.copy(alpha = 0.5f)),
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = badgeText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryAccent,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            // Center Graphic & Icon Art
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 28.dp, bottom = 48.dp, start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = AnimeNavyCard,
                    border = BorderStroke(2.dp, primaryAccent),
                    modifier = Modifier.size(68.dp),
                    shadowElevation = 6.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = null,
                            tint = primaryAccent,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = mode.titleAr,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            // Bottom Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(44.dp),
                color = Color(0xFF070B14).copy(alpha = 0.95f),
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mode.titleAr,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryAccent,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
