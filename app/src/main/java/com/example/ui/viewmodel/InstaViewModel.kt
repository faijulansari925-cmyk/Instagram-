package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.CommentEntity
import com.example.data.local.ConversationEntity
import com.example.data.local.DirectMessageEntity
import com.example.data.local.InstaDatabase
import com.example.data.local.NotificationEntity
import com.example.data.local.PostEntity
import com.example.data.local.ReelEntity
import com.example.data.local.StoryEntity
import com.example.data.local.UserProfileEntity
import com.example.data.repository.InstaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InstaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InstaRepository

    init {
        val dao = InstaDatabase.getDatabase(application).instaDao()
        repository = InstaRepository(dao)
    }

    val posts: StateFlow<List<PostEntity>> = repository.posts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val stories: StateFlow<List<StoryEntity>> = repository.stories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val reels: StateFlow<List<ReelEntity>> = repository.reels.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val conversations: StateFlow<List<ConversationEntity>> = repository.conversations.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val notifications: StateFlow<List<NotificationEntity>> = repository.notifications.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Comments bottom sheet state
    private val _activeCommentPostId = MutableStateFlow<String?>(null)
    val activeCommentPostId: StateFlow<String?> = _activeCommentPostId.asStateFlow()

    val activePostComments: StateFlow<List<CommentEntity>> = _activeCommentPostId
        .flatMapLatest { postId ->
            if (postId != null) repository.getCommentsForPost(postId) else flowOf(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Story viewer state
    private val _selectedStoryIndex = MutableStateFlow<Int?>(null)
    val selectedStoryIndex: StateFlow<Int?> = _selectedStoryIndex.asStateFlow()

    // Active conversation for Direct Message screen
    private val _activeConversationId = MutableStateFlow<String?>(null)
    val activeConversationId: StateFlow<String?> = _activeConversationId.asStateFlow()

    val activeMessages: StateFlow<List<DirectMessageEntity>> = _activeConversationId
        .flatMapLatest { convId ->
            if (convId != null) repository.getMessagesForConversation(convId) else flowOf(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Dark mode setting
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Post Options bottom sheet post
    private val _selectedPostForOptions = MutableStateFlow<PostEntity?>(null)
    val selectedPostForOptions: StateFlow<PostEntity?> = _selectedPostForOptions.asStateFlow()

    fun togglePostLike(post: PostEntity) {
        viewModelScope.launch {
            repository.togglePostLike(post)
        }
    }

    fun togglePostSave(post: PostEntity) {
        viewModelScope.launch {
            repository.togglePostSave(post)
        }
    }

    fun openCommentsForPost(postId: String) {
        _activeCommentPostId.value = postId
    }

    fun closeComments() {
        _activeCommentPostId.value = null
    }

    fun addComment(postId: String, text: String) {
        val currentProfile = userProfile.value ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.addComment(postId, text.trim(), currentProfile)
        }
    }

    fun toggleCommentLike(comment: CommentEntity) {
        viewModelScope.launch {
            repository.toggleCommentLike(comment)
        }
    }

    fun openStoryViewer(index: Int) {
        _selectedStoryIndex.value = index
        val storyList = stories.value
        if (index in storyList.indices) {
            viewModelScope.launch {
                repository.markStorySeen(storyList[index].id)
            }
        }
    }

    fun closeStoryViewer() {
        _selectedStoryIndex.value = null
    }

    fun toggleReelLike(reel: ReelEntity) {
        viewModelScope.launch {
            repository.toggleReelLike(reel)
        }
    }

    fun toggleReelSave(reel: ReelEntity) {
        viewModelScope.launch {
            repository.toggleReelSave(reel)
        }
    }

    fun toggleReelFollow(reel: ReelEntity) {
        viewModelScope.launch {
            repository.toggleReelFollow(reel)
        }
    }

    fun openConversation(conversationId: String) {
        _activeConversationId.value = conversationId
    }

    fun closeConversation() {
        _activeConversationId.value = null
    }

    fun sendMessage(conversationId: String, text: String, imageUri: String? = null) {
        if (text.isBlank() && imageUri == null) return
        viewModelScope.launch {
            repository.sendMessage(conversationId, text.trim(), imageUri)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun createNewPost(mediaUrl: String, caption: String, location: String, isReel: Boolean) {
        val profile = userProfile.value ?: return
        viewModelScope.launch {
            repository.createNewPost(mediaUrl, caption, location, isReel, profile)
        }
    }

    fun createStory(mediaUrl: String) {
        val profile = userProfile.value ?: return
        viewModelScope.launch {
            repository.createStory(mediaUrl, profile)
        }
    }

    fun toggleNotificationFollow(notification: NotificationEntity) {
        viewModelScope.launch {
            repository.toggleNotificationFollow(notification)
        }
    }

    fun updateUserProfile(fullName: String, bio: String, website: String, avatarUrl: String) {
        viewModelScope.launch {
            repository.updateUserProfile(fullName, bio, website, avatarUrl)
        }
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun openPostOptions(post: PostEntity) {
        _selectedPostForOptions.value = post
    }

    fun closePostOptions() {
        _selectedPostForOptions.value = null
    }
}
