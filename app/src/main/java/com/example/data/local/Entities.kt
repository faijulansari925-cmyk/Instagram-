package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean = false,
    val location: String = "",
    val mediaUrl: String,
    val caption: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val timestamp: String,
    val isReel: Boolean = false
)

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val mediaUrl: String,
    val isUser: Boolean = false,
    val hasUnseen: Boolean = true,
    val timestamp: String
)

@Entity(tableName = "reels")
data class ReelEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val isVerified: Boolean = false,
    val isFollowing: Boolean = false,
    val caption: String,
    val audioTitle: String,
    val mediaUrl: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val sharesCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val userId: String,
    val username: String,
    val userAvatar: String,
    val text: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val timestamp: String
)

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey val id: String,
    val otherUserId: String,
    val otherUsername: String,
    val otherUserAvatar: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val note: String? = null
)

@Entity(tableName = "direct_messages")
data class DirectMessageEntity(
    @PrimaryKey val id: String,
    val conversationId: String,
    val senderId: String,
    val text: String,
    val imageUri: String? = null,
    val timestamp: String,
    val isMe: Boolean
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val type: String, // LIKE, COMMENT, FOLLOW, MENTION
    val actorUsername: String,
    val actorAvatar: String,
    val postImageUrl: String? = null,
    val timeAgo: String,
    val isFollowingBack: Boolean = false
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String = "me",
    val username: String,
    val fullName: String,
    val avatarUrl: String,
    val bio: String,
    val website: String,
    val postsCount: Int,
    val followersCount: Int,
    val followingCount: Int
)
