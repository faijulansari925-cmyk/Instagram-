package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InstaDao {
    // Posts
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Query("UPDATE posts SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :postId")
    suspend fun updatePostLike(postId: String, isLiked: Boolean, likesCount: Int)

    @Query("UPDATE posts SET isSaved = :isSaved WHERE id = :postId")
    suspend fun updatePostSave(postId: String, isSaved: Boolean)

    @Query("UPDATE posts SET commentsCount = commentsCount + 1 WHERE id = :postId")
    suspend fun incrementCommentsCount(postId: String)

    // Stories
    @Query("SELECT * FROM stories ORDER BY isUser DESC, id ASC")
    fun getAllStories(): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: StoryEntity)

    @Query("UPDATE stories SET hasUnseen = 0 WHERE id = :storyId")
    suspend fun markStorySeen(storyId: String)

    // Reels
    @Query("SELECT * FROM reels ORDER BY id ASC")
    fun getAllReels(): Flow<List<ReelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReels(reels: List<ReelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReel(reel: ReelEntity)

    @Query("UPDATE reels SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :reelId")
    suspend fun updateReelLike(reelId: String, isLiked: Boolean, likesCount: Int)

    @Query("UPDATE reels SET isSaved = :isSaved WHERE id = :reelId")
    suspend fun updateReelSave(reelId: String, isSaved: Boolean)

    @Query("UPDATE reels SET isFollowing = :isFollowing WHERE userId = :userId")
    suspend fun updateReelFollow(userId: String, isFollowing: Boolean)

    // Comments
    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY id ASC")
    fun getCommentsForPost(postId: String): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Query("UPDATE comments SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :commentId")
    suspend fun updateCommentLike(commentId: String, isLiked: Boolean, likesCount: Int)

    // Direct Messages & Conversations
    @Query("SELECT * FROM conversations ORDER BY id ASC")
    fun getAllConversations(): Flow<List<ConversationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversations(conversations: List<ConversationEntity>)

    @Query("SELECT * FROM direct_messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun getMessagesForConversation(conversationId: String): Flow<List<DirectMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: DirectMessageEntity)

    @Query("UPDATE conversations SET lastMessage = :lastMessage, lastMessageTime = :time WHERE id = :conversationId")
    suspend fun updateLastMessage(conversationId: String, lastMessage: String, time: String)

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY id ASC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("UPDATE notifications SET isFollowingBack = :isFollowingBack WHERE id = :notificationId")
    suspend fun updateNotificationFollow(notificationId: String, isFollowingBack: Boolean)

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 'me'")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfileEntity)

    @Query("UPDATE user_profile SET fullName = :fullName, bio = :bio, website = :website, avatarUrl = :avatarUrl WHERE id = 'me'")
    suspend fun updateUserProfile(fullName: String, bio: String, website: String, avatarUrl: String)

    @Query("UPDATE user_profile SET postsCount = postsCount + 1 WHERE id = 'me'")
    suspend fun incrementUserPostsCount()
}
