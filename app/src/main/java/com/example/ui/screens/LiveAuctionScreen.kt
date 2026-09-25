package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun LiveAuctionScreen(
    viewModel: AnimeGameViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.auctionState.collectAsState()
    val character = state.currentCharacter

    // Pulsing timer scale when under 5s
    val timerScale = remember { Animatable(1f) }
    LaunchedEffect(state.timeRemainingSeconds <= 5) {
        if (state.timeRemainingSeconds <= 5) {
            timerScale.animateTo(
                targetValue = 1.15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(400),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else {
            timerScale.snapTo(1f)
        }
    }

    // Visual Alert System: Flash overlay and screen shake on opponent/AI bid
    val flashAlpha = remember { Animatable(0f) }
    val shakeOffset = remember { Animatable(0f) }
    var showAlertBanner by remember { mutableStateOf(false) }

    LaunchedEffect(state.opponentBidAlertTrigger) {
        if (state.opponentBidAlertTrigger > 0L) {
            showAlertBanner = true
            launch {
                // Rapid screen vibration shake: left-right vibration
                shakeOffset.animateTo(-16f, tween(45))
                shakeOffset.animateTo(16f, tween(45))
                shakeOffset.animateTo(-12f, tween(45))
                shakeOffset.animateTo(12f, tween(45))
                shakeOffset.animateTo(-6f, tween(40))
                shakeOffset.animateTo(6f, tween(40))
                shakeOffset.animateTo(0f, tween(40))
            }
            launch {
                // Red/Amber warning flash across screen
                flashAlpha.animateTo(0.5f, tween(80))
                flashAlpha.animateTo(0f, tween(400))
                flashAlpha.animateTo(0.35f, tween(80))
                flashAlpha.animateTo(0f, tween(350))
            }
            delay(3500)
            showAlertBanner = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = shakeOffset.value.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF081020),
                            Color(0xFF0A152A),
                            Color(0xFF040810)
                        )
                    )
                )
        ) {
        // Top Header matching Screenshot 3
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(40.dp))

            Text(
                text = "المزايدة جارية",
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

        // Teams Scoreboard & Countdown matching Screenshot 3
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AnimeNavyCard.copy(alpha = 0.85f)),
            border = BorderStroke(1.dp, Color(0xFF22345C))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Player 1 (User)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF00B0FF),
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = state.userName,
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${state.userBudgetM}M بيري",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = AnimeCyan
                            )
                        }
                    }

                    // Center: Round Counter Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFB300),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${state.currentRound}/${state.totalRounds}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }

                    // Opponent / AI
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (showAlertBanner) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = AnimeRed,
                                        modifier = Modifier.padding(end = 4.dp)
                                    ) {
                                        Text(
                                            text = "زايد الآن!",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = state.opponentName,
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "${state.opponentBudgetM}M بيري",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = if (showAlertBanner) AnimeRed else Color(0xFFFF5252)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (showAlertBanner) AnimeRed else Color(0xFF00E676),
                            border = if (showAlertBanner) BorderStroke(2.dp, AnimeGold) else null,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (showAlertBanner) Icons.Default.Whatshot else Icons.Default.SmartToy,
                                    contentDescription = null,
                                    tint = if (showAlertBanner) AnimeGold else Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Countdown Timer Pill in Neon Green
                val formattedTime = String.format("%02d", state.timeRemainingSeconds)
                val isUrgent = state.timeRemainingSeconds <= 5
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Transparent,
                    border = BorderStroke(2.dp, if (isUrgent) AnimeRed else AnimeGreen),
                    modifier = Modifier.scale(timerScale.value)
                ) {
                    Text(
                        text = "00:$formattedTime",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = if (isUrgent) AnimeRed else AnimeGreen,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Center Stage: The Anime Trading Card
        val cardScrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(cardScrollState)
                .padding(horizontal = 20.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            if (character != null) {
                AnimeTradingCard(
                    character = character,
                    modifier = Modifier.fillMaxWidth(0.94f),
                    showLargeNumberBelow = true
                )
            }
        }

        // Bottom Bidding Controls Console matching Screenshot 3
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, bottom = 14.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = AnimeNavyDark.copy(alpha = 0.95f)),
            border = BorderStroke(1.5.dp, Color(0xFF1E2D4A))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Info / Banner status message
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF101B34),
                    border = BorderStroke(1.dp, Color(0xFF1E3A6E)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = AnimeCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = state.bannerStatusText,
                            fontSize = 11.sp,
                            color = Color(0xFFE2E8F0),
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Proposed Bid Adjuster with Minus and Plus
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Minus Button
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE53935),
                        modifier = Modifier
                            .size(46.dp)
                            .clickable { viewModel.adjustUserProposedBid(-1) }
                            .testTag("bid_minus_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "تقليل المزايدة",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Center Bid Display
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${state.userProposedBidM}M بيري",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = if (state.currentHighestBidM > 0)
                                "أعلى مزايدة: ${state.currentHighestBidM}M بيري (${if (state.currentHighestBidder == "USER") "لك" else "للخصم"})"
                            else "المزاد الافتتاحي جاهز",
                            fontSize = 10.sp,
                            color = AnimeGoldLight
                        )
                    }

                    // Plus Button
                    Surface(
                        shape = CircleShape,
                        color = AnimeGreen,
                        modifier = Modifier
                            .size(46.dp)
                            .clickable { viewModel.adjustUserProposedBid(1) }
                            .testTag("bid_plus_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "زيادة المزايدة",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // Quick Increment Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(1, 5, 10, 20).forEach { delta ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AnimeNavyMedium,
                            border = BorderStroke(1.dp, Color(0xFF334155)),
                            modifier = Modifier.clickable { viewModel.adjustUserProposedBid(delta) }
                        ) {
                            Text(
                                text = "+${delta}M",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AnimeGoldLight,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                // Main Action Buttons matching Screenshot 3: Confirm Bid vs Pass
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Green Confirm Bid Button
                    Button(
                        onClick = { viewModel.confirmUserBid() },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("confirm_bid_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AnimeGreen)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(0xFF003314),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "تأكيد المزايدة",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF003314)
                            )
                        }
                    }

                    // Red Pass Button
                    Button(
                        onClick = { viewModel.passCurrentCharacter() },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("pass_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AnimeRed)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FastForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "تخطي الشخصية",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    // Flashing Warning Edge Overlay
    if (flashAlpha.value > 0f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(BorderStroke((12 * (flashAlpha.value / 0.5f)).dp, AnimeRed))
                .background(AnimeRed.copy(alpha = flashAlpha.value * 0.25f))
        )
    }

    // Floating Alert Toast / Banner
    AnimatedVisibility(
        visible = showAlertBanner,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 58.dp, start = 16.dp, end = 16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFF991B1B),
            border = BorderStroke(2.dp, AnimeGold),
            shadowElevation = 16.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = AnimeGold,
                    modifier = Modifier.size(30.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "⚡ عرض مزايدة جديد من الخصم!",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = AnimeGoldLight
                    )
                    Text(
                        text = "${state.opponentName} رفع المزاد إلى ${state.lastOpponentBidAmountM}M بيري",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
}
