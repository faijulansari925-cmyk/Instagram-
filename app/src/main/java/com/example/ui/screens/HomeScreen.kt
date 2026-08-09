package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ConversationEntity
import com.example.data.local.PostEntity
import com.example.data.local.StoryEntity
import com.example.ui.components.PostItem
import com.example.ui.components.StoryCircle
import com.example.ui.theme.InstaBlue

@Composable
fun HomeScreen(
    posts: List<PostEntity>,
    stories: List<StoryEntity>,
    conversations: List<ConversationEntity>,
    onStoryClick: (Int) -> Unit,
    onAddStoryClick: () -> Unit,
    onLikePost: (PostEntity) -> Unit,
    onCommentPost: (PostEntity) -> Unit,
    onSharePost: (PostEntity) -> Unit,
    onSavePost: (PostEntity) -> Unit,
    onOptionsPost: (PostEntity) -> Unit,
    onNotificationsClick: () -> Unit,
    onDirectMessagesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val unreadDms = conversations.sumOf { it.unreadCount }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Instagram Title
                Text(
                    text = "InstaGram",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                // Heart Activity Icon
                IconButton(
                    onClick = onNotificationsClick,
                    modifier = Modifier.testTag("nav_notifications_button")
                ) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = InstaBlue,
                                modifier = Modifier.size(8.dp)
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Direct Message Icon with Unread Badge
                IconButton(
                    onClick = onDirectMessagesClick,
                    modifier = Modifier.testTag("nav_dm_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (unreadDms > 0) {
                                Badge(
                                    containerColor = InstaBlue,
                                    contentColor = Color.White
                                ) {
                                    Text(text = unreadDms.toString(), fontSize = 10.sp)
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = "Direct Messages",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .testTag("home_feed_list")
        ) {
            // 1. Stories Tray
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 4.dp)
                    ) {
                        itemsIndexed(stories, key = { _, s -> s.id }) { index, story ->
                            StoryCircle(
                                username = story.username,
                                avatarUrl = story.userAvatar,
                                hasUnseen = story.hasUnseen,
                                isUserStory = story.isUser,
                                onClick = { onStoryClick(index) },
                                onAddStoryClick = if (story.isUser) onAddStoryClick else null
                            )
                        }
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        thickness = 0.8.dp
                    )
                }
            }

            // 2. Main Feed Posts
            items(posts, key = { it.id }) { post ->
                PostItem(
                    post = post,
                    onLikeClick = { onLikePost(post) },
                    onCommentClick = { onCommentPost(post) },
                    onShareClick = { onSharePost(post) },
                    onSaveClick = { onSavePost(post) },
                    onOptionsClick = { onOptionsPost(post) }
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    thickness = 0.8.dp
                )
            }
        }
    }
}
