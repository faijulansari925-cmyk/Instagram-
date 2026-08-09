package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ui.components.CommentsBottomSheet
import com.example.ui.components.PostOptionsMenu
import com.example.ui.components.ShareBottomSheet
import com.example.ui.screens.CreatePostScreen
import com.example.ui.screens.DirectMessagesScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ReelsScreen
import com.example.ui.screens.StoryViewerScreen
import com.example.ui.theme.InstaBlue
import com.example.ui.viewmodel.InstaViewModel

enum class BottomTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, "tab_home"),
    EXPLORE("Explore", Icons.Filled.Search, Icons.Outlined.Search, "tab_explore"),
    CREATE("Create", Icons.Filled.AddBox, Icons.Outlined.AddBox, "tab_create"),
    REELS("Reels", Icons.Filled.Movie, Icons.Outlined.Movie, "tab_reels"),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person, "tab_profile")
}

@Composable
fun InstaNavGraph(
    viewModel: InstaViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(BottomTab.HOME) }
    var currentSubScreen by remember { mutableStateOf<String?>(null) } // "DM", "NOTIFICATIONS"

    val posts by viewModel.posts.collectAsState()
    val stories by viewModel.stories.collectAsState()
    val reels by viewModel.reels.collectAsState()
    val conversations by viewModel.conversations.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    val activeCommentPostId by viewModel.activeCommentPostId.collectAsState()
    val activePostComments by viewModel.activePostComments.collectAsState()
    val selectedStoryIndex by viewModel.selectedStoryIndex.collectAsState()
    val activeConversationId by viewModel.activeConversationId.collectAsState()
    val activeMessages by viewModel.activeMessages.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val selectedPostForOptions by viewModel.selectedPostForOptions.collectAsState()

    var showShareSheetForPostId by remember { mutableStateOf<String?>(null) }

    // Fullscreen Story Viewer Overlay
    selectedStoryIndex?.let { index ->
        StoryViewerScreen(
            stories = stories,
            initialIndex = index,
            onClose = { viewModel.closeStoryViewer() },
            onSendMessage = { text ->
                if (index in stories.indices) {
                    val story = stories[index]
                    val conv = conversations.find { it.otherUserId == story.userId }
                    if (conv != null) {
                        viewModel.sendMessage(conv.id, text, null)
                    }
                }
            }
        )
        return
    }

    Scaffold(
        bottomBar = {
            if (currentSubScreen == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    BottomTab.entries.forEach { tab ->
                        val isSelected = selectedTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            icon = {
                                if (tab == BottomTab.PROFILE && userProfile != null) {
                                    AsyncImage(
                                        model = userProfile?.avatarUrl,
                                        contentDescription = "Profile tab avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = if (isSelected) 2.dp else 0.dp,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                                shape = CircleShape
                                            )
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.title,
                                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag(tab.testTag)
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                currentSubScreen == "DM" -> {
                    DirectMessagesScreen(
                        conversations = conversations,
                        activeConversationId = activeConversationId,
                        activeMessages = activeMessages,
                        onSelectConversation = { convId -> viewModel.openConversation(convId) },
                        onBackFromChat = { viewModel.closeConversation() },
                        onSendMessage = { convId, text, imageUri ->
                            viewModel.sendMessage(convId, text, imageUri)
                        },
                        onBack = { currentSubScreen = null }
                    )
                }

                currentSubScreen == "NOTIFICATIONS" -> {
                    NotificationsScreen(
                        notifications = notifications,
                        onToggleFollow = { notif -> viewModel.toggleNotificationFollow(notif) },
                        onBack = { currentSubScreen = null }
                    )
                }

                else -> {
                    when (selectedTab) {
                        BottomTab.HOME -> {
                            HomeScreen(
                                posts = posts,
                                stories = stories,
                                conversations = conversations,
                                onStoryClick = { index -> viewModel.openStoryViewer(index) },
                                onAddStoryClick = { selectedTab = BottomTab.CREATE },
                                onLikePost = { post -> viewModel.togglePostLike(post) },
                                onCommentPost = { post -> viewModel.openCommentsForPost(post.id) },
                                onSharePost = { post -> showShareSheetForPostId = post.id },
                                onSavePost = { post -> viewModel.togglePostSave(post) },
                                onOptionsPost = { post -> viewModel.openPostOptions(post) },
                                onNotificationsClick = { currentSubScreen = "NOTIFICATIONS" },
                                onDirectMessagesClick = { currentSubScreen = "DM" }
                            )
                        }

                        BottomTab.EXPLORE -> {
                            ExploreScreen(
                                posts = posts,
                                searchQuery = searchQuery,
                                onSearchQueryChange = { query -> viewModel.setSearchQuery(query) },
                                onLikePost = { post -> viewModel.togglePostLike(post) },
                                onCommentPost = { post -> viewModel.openCommentsForPost(post.id) },
                                onSharePost = { post -> showShareSheetForPostId = post.id },
                                onSavePost = { post -> viewModel.togglePostSave(post) },
                                onOptionsPost = { post -> viewModel.openPostOptions(post) }
                            )
                        }

                        BottomTab.CREATE -> {
                            CreatePostScreen(
                                onPublishPost = { mediaUrl, caption, location, isReel ->
                                    viewModel.createNewPost(mediaUrl, caption, location, isReel)
                                    selectedTab = if (isReel) BottomTab.REELS else BottomTab.HOME
                                },
                                onPublishStory = { mediaUrl ->
                                    viewModel.createStory(mediaUrl)
                                    selectedTab = BottomTab.HOME
                                }
                            )
                        }

                        BottomTab.REELS -> {
                            ReelsScreen(
                                reels = reels,
                                onLikeReel = { reel -> viewModel.toggleReelLike(reel) },
                                onCommentReel = { reel ->
                                    val post = posts.find { it.mediaUrl == reel.mediaUrl } ?: posts.firstOrNull()
                                    if (post != null) {
                                        viewModel.openCommentsForPost(post.id)
                                    }
                                },
                                onShareReel = { reel ->
                                    val post = posts.find { it.mediaUrl == reel.mediaUrl } ?: posts.firstOrNull()
                                    if (post != null) {
                                        showShareSheetForPostId = post.id
                                    }
                                },
                                onSaveReel = { reel -> viewModel.toggleReelSave(reel) },
                                onFollowReel = { reel -> viewModel.toggleReelFollow(reel) }
                            )
                        }

                        BottomTab.PROFILE -> {
                            ProfileScreen(
                                userProfile = userProfile,
                                userPosts = posts.filter { it.userId == "me" },
                                isDarkMode = isDarkMode,
                                onToggleDarkMode = { viewModel.toggleDarkMode() },
                                onUpdateProfile = { name, bio, site, avatar ->
                                    viewModel.updateUserProfile(name, bio, site, avatar)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Bottom Sheets
    activeCommentPostId?.let { postId ->
        CommentsBottomSheet(
            comments = activePostComments,
            userProfile = userProfile,
            onDismiss = { viewModel.closeComments() },
            onAddComment = { text -> viewModel.addComment(postId, text) },
            onLikeComment = { comment -> viewModel.toggleCommentLike(comment) }
        )
    }

    showShareSheetForPostId?.let { postId ->
        ShareBottomSheet(
            conversations = conversations,
            onDismiss = { showShareSheetForPostId = null },
            onSend = { targetConvIds ->
                val post = posts.find { it.id == postId }
                val shareText = "Check out this post by @${post?.username ?: "user"}: ${post?.mediaUrl}"
                targetConvIds.forEach { convId ->
                    viewModel.sendMessage(convId, shareText, null)
                }
            }
        )
    }

    selectedPostForOptions?.let { post ->
        PostOptionsMenu(
            post = post,
            onDismiss = { viewModel.closePostOptions() },
            onSaveClick = { viewModel.togglePostSave(post) }
        )
    }
}
