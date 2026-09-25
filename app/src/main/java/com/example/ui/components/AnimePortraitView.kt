package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnimeCharacter
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimeGold
import com.example.ui.theme.AnimeGoldLight
import com.example.ui.theme.AnimeNavyDark
import com.example.ui.theme.AnimeRed

@Composable
fun AnimePortraitView(
    character: AnimeCharacter,
    modifier: Modifier = Modifier
) {
    val primaryColor = Color(character.primaryColorHex)
    val secondaryColor = Color(character.secondaryColorHex)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.5f),
                        secondaryColor,
                        AnimeNavyDark
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            // Dynamic Energy Aura circles
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(primaryColor.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(cx, cy),
                    radius = w * 0.48f
                )
            )

            // Character Specific Artistic Renderings
            when (character.id) {
                "luffy_g5" -> {
                    // Gear 5 Sun God Nika: White clouds, Straw hat, golden eyes
                    // Swirling White Cloud Aura
                    val cloudPath = Path().apply {
                        moveTo(cx - w * 0.35f, cy + h * 0.15f)
                        quadraticTo(cx - w * 0.45f, cy - h * 0.1f, cx - w * 0.15f, cy - h * 0.25f)
                        quadraticTo(cx + w * 0.15f, cy - h * 0.35f, cx + w * 0.35f, cy - h * 0.15f)
                        quadraticTo(cx + w * 0.45f, cy + h * 0.15f, cx, cy + h * 0.35f)
                        close()
                    }
                    drawPath(cloudPath, Brush.linearGradient(listOf(Color.White, Color(0xFFFFD54F))), style = Stroke(width = 6f))

                    // Straw Hat behind head
                    drawOval(
                        color = Color(0xFFFFB300),
                        topLeft = Offset(cx - w * 0.32f, cy - h * 0.42f),
                        size = Size(w * 0.64f, h * 0.22f)
                    )
                    // Red ribbon on hat
                    drawOval(
                        color = Color(0xFFD50000),
                        topLeft = Offset(cx - w * 0.25f, cy - h * 0.38f),
                        size = Size(w * 0.5f, h * 0.08f)
                    )
                    // White wild spiky flame hair
                    drawCircle(color = Color(0xFFFAFAFA), radius = w * 0.22f, center = Offset(cx, cy - h * 0.15f))
                    // Face
                    drawCircle(color = Color(0xFFFFE0B2), radius = w * 0.17f, center = Offset(cx, cy - h * 0.08f))
                    // Big Grin
                    val grinPath = Path().apply {
                        moveTo(cx - w * 0.1f, cy - h * 0.04f)
                        quadraticTo(cx, cy + h * 0.05f, cx + w * 0.1f, cy - h * 0.04f)
                        close()
                    }
                    drawPath(grinPath, color = Color.White)
                    drawPath(grinPath, color = Color(0xFF212121), style = Stroke(3f))
                    // Red Sun God Vest
                    drawRect(color = Color(0xFFD50000), topLeft = Offset(cx - w * 0.22f, cy + h * 0.1f), size = Size(w * 0.44f, h * 0.35f))
                }

                "gojo" -> {
                    // Gojo Satoru: Iconic Black Blindfold, White Spiky Hair, Blue Infinity Aura
                    // White Spiky Hair
                    drawCircle(color = Color(0xFFF5F5F5), radius = w * 0.26f, center = Offset(cx, cy - h * 0.18f))
                    // Face
                    drawCircle(color = Color(0xFFFFE0B2), radius = w * 0.19f, center = Offset(cx, cy - h * 0.06f))
                    // Black Blindfold
                    drawRect(color = Color(0xFF111827), topLeft = Offset(cx - w * 0.22f, cy - h * 0.14f), size = Size(w * 0.44f, h * 0.14f))
                    // Infinity Blue Energy sparks
                    drawCircle(color = AnimeCyan, radius = 8f, center = Offset(cx - w * 0.1f, cy - h * 0.07f))
                    drawCircle(color = AnimeCyan, radius = 8f, center = Offset(cx + w * 0.1f, cy - h * 0.07f))
                    // Jujutsu High Dark Blue Uniform
                    drawRect(color = Color(0xFF0F172A), topLeft = Offset(cx - w * 0.28f, cy + h * 0.12f), size = Size(w * 0.56f, h * 0.35f))
                    // High Collar
                    drawRect(color = Color(0xFF1E293B), topLeft = Offset(cx - w * 0.15f, cy + h * 0.08f), size = Size(w * 0.3f, h * 0.1f))
                }

                "itachi" -> {
                    // Itachi: Akatsuki Black Cloak with Red Clouds, Forehead Protector, Sharingan
                    // Long black hair
                    drawCircle(color = Color(0xFF1A1A1A), radius = w * 0.27f, center = Offset(cx, cy - h * 0.16f))
                    // Face
                    drawCircle(color = Color(0xFFFFE0B2), radius = w * 0.18f, center = Offset(cx, cy - h * 0.06f))
                    // Konoha Rogue Headband
                    drawRect(color = Color(0xFF475569), topLeft = Offset(cx - w * 0.2f, cy - h * 0.22f), size = Size(w * 0.4f, h * 0.08f))
                    // Slash across symbol
                    drawLine(color = Color.Red, start = Offset(cx - w * 0.08f, cy - h * 0.2f), end = Offset(cx + w * 0.08f, cy - h * 0.16f), strokeWidth = 3f)
                    // Glowing Red Sharingan Eyes
                    drawCircle(color = Color(0xFFFF1744), radius = 10f, center = Offset(cx - w * 0.08f, cy - h * 0.06f))
                    drawCircle(color = Color(0xFFFF1744), radius = 10f, center = Offset(cx + w * 0.08f, cy - h * 0.06f))
                    // Stress Lines
                    drawLine(color = Color(0xFF5D4037), start = Offset(cx - w * 0.08f, cy - h * 0.02f), end = Offset(cx - w * 0.04f, cy + h * 0.04f), strokeWidth = 2.5f)
                    drawLine(color = Color(0xFF5D4037), start = Offset(cx + w * 0.08f, cy - h * 0.02f), end = Offset(cx + w * 0.04f, cy + h * 0.04f), strokeWidth = 2.5f)
                    // Akatsuki Cloak
                    drawRect(color = Color(0xFF0F172A), topLeft = Offset(cx - w * 0.3f, cy + h * 0.12f), size = Size(w * 0.6f, h * 0.35f))
                    // Red Cloud on Cloak
                    drawCircle(color = Color(0xFFD50000), radius = 16f, center = Offset(cx - w * 0.1f, cy + h * 0.22f))
                    drawCircle(color = Color(0xFFD50000), radius = 22f, center = Offset(cx, cy + h * 0.24f))
                    drawCircle(color = Color(0xFFD50000), radius = 16f, center = Offset(cx + w * 0.1f, cy + h * 0.22f))
                }

                "levi" -> {
                    // Levi Ackerman: Survey Corps Green Cloak, Cravat, Dual Blades
                    // Dark Hair with Undercut
                    drawCircle(color = Color(0xFF212121), radius = w * 0.24f, center = Offset(cx, cy - h * 0.17f))
                    // Face
                    drawCircle(color = Color(0xFFFFE0B2), radius = w * 0.17f, center = Offset(cx, cy - h * 0.06f))
                    // Sharp Narrow Eyes
                    drawRect(color = Color(0xFF37474F), topLeft = Offset(cx - w * 0.1f, cy - h * 0.08f), size = Size(w * 0.08f, 4f))
                    drawRect(color = Color(0xFF37474F), topLeft = Offset(cx + w * 0.02f, cy - h * 0.08f), size = Size(w * 0.08f, 4f))
                    // White Cravat
                    drawOval(color = Color.White, topLeft = Offset(cx - w * 0.06f, cy + h * 0.08f), size = Size(w * 0.12f, h * 0.1f))
                    // Survey Corps Green Cape
                    drawRect(color = Color(0xFF1B5E20), topLeft = Offset(cx - w * 0.3f, cy + h * 0.12f), size = Size(w * 0.6f, h * 0.35f))
                    // Dual Blades Silhouette
                    drawLine(color = Color(0xFFB0BEC5), start = Offset(cx - w * 0.28f, cy + h * 0.4f), end = Offset(cx - w * 0.4f, cy - h * 0.1f), strokeWidth = 8f)
                    drawLine(color = Color(0xFFB0BEC5), start = Offset(cx + w * 0.28f, cy + h * 0.4f), end = Offset(cx + w * 0.4f, cy - h * 0.1f), strokeWidth = 8f)
                }

                "goku_ui" -> {
                    // Goku Ultra Instinct: Spiky Silver Hair, Blue Divine Aura
                    // Spiky Silver Hair
                    val hairPath = Path().apply {
                        moveTo(cx - w * 0.3f, cy)
                        lineTo(cx - w * 0.38f, cy - h * 0.2f)
                        lineTo(cx - w * 0.15f, cy - h * 0.15f)
                        lineTo(cx - w * 0.1f, cy - h * 0.38f)
                        lineTo(cx, cy - h * 0.22f)
                        lineTo(cx + w * 0.15f, cy - h * 0.42f)
                        lineTo(cx + w * 0.22f, cy - h * 0.18f)
                        lineTo(cx + w * 0.38f, cy - h * 0.22f)
                        lineTo(cx + w * 0.3f, cy)
                        close()
                    }
                    drawPath(hairPath, color = Color(0xFFE0E0E0))
                    // Face
                    drawCircle(color = Color(0xFFFFCC80), radius = w * 0.18f, center = Offset(cx, cy - h * 0.05f))
                    // Silver Glowing Eyes
                    drawCircle(color = Color(0xFFECEFF1), radius = 8f, center = Offset(cx - w * 0.08f, cy - h * 0.05f))
                    drawCircle(color = Color(0xFFECEFF1), radius = 8f, center = Offset(cx + w * 0.08f, cy - h * 0.05f))
                    // Muscular Chest / Torn Gi
                    drawRect(color = Color(0xFF0D47A1), topLeft = Offset(cx - w * 0.26f, cy + h * 0.12f), size = Size(w * 0.52f, h * 0.35f))
                }

                "sukuna" -> {
                    // Sukuna: Curse Markings, Pink Hair, Malevolent Shrine Aura
                    // Pinkish spiky hair
                    drawCircle(color = Color(0xFFF06292), radius = w * 0.25f, center = Offset(cx, cy - h * 0.17f))
                    // Face
                    drawCircle(color = Color(0xFFFFCC80), radius = w * 0.18f, center = Offset(cx, cy - h * 0.06f))
                    // Black Curse Marks
                    drawLine(color = Color.Black, start = Offset(cx - w * 0.12f, cy - h * 0.12f), end = Offset(cx - w * 0.04f, cy - h * 0.02f), strokeWidth = 4f)
                    drawLine(color = Color.Black, start = Offset(cx + w * 0.12f, cy - h * 0.12f), end = Offset(cx + w * 0.04f, cy - h * 0.02f), strokeWidth = 4f)
                    // Forehead mark
                    drawCircle(color = Color.Black, radius = 6f, center = Offset(cx, cy - h * 0.16f))
                    // Piercing Crimson Eyes
                    drawCircle(color = Color(0xFFFF1744), radius = 8f, center = Offset(cx - w * 0.08f, cy - h * 0.06f))
                    drawCircle(color = Color(0xFFFF1744), radius = 8f, center = Offset(cx + w * 0.08f, cy - h * 0.06f))
                    // White Kimono
                    drawRect(color = Color(0xFFFAFAFA), topLeft = Offset(cx - w * 0.28f, cy + h * 0.12f), size = Size(w * 0.56f, h * 0.35f))
                }

                "zoro" -> {
                    // Zoro: Green Spiky Hair, Three Gold Earrings, Black Bandana
                    // Green Spiky Hair
                    drawCircle(color = Color(0xFF2E7D32), radius = w * 0.24f, center = Offset(cx, cy - h * 0.18f))
                    // Face
                    drawCircle(color = Color(0xFFFFCC80), radius = w * 0.18f, center = Offset(cx, cy - h * 0.06f))
                    // Scar over left eye
                    drawLine(color = Color(0xFFB71C1C), start = Offset(cx + w * 0.08f, cy - h * 0.1f), end = Offset(cx + w * 0.08f, cy - h * 0.02f), strokeWidth = 3f)
                    // Three Gold Earrings
                    drawCircle(color = AnimeGold, radius = 5f, center = Offset(cx - w * 0.19f, cy - h * 0.04f))
                    drawCircle(color = AnimeGold, radius = 5f, center = Offset(cx - w * 0.19f, cy - h * 0.01f))
                    drawCircle(color = AnimeGold, radius = 5f, center = Offset(cx - w * 0.19f, cy + h * 0.02f))
                    // Green Robe
                    drawRect(color = Color(0xFF1B5E20), topLeft = Offset(cx - w * 0.28f, cy + h * 0.12f), size = Size(w * 0.56f, h * 0.35f))
                }

                "saitama" -> {
                    // Saitama: Shiny Bald Head, Yellow Suit, White Cape
                    // Shiny Bald Head
                    drawCircle(color = Color(0xFFFFCC80), radius = w * 0.22f, center = Offset(cx, cy - h * 0.12f))
                    // Head shine
                    drawOval(color = Color.White.copy(alpha = 0.6f), topLeft = Offset(cx - w * 0.12f, cy - h * 0.22f), size = Size(w * 0.12f, h * 0.06f))
                    // Serious round eyes
                    drawCircle(color = Color.Black, radius = 5f, center = Offset(cx - w * 0.07f, cy - h * 0.1f))
                    drawCircle(color = Color.Black, radius = 5f, center = Offset(cx + w * 0.07f, cy - h * 0.1f))
                    // Yellow Hero Suit
                    drawRect(color = Color(0xFFFFD600), topLeft = Offset(cx - w * 0.26f, cy + h * 0.1f), size = Size(w * 0.52f, h * 0.35f))
                    // White Cape
                    drawRect(color = Color.White, topLeft = Offset(cx - w * 0.34f, cy + h * 0.08f), size = Size(w * 0.12f, h * 0.35f))
                    drawRect(color = Color.White, topLeft = Offset(cx + w * 0.22f, cy + h * 0.08f), size = Size(w * 0.12f, h * 0.35f))
                }

                "sung_jinwoo" -> {
                    // Sung Jin-Woo: Dark Hair, Glowing Purple Eyes, Shadow Monarch Aura
                    // Sleek Dark Hair
                    drawCircle(color = Color(0xFF0F172A), radius = w * 0.24f, center = Offset(cx, cy - h * 0.18f))
                    // Face
                    drawCircle(color = Color(0xFFFFE0B2), radius = w * 0.17f, center = Offset(cx, cy - h * 0.06f))
                    // Glowing Purple/Cyan Eyes
                    drawCircle(color = Color(0xFF00E5FF), radius = 8f, center = Offset(cx - w * 0.07f, cy - h * 0.06f))
                    drawCircle(color = Color(0xFF00E5FF), radius = 8f, center = Offset(cx + w * 0.07f, cy - h * 0.06f))
                    // Black Trench Coat
                    drawRect(color = Color(0xFF020617), topLeft = Offset(cx - w * 0.28f, cy + h * 0.1f), size = Size(w * 0.56f, h * 0.35f))
                    // Purple Shadow Energy
                    drawCircle(color = Color(0xFF7C4DFF).copy(alpha = 0.5f), radius = w * 0.4f, center = Offset(cx, cy + h * 0.2f))
                }

                else -> {
                    // Universal Heroic Anime Silhouette
                    drawCircle(color = primaryColor, radius = w * 0.24f, center = Offset(cx, cy - h * 0.16f))
                    drawCircle(color = Color(0xFFFFE0B2), radius = w * 0.18f, center = Offset(cx, cy - h * 0.06f))
                    drawCircle(color = AnimeGold, radius = 8f, center = Offset(cx - w * 0.07f, cy - h * 0.06f))
                    drawCircle(color = AnimeGold, radius = 8f, center = Offset(cx + w * 0.07f, cy - h * 0.06f))
                    drawRect(color = secondaryColor, topLeft = Offset(cx - w * 0.26f, cy + h * 0.12f), size = Size(w * 0.52f, h * 0.35f))
                }
            }
        }
    }
}
