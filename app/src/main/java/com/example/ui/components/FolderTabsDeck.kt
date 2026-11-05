package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.InsertDriveFile
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScanCategory
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanBright
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.SeverityCritical
import com.example.ui.theme.SeverityLow

@Composable
fun FolderTabsDeck(
    activeCategory: ScanCategory,
    isScanning: Boolean,
    currentFilePath: String,
    categoryProgress: Int,
    totalFiles: Int,
    totalBugs: Int,
    fixedBugs: Int,
    onCategorySelected: (ScanCategory) -> Unit,
    onBugsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // List of tabs shown in the deck matching the video
    val categories = listOf(
        ScanCategory.RESULT,
        ScanCategory.MUSIC,
        ScanCategory.FILES,
        ScanCategory.VIDEOS
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .testTag("folder_tabs_deck")
    ) {
        // Stacked Tab Ears Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            categories.forEach { category ->
                val isSelected = activeCategory == category
                FolderTabEar(
                    category = category,
                    isSelected = isSelected,
                    onClick = { onCategorySelected(category) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Active Folder Card with stacked shadow layers behind it
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-1).dp)
        ) {
            // Layer 2 background depth shadow
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .height(176.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 8.dp)
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(22.dp))
                    .background(
                        color = Color(0x331E293B),
                        shape = RoundedCornerShape(22.dp)
                    )
            )

            // Layer 1 background depth card
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.97f)
                    .height(182.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 4.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF20376B), Color(0xFF142448))
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .border(
                        BorderStroke(1.dp, Color(0x22FFFFFF)),
                        RoundedCornerShape(22.dp)
                    )
            )

            // Main Active Front Folder Card
            Surface(
                shape = RoundedCornerShape(
                    topStart = if (activeCategory == ScanCategory.RESULT) 0.dp else 22.dp,
                    topEnd = 22.dp,
                    bottomStart = 22.dp,
                    bottomEnd = 22.dp
                ),
                color = Color.Transparent,
                shadowElevation = 14.dp,
                border = BorderStroke(1.dp, Color(0x40FFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = getCategoryGradient(activeCategory)
                        )
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                ) {
                    AnimatedContent(
                        targetState = activeCategory,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(220)) togetherWith
                                    fadeOut(animationSpec = tween(150))
                        },
                        label = "folder_content"
                    ) { category ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            if (category == ScanCategory.RESULT) {
                                // Result Card Content (Matches Video Frame 00:00 & 00:10)
                                ResultCardHeader(
                                    isClean = totalBugs == fixedBugs,
                                    onBugsClicked = onBugsClicked
                                )
                            } else {
                                // Scanning / Category Card Content (Matches Video Frame 00:02 - 00:08)
                                CategoryScanningHeader(
                                    category = category,
                                    isScanning = isScanning,
                                    currentFilePath = currentFilePath,
                                    categoryProgress = categoryProgress
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            // Subtle divider line
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color(0x26FFFFFF))
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // 3-Column Statistics Row (Total files | Total bugs | Fixed bugs)
                            StatsRow(
                                totalFiles = totalFiles,
                                totalBugs = totalBugs,
                                fixedBugs = fixedBugs,
                                onBugsClicked = onBugsClicked
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FolderTabEar(
    category: ScanCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val height by animateDpAsState(
        targetValue = if (isSelected) 34.dp else 28.dp,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "tab_height"
    )

    val backgroundBrush = if (isSelected) {
        getCategoryGradient(category)
    } else {
        Brush.verticalGradient(
            listOf(Color(0x6629478A), Color(0x551E3364))
        )
    }

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color(0xCCB0C4DE),
        label = "tab_text_color"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(backgroundBrush)
            .border(
                BorderStroke(1.dp, if (isSelected) Color(0x55FFFFFF) else Color(0x20FFFFFF)),
                RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
            .clickable(onClick = onClick)
            .testTag("folder_tab_${category.name.lowercase()}")
    ) {
        Text(
            text = category.displayName,
            fontFamily = PlusJakartaSansFamily,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp,
            color = textColor,
            maxLines = 1
        )
    }
}

@Composable
private fun ResultCardHeader(
    isClean: Boolean,
    onBugsClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Done",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (isClean) "Perangkat Anda terlindungi sepenuhnya" else "Ancaman siap dipindahkan ke karantina",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = Color(0xCCCFE2FE)
            )
        }

        // Pill Badge matching video ("100% 2021" / "100% SAFE")
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0x3300D2FF))
                .border(BorderStroke(1.dp, CyanBright.copy(alpha = 0.8f)), RoundedCornerShape(14.dp))
                .clickable(onClick = onBugsClicked)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = if (isClean) Icons.Rounded.VerifiedUser else Icons.Rounded.Security,
                    contentDescription = null,
                    tint = CyanBright,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = if (isClean) "100% SAFE" else "100% 2021",
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun CategoryScanningHeader(
    category: ScanCategory,
    isScanning: Boolean,
    currentFilePath: String,
    categoryProgress: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = getCategoryIcon(category),
                    contentDescription = null,
                    tint = CyanBright,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = if (isScanning) "Scanning..." else category.displayName,
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            // Percentage Text e.g. "56%", "100%"
            Text(
                text = "$categoryProgress%",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // File path text matching video (e.g. "D:/Users/Music/100.gif")
        Text(
            text = currentFilePath,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            color = Color(0xCCBFDBFE),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Mini Category Progress Bar
        LinearProgressIndicator(
            progress = { (categoryProgress / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = CyanBright,
            trackColor = Color(0x33FFFFFF),
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun StatsRow(
    totalFiles: Int,
    totalBugs: Int,
    fixedBugs: Int,
    onBugsClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Total files column
        Column {
            Text(
                text = "Total files",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = Color(0x99FFFFFF)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = totalFiles.toString(),
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        // Total bugs column (Clickable to inspect threats!)
        Column(
            modifier = Modifier.clickable(onClick = onBugsClicked)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Total bugs",
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    color = Color(0x99FFFFFF)
                )
                if (totalBugs > fixedBugs) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(SeverityCritical, CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = totalBugs.toString(),
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (totalBugs > fixedBugs) Color(0xFFFF8B8B) else Color.White
            )
        }

        // Fixed bugs column
        Column {
            Text(
                text = "Fixed bugs",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = Color(0x99FFFFFF)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = fixedBugs.toString(),
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = CyanBright
            )
        }
    }
}

private fun getCategoryGradient(category: ScanCategory): Brush {
    return when (category) {
        ScanCategory.RESULT -> Brush.verticalGradient(
            listOf(Color(0xFF244487), Color(0xFF172C59))
        )
        ScanCategory.MUSIC -> Brush.verticalGradient(
            listOf(Color(0xFF2E539E), Color(0xFF1F386E))
        )
        ScanCategory.FILES -> Brush.verticalGradient(
            listOf(Color(0xFF3861B3), Color(0xFF264482))
        )
        ScanCategory.VIDEOS -> Brush.verticalGradient(
            listOf(Color(0xFF436FC4), Color(0xFF2E4E94))
        )
        ScanCategory.APPS -> Brush.verticalGradient(
            listOf(Color(0xFF507DD8), Color(0xFF3759A4))
        )
    }
}

private fun getCategoryIcon(category: ScanCategory): ImageVector {
    return when (category) {
        ScanCategory.RESULT -> Icons.Rounded.VerifiedUser
        ScanCategory.MUSIC -> Icons.Rounded.MusicNote
        ScanCategory.FILES -> Icons.Rounded.Folder
        ScanCategory.VIDEOS -> Icons.Rounded.Movie
        ScanCategory.APPS -> Icons.AutoMirrored.Rounded.InsertDriveFile
    }
}
