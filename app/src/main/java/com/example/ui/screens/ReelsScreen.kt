package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.example.data.local.ReelEntity
import com.example.ui.components.ReelItem

@Composable
fun ReelsScreen(
    reels: List<ReelEntity>,
    onLikeReel: (ReelEntity) -> Unit,
    onCommentReel: (ReelEntity) -> Unit,
    onShareReel: (ReelEntity) -> Unit,
    onSaveReel: (ReelEntity) -> Unit,
    onFollowReel: (ReelEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("reels_screen")
    ) {
        if (reels.isEmpty()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "No Reels available", color = Color.White)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(reels, key = { it.id }) { reel ->
                    Box(modifier = Modifier.fillParentMaxSize()) {
                        ReelItem(
                            reel = reel,
                            onLikeClick = { onLikeReel(reel) },
                            onCommentClick = { onCommentReel(reel) },
                            onShareClick = { onShareReel(reel) },
                            onSaveClick = { onSaveReel(reel) },
                            onFollowClick = { onFollowReel(reel) }
                        )
                    }
                }
            }
        }
    }
}
