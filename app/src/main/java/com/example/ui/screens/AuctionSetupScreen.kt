package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnimeCategory
import com.example.model.AuctionType
import com.example.model.CardRuleType
import com.example.ui.theme.AnimeCyan
import com.example.ui.theme.AnimeGold
import com.example.ui.theme.AnimeGoldLight
import com.example.ui.theme.AnimeGreen
import com.example.ui.theme.AnimeNavyCard
import com.example.ui.theme.AnimeNavyDark
import com.example.ui.theme.AnimeNavyMedium
import com.example.viewmodel.AnimeGameViewModel
import com.example.viewmodel.AppScreen

@Composable
fun AuctionSetupScreen(
    viewModel: AnimeGameViewModel,
    modifier: Modifier = Modifier
) {
    val auctionType by viewModel.auctionType.collectAsState()
    val cardRule by viewModel.cardRule.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        AnimeNavyMedium,
                        AnimeNavyDark,
                        Color(0xFF050812)
                    )
                )
            )
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(42.dp))

            Text(
                text = "إعداد المزاد",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            Surface(
                shape = CircleShape,
                color = AnimeNavyCard,
                border = BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier
                    .size(42.dp)
                    .clickable { viewModel.navigateTo(AppScreen.HOME) }
                    .testTag("setup_back_button")
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

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(bottom = 16.dp)
        ) {
            // Section 1: نوع المزاد (Auction Format)
            Text(
                text = "نوع المزاد",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AuctionType.values().forEach { type ->
                    val isSelected = auctionType == type
                    SelectableOptionCard(
                        title = type.titleAr,
                        subtitle = "${type.slots} لاعبين • ${type.initialBudgetM}M بيري",
                        isSelected = isSelected,
                        onClick = { viewModel.setAuctionType(type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section 2: اختر الكارت (Rule / Modifier)
            Text(
                text = "اختر الكارت",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CardRuleType.values().forEach { rule ->
                    val isSelected = cardRule == rule
                    SelectableOptionCard(
                        title = rule.titleAr,
                        subtitle = rule.descAr,
                        isSelected = isSelected,
                        onClick = { viewModel.setCardRule(rule) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section 3: اختر لاعبي المزاد (Universe Pool)
            Text(
                text = "اختر شخصيات المزاد",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AnimeCategory.values().forEach { category ->
                    val isSelected = selectedCategory == category
                    SelectableUniverseCard(
                        category = category,
                        isSelected = isSelected,
                        onClick = { viewModel.setCategory(category) }
                    )
                }
            }
        }

        // Bottom Action Buttons matching Screenshot 2
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF060A14))
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Play 2-Player Button (Blue)
            Button(
                onClick = { viewModel.startAuction(versusAi = false) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("play_local_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "العب (لاعب ضد لاعب)",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Challenge AI Button (Green)
            Button(
                onClick = { viewModel.startAuction(versusAi = true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("challenge_ai_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AnimeGreen)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Memory,
                        contentDescription = null,
                        tint = Color(0xFF003314),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تحدّي الـ AI",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF003314)
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableOptionCard(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            if (isSelected) 2.5.dp else 1.dp,
            if (isSelected) AnimeGold else Color(0xFF1E293B)
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AnimeNavyCard else AnimeNavyDark
        )
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            if (isSelected) {
                Surface(
                    shape = CircleShape,
                    color = AnimeGold,
                    modifier = Modifier
                        .size(22.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "تم الاختيار",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) AnimeGoldLight else Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center,
                    lineHeight = 13.sp
                )
            }
        }
    }
}

@Composable
private fun SelectableUniverseCard(
    category: AnimeCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(135.dp)
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            if (isSelected) 2.5.dp else 1.dp,
            if (isSelected) AnimeGold else Color(0xFF1E293B)
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AnimeNavyCard else AnimeNavyDark
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Gradient fill
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                if (isSelected) AnimeGold.copy(alpha = 0.25f) else AnimeNavyMedium,
                                AnimeNavyDark
                            )
                        )
                    )
            )

            if (isSelected) {
                Surface(
                    shape = CircleShape,
                    color = AnimeGold,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(22.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "تم الاختيار",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (isSelected) AnimeGold.copy(alpha = 0.2f) else Color(0xFF1E293B),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (isSelected) AnimeGold else AnimeCyan,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = category.titleAr,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) AnimeGoldLight else Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = category.subtitleAr,
                    fontSize = 9.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center,
                    lineHeight = 11.sp
                )
            }
        }
    }
}
