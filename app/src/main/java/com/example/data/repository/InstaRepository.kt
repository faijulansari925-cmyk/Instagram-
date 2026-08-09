package com.example.data.repository

import com.example.data.SampleData
import com.example.data.local.CommentEntity
import com.example.data.local.DirectMessageEntity
import com.example.data.local.InstaDao
import com.example.data.local.NotificationEntity
import com.example.data.local.PostEntity
import com.example.data.local.ReelEntity
import com.example.data.local.StoryEntity
import com.example.data.local.UserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class InstaRepository(private val instaDao: InstaDao) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            seedSampleDataIfNeeded()
        }
    }

    private suspend fun seedSampleDataIfNeeded() {
        val existingProfile = instaDao.getUserProfile().firstOrNull()
        if (existingProfile == null) {
            instaDao.insertUserProfile(SampleData.sampleUserProfile)
            instaDao.insertPosts(SampleData.samplePosts)
            instaDao.insertStories(SampleData.sampleStories)
            instaDao.insertReels(SampleData.sampleReels)
            instaDao.insertComments(SampleData.sampleComments)
            instaDao.insertConversations(SampleData.sampleConversations)
            for (msg in SampleData.sampleMessages) {
                instaDao.insertMessage(msg)
            }
            instaDao.insertNotifications(SampleData.sampleNotifications)
        }
    }

    val posts: Flow<List<PostEntity>> = instaDao.getAllPosts()
    val stories: Flow<List<StoryEntity>> = instaDao.getAllStories()
    val reels: Flow<List<ReelEntity>> = instaDao.getAllReels()
    val conversations = instaDao.getAllConversations()
    val notifications: Flow<List<NotificationEntity>> = instaDao.getAllNotifications()
    val userProfile: Flow<UserProfileEntity?> = instaDao.getUserProfile()

    suspend fun togglePostLike(post: PostEntity) {
        val newIsLiked = !post.isLiked
        val newLikesCount = if (newIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        instaDao.updatePostLike(post.id, newIsLiked, newLikesCount)
    }

    suspend fun togglePostSave(post: PostEntity) {
        instaDao.updatePostSave(post.id, !post.isSaved)
    }

    suspend fun markStorySeen(storyId: String) {
        instaDao.markStorySeen(storyId)
    }

    suspend fun toggleReelLike(reel: ReelEntity) {
        val newIsLiked = !reel.isLiked
        val newLikesCount = if (newIsLiked) reel.likesCount + 1 else (reel.likesCount - 1).coerceAtLeast(0)
        instaDao.updateReelLike(reel.id, newIsLiked, newLikesCount)
    }

    suspend fun toggleReelSave(reel: ReelEntity) {
        instaDao.updateReelSave(reel.id, !reel.isSaved)
    }

    suspend fun toggleReelFollow(reel: ReelEntity) {
        instaDao.updateReelFollow(reel.userId, !reel.isFollowing)
    }

    fun getCommentsForPost(postId: String): Flow<List<CommentEntity>> {
        return instaDao.getCommentsForPost(postId)
    }

    suspend fun addComment(postId: String, text: String, userProfile: UserProfileEntity) {
        val comment = CommentEntity(
            id = "c_${System.currentTimeMillis()}",
            postId = postId,
            userId = userProfile.id,
            username = userProfile.username,
            userAvatar = userProfile.avatarUrl,
            text = text,
            likesCount = 0,
            isLiked = false,
            timestamp = "Just now"
        )
        instaDao.insertComment(comment)
        instaDao.incrementCommentsCount(postId)
    }

    suspend fun toggleCommentLike(comment: CommentEntity) {
        val newIsLiked = !comment.isLiked
        val newLikesCount = if (newIsLiked) comment.likesCount + 1 else (comment.likesCount - 1).coerceAtLeast(0)
        instaDao.updateCommentLike(comment.id, newIsLiked, newLikesCount)
    }

    fun getMessagesForConversation(conversationId: String): Flow<List<DirectMessageEntity>> {
        return instaDao.getMessagesForConversation(conversationId)
    }

    suspend fun sendMessage(conversationId: String, text: String, imageUri: String? = null) {
        val msg = DirectMessageEntity(
            id = "m_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderId = "me",
            text = text,
            imageUri = imageUri,
            timestamp = "Just now",
            isMe = true
        )
        instaDao.insertMessage(msg)
        val displayMsg = if (imageUri != null) "📷 Photo" else text
        instaDao.updateLastMessage(conversationId, displayMsg, "Just now")
    }

    suspend fun createNewPost(
        mediaUrl: String,
        caption: String,
        location: String,
        isReel: Boolean,
        userProfile: UserProfileEntity
    ) {
        val newId = "post_${System.currentTimeMillis()}"
        val post = PostEntity(
            id = newId,
            userId = userProfile.id,
            username = userProfile.username,
            userAvatar = userProfile.avatarUrl,
            isVerified = true,
            location = location,
            mediaUrl = mediaUrl,
            caption = caption,
            likesCount = 0,
            commentsCount = 0,
            isLiked = false,
            isSaved = false,
            timestamp = "Just now",
            isReel = isReel
        )
        instaDao.insertPost(post)
        instaDao.incrementUserPostsCount()

        if (isReel) {
            val reel = ReelEntity(
                id = "reel_${System.currentTimeMillis()}",
                userId = userProfile.id,
                username = userProfile.username,
                userAvatar = userProfile.avatarUrl,
                isVerified = true,
                isFollowing = true,
                caption = caption,
                audioTitle = "original audio - ${userProfile.username}",
                mediaUrl = mediaUrl,
                likesCount = 0,
                commentsCount = 0,
                sharesCount = 0,
                isLiked = false,
                isSaved = false
            )
            instaDao.insertReel(reel)
        }
    }

    suspend fun createStory(mediaUrl: String, userProfile: UserProfileEntity) {
        val story = StoryEntity(
            id = "story_user_${System.currentTimeMillis()}",
            userId = userProfile.id,
            username = "Your story",
            userAvatar = userProfile.avatarUrl,
            mediaUrl = mediaUrl,
            isUser = true,
            hasUnseen = false,
            timestamp = "Just now"
        )
        instaDao.insertStory(story)
    }

    suspend fun toggleNotificationFollow(notification: NotificationEntity) {
        instaDao.updateNotificationFollow(notification.id, !notification.isFollowingBack)
    }

    suspend fun updateUserProfile(fullName: String, bio: String, website: String, avatarUrl: String) {
        instaDao.updateUserProfile(fullName, bio, website, avatarUrl)
    }
}
