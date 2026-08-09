package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.PostEntity
import com.example.ui.theme.InstaBlue
import com.example.ui.theme.InstaHeartRed
import kotlinx.coroutines.launch

@Composable
fun PostItem(
    post: PostEntity,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var showBigHeart by remember { mutableStateOf(false) }
    val bigHeartScale = remember { Animatable(0f) }

    var expandedCaption by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 12.dp)
            .testTag("post_item_${post.id}")
    ) {
        // 1. Post Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            AsyncImage(
                model = post.userAvatar,
                contentDescription = "${post.username} avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = post.username,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (post.isVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified account",
                            tint = InstaBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                if (post.location.isNotEmpty()) {
                    Text(
                        text = post.location,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onOptionsClick,
                modifier = Modifier.testTag("post_options_${post.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Post options",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // 2. Post Media Image with Double-Tap Heart Gesture
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .pointerInput(post.id) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (!post.isLiked) {
                                onLikeClick()
                            }
                            coroutineScope.launch {
                                showBigHeart = true
                                bigHeartScale.snapTo(0f)
                                bigHeartScale.animateTo(
                                    targetValue = 1.2f,
                                    animationSpec = spring(dampingRatio = 0.5f)
                                )
                                bigHeartScale.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring()
                                )
                                showBigHeart = false
                            }
                        }
                    )
                }
        ) {
            AsyncImage(
                model = post.mediaUrl,
                contentDescription = "Post image by ${post.username}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            // Animated Big Double-Tap Heart Overlay
            if (showBigHeart) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier
                        .size(100.dp)
                        .scale(bigHeartScale.value)
                )
            }
        }

        // 3. Post Actions Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onLikeClick,
                    modifier = Modifier.testTag("like_button_${post.id}")
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like post",
                        tint = if (post.isLiked) InstaHeartRed else MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(
                    onClick = onCommentClick,
                    modifier = Modifier.testTag("comment_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment on post",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier.testTag("share_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Send,
                        contentDescription = "Share post",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            IconButton(
                onClick = onSaveClick,
                modifier = Modifier.testTag("save_button_${post.id}")
            ) {
                Icon(
                    imageVector = if (post.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save post",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // 4. Likes Counter Text
        Text(
            text = "${formatNumber(post.likesCount)} likes",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 5. Caption with Username & Highlighted Hashtags
        val captionString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)) {
                append("${post.username} ")
            }
            append(if (expandedCaption || post.caption.length <= 80) post.caption else post.caption.take(80) + "... ")
        }

        Text(
            text = captionString,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .clickable { expandedCaption = !expandedCaption }
        )

        // 6. View All Comments CTA
        if (post.commentsCount > 0) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "View all ${post.commentsCount} comments",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable { onCommentClick() }
            )
        }

        // 7. Timestamp
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.timestamp.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

private fun formatNumber(count: Int): String {
    return when {
        count >= 1_000_000 -> String.format("%.1fM", count / 1_000_000.0)
        count >= 1_000 -> String.format("%,d", count)
        else -> count.toString()
    }
}
