package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.model.AnimeCharacter
import com.example.model.CharacterRarity
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimeGold
import com.example.ui.theme.AnimeGoldBorder
import com.example.ui.theme.AnimeGoldLight
import com.example.ui.theme.AnimeNavyCard
import com.example.ui.theme.AnimeNavyDark
import com.example.ui.theme.AnimeNavyMedium
import com.example.ui.theme.AnimeRed

@Composable
fun AnimeTradingCard(
    character: AnimeCharacter,
    modifier: Modifier = Modifier,
    isCompact: Boolean = false,
    showGlow: Boolean = true,
    showLargeNumberBelow: Boolean = true
) {
    val primaryColor = Color(character.primaryColorHex)
    val secondaryColor = Color(character.secondaryColorHex)
    val rarityColor = Color(character.rarity.badgeColorHex)

    val cardBorder = Brush.verticalGradient(
        colors = listOf(
            AnimeGoldLight,
            AnimeGold,
            rarityColor,
            AnimeGoldBorder
        )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The Luxury Trading Card (Matching Screenshot 3 FUT / Buffon Style)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = if (showGlow) 18.dp else 6.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = rarityColor,
                    ambientColor = AnimeGold
                ),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(if (isCompact) 2.dp else 3.5.dp, cardBorder),
            colors = CardDefaults.cardColors(containerColor = AnimeNavyDark)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                secondaryColor.copy(alpha = 0.9f),
                                AnimeNavyCard,
                                AnimeNavyDark
                            )
                        )
                    )
            ) {
                // Background radial illumination
                Box(
                    modifier = Modifier
                        .size(if (isCompact) 140.dp else 260.dp)
                        .align(Alignment.Center)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(if (isCompact) 8.dp else 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Row: Big In-Card Rating (98 SSS) + Rarity Stars
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        // Rating & Rank Badge (Screenshot 3 style: "92 GK" -> "98 SSS")
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "${character.powerRating}",
                                fontSize = if (isCompact) 22.sp else 36.sp,
                                fontWeight = FontWeight.Black,
                                color = AnimeGold,
                                letterSpacing = (-1).sp,
                                lineHeight = if (isCompact) 22.sp else 36.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = rarityColor.copy(alpha = 0.3f),
                                border = BorderStroke(1.dp, rarityColor)
                            ) {
                                Text(
                                    text = character.rarity.name,
                                    fontSize = if (isCompact) 8.sp else 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                            Text(
                                text = character.roleAr,
                                fontSize = if (isCompact) 8.sp else 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE2E8F0),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Right side: Series Tag & Stars
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AnimeNavyMedium.copy(alpha = 0.9f),
                                border = BorderStroke(1.dp, AnimeGold.copy(alpha = 0.6f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = AnimeGold,
                                        modifier = Modifier.size(if (isCompact) 10.dp else 13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = character.animeTitleAr.take(12),
                                        fontSize = if (isCompact) 8.sp else 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AnimeGoldLight
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                repeat(5) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = AnimeGold,
                                        modifier = Modifier.size(if (isCompact) 9.dp else 12.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(if (isCompact) 4.dp else 8.dp))

                    // Character Real Picture Area (Always shows vibrant Anime Portrait, with Coil image overlay if available)
                    Box(
                        modifier = Modifier
                            .size(if (isCompact) 110.dp else 185.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                BorderStroke(
                                    2.5.dp,
                                    Brush.sweepGradient(
                                        listOf(
                                            AnimeGoldLight,
                                            rarityColor,
                                            AnimeGold,
                                            Color.White
                                        )
                                    )
                                ),
                                RoundedCornerShape(20.dp)
                            )
                            .background(AnimeNavyMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        // 1. Guaranteed Anime Portrait Artwork - Always renders immediately!
                        AnimePortraitView(
                            character = character,
                            modifier = Modifier.fillMaxSize()
                        )

                        // 2. If online image succeeds, overlay it seamlessly
                        if (character.imageUrl.isNotEmpty()) {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(character.imageUrl)
                                    .setHeader("User-Agent", "Mozilla/5.0")
                                    .crossfade(true)
                                    .build(),
                                contentDescription = character.nameAr,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                loading = null,
                                error = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(if (isCompact) 6.dp else 10.dp))

                    // Name Banner Ribbon (matching Screenshot 3: BUFFON banner style)
                    Surface(
                        modifier = Modifier.fillMaxWidth(0.94f),
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF1E293B).copy(alpha = 0.95f),
                        border = BorderStroke(1.5.dp, AnimeGold)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = if (isCompact) 3.dp else 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = character.nameEn,
                                fontSize = if (isCompact) 11.sp else 16.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = AnimeGoldLight,
                                letterSpacing = 1.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = character.nameAr,
                                fontSize = if (isCompact) 10.sp else 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Affiliation / Clan & Signature Skill
                    if (!isCompact) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🏴 ${character.clanOrAffiliation}",
                                fontSize = 10.sp,
                                color = Color(0xFF90CAF9),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF0F172A).copy(alpha = 0.85f),
                            border = BorderStroke(1.dp, Color(0xFF334155))
                        ) {
                            Text(
                                text = "⚡ ${character.signatureSkillAr}",
                                fontSize = 11.sp,
                                color = AnimeGoldLight,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // =================================================================
        // USER REQUEST: ظهور رقم الشخصية كبير تحت البطاقة
        // (Large character number & stats displayed prominently below card)
        // =================================================================
        if (showLargeNumberBelow && !isCompact) {
            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(0.96f),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF070E1E),
                border = BorderStroke(2.5.dp, AnimeGold),
                shadowElevation = 10.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Massive Golden Number Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = AnimeGold,
                            border = BorderStroke(3.dp, AnimeGoldLight),
                            modifier = Modifier.size(62.dp),
                            shadowElevation = 8.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${character.powerRating}",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF1A1200)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "رقم وقوة الشخصية: ${character.powerRating}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = AnimeGoldLight
                            )
                            Text(
                                text = "${character.rarity.labelAr} • ${character.basePriceM}M بيري",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }

                    // Mini Stats Grid (ATK, SPD, IQ)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatPill(label = "هجوم", value = character.stats.attack, color = AnimeRed)
                        StatPill(label = "سرعة", value = character.stats.speed, color = AnimeCyan)
                        StatPill(label = "ذكاء", value = character.stats.iq, color = Color(0xFFB388FF))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatPill(
    label: String,
    value: Int,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$value",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            Text(
                text = label,
                fontSize = 8.sp,
                color = Color.White
            )
        }
    }
}
