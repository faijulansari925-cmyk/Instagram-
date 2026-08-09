package com.example.data

import com.example.data.local.CommentEntity
import com.example.data.local.ConversationEntity
import com.example.data.local.DirectMessageEntity
import com.example.data.local.NotificationEntity
import com.example.data.local.PostEntity
import com.example.data.local.ReelEntity
import com.example.data.local.StoryEntity
import com.example.data.local.UserProfileEntity

object SampleData {

    val sampleUserProfile = UserProfileEntity(
        id = "me",
        username = "alex_creative",
        fullName = "Alex Rivera",
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=500&q=80",
        bio = "✨ Digital Creator & Mobile Developer 🚀\n📸 Capturing moments & building beautiful apps\n📍 San Francisco, CA | 🌐 alexrivera.dev",
        website = "https://alexrivera.dev",
        postsCount = 18,
        followersCount = 4250,
        followingCount = 380
    )

    val sampleStories = listOf(
        StoryEntity(
            id = "story_me",
            userId = "me",
            username = "Your story",
            userAvatar = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=500&q=80",
            mediaUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80",
            isUser = true,
            hasUnseen = false,
            timestamp = "Just now"
        ),
        StoryEntity(
            id = "story_1",
            userId = "u1",
            username = "sophia_art",
            userAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=500&q=80",
            mediaUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?auto=format&fit=crop&w=800&q=80",
            hasUnseen = true,
            timestamp = "2h ago"
        ),
        StoryEntity(
            id = "story_2",
            userId = "u2",
            username = "liam_tech",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=500&q=80",
            mediaUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=800&q=80",
            hasUnseen = true,
            timestamp = "4h ago"
        ),
        StoryEntity(
            id = "story_3",
            userId = "u3",
            username = "emma_travel",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=500&q=80",
            mediaUrl = "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?auto=format&fit=crop&w=800&q=80",
            hasUnseen = true,
            timestamp = "6h ago"
        ),
        StoryEntity(
            id = "story_4",
            userId = "u4",
            username = "noah_fits",
            userAvatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=500&q=80",
            mediaUrl = "https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=800&q=80",
            hasUnseen = false,
            timestamp = "8h ago"
        ),
        StoryEntity(
            id = "story_5",
            userId = "u5",
            username = "ava_foodie",
            userAvatar = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?auto=format&fit=crop&w=500&q=80",
            mediaUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=800&q=80",
            hasUnseen = false,
            timestamp = "12h ago"
        )
    )

    val samplePosts = listOf(
        PostEntity(
            id = "post_1",
            userId = "u1",
            username = "sophia_art",
            userAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=500&q=80",
            isVerified = true,
            location = "Florence, Italy",
            mediaUrl = "https://images.unsplash.com/photo-1541872703-74c5e44368f9?auto=format&fit=crop&w=1000&q=80",
            caption = "Golden hour lights hitting the historic cobblestone streets of Florence. 🇮🇹✨ Nothing compares to Italian architecture in autumn. #travelgram #florence #italy #photography #art",
            likesCount = 1420,
            commentsCount = 89,
            isLiked = true,
            isSaved = false,
            timestamp = "2 hours ago"
        ),
        PostEntity(
            id = "post_2",
            userId = "u2",
            username = "liam_tech",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=500&q=80",
            isVerified = false,
            location = "Silicon Valley, CA",
            mediaUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=1000&q=80",
            caption = "Late night coding session with Jetpack Compose & Kotlin! 💻🔥 Loving how clean reactive UI state feels with StateFlow. What are you building this week? #androiddev #developer #kotlin #coder",
            likesCount = 856,
            commentsCount = 42,
            isLiked = false,
            isSaved = true,
            timestamp = "4 hours ago"
        ),
        PostEntity(
            id = "post_3",
            userId = "u3",
            username = "emma_travel",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=500&q=80",
            isVerified = true,
            location = "Kyoto, Japan",
            mediaUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=1000&q=80",
            caption = "Peaceful morning walk through Arashiyama Bamboo Grove in Kyoto. 🎍🍵 The sound of rustling bamboo in the soft wind is pure magic. #japan #kyoto #bamboo #serenity #wanderlust",
            likesCount = 3890,
            commentsCount = 210,
            isLiked = false,
            isSaved = false,
            timestamp = "6 hours ago"
        ),
        PostEntity(
            id = "post_4",
            userId = "u5",
            username = "ava_foodie",
            userAvatar = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?auto=format&fit=crop&w=500&q=80",
            isVerified = false,
            location = "Tokyo Artisan Ramen",
            mediaUrl = "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&w=1000&q=80",
            caption = "Rich 18-hour Tonkotsu ramen with handmade noodles and melt-in-your-mouth chashu pork! 🍜🤤 Tag someone who needs ramen right now. #ramen #japanesefood #foodie #tokyo #delicious",
            likesCount = 2150,
            commentsCount = 114,
            isLiked = true,
            isSaved = true,
            timestamp = "9 hours ago"
        ),
        PostEntity(
            id = "post_5",
            userId = "me",
            username = "alex_creative",
            userAvatar = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=500&q=80",
            isVerified = true,
            location = "San Francisco, CA",
            mediaUrl = "https://images.unsplash.com/photo-1506146332389-18140dc7b2fb?auto=format&fit=crop&w=1000&q=80",
            caption = "Golden Gate Bridge wrapped in moody afternoon coastal fog. 🌉🌫️ San Francisco never ceases to amaze me with its light. #sanfrancisco #goldengate #california #cinematic #view",
            likesCount = 1920,
            commentsCount = 76,
            isLiked = true,
            isSaved = false,
            timestamp = "1 day ago"
        )
    )

    val sampleReels = listOf(
        ReelEntity(
            id = "reel_1",
            userId = "u1",
            username = "sophia_art",
            userAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=500&q=80",
            isVerified = true,
            isFollowing = true,
            caption = "Oil painting process: Creating an ethereal sunset landscape in 60 seconds! 🎨🖌️ #art #painting #artist #satisfying",
            audioTitle = "original sound - sophia_art • Ethereal Vibes",
            mediaUrl = "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?auto=format&fit=crop&w=800&q=1200",
            likesCount = 42100,
            commentsCount = 1840,
            sharesCount = 3900,
            isLiked = true,
            isSaved = false
        ),
        ReelEntity(
            id = "reel_2",
            userId = "u2",
            username = "liam_tech",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=500&q=80",
            isVerified = false,
            isFollowing = false,
            caption = "5 Kotlin extension functions every Android dev MUST know in 2026! 🚀⚡ #android #kotlin #coding #programming",
            audioTitle = "trending sound - Tech Beats • Lofi Study",
            mediaUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?auto=format&fit=crop&w=800&q=1200",
            likesCount = 18900,
            commentsCount = 420,
            sharesCount = 1200,
            isLiked = false,
            isSaved = true
        ),
        ReelEntity(
            id = "reel_3",
            userId = "u3",
            username = "emma_travel",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=500&q=80",
            isVerified = true,
            isFollowing = true,
            caption = "Top 3 hidden gems in Switzerland you need to visit before you die! 🏔️🇨🇭 #switzerland #travelreels #nature #alps",
            audioTitle = "Alpine Wanderer - Wanderlust Melodies",
            mediaUrl = "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?auto=format&fit=crop&w=800&q=1200",
            likesCount = 89400,
            commentsCount = 3120,
            sharesCount = 15200,
            isLiked = true,
            isSaved = true
        )
    )

    val sampleComments = listOf(
        CommentEntity(
            id = "c1",
            postId = "post_1",
            userId = "u2",
            username = "liam_tech",
            userAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=500&q=80",
            text = "Incredible colors! What lens did you shoot this on? 📸",
            likesCount = 12,
            isLiked = false,
            timestamp = "1h ago"
        ),
        CommentEntity(
            id = "c2",
            postId = "post_1",
            userId = "u3",
            username = "emma_travel",
            userAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=500&q=80",
            text = "Florence is magical! Make sure to grab gelato at Gelateria dei Neri! 🍦❤️",
            likesCount = 28,
            isLiked = true,
            timestamp = "1h ago"
        ),
        CommentEntity(
            id = "c3",
            postId = "post_1",
            userId = "u4",
            username = "noah_fits",
            userAvatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=500&q=80",
            text = "Stunning shot! The framing is top tier 🔥",
            likesCount = 5,
            isLiked = false,
            timestamp = "30m ago"
        )
    )

    val sampleConversations = listOf(
        ConversationEntity(
            id = "conv_1",
            otherUserId = "u1",
            otherUsername = "sophia_art",
            otherUserAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=500&q=80",
            lastMessage = "Thanks Alex! I'll send you the high-res render tomorrow 😊",
            lastMessageTime = "2m",
            unreadCount = 1,
            note = "Working on new art exhibition! 🎨"
        ),
        ConversationEntity(
            id = "conv_2",
            otherUserId = "u2",
            otherUsername = "liam_tech",
            otherUserAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=500&q=80",
            lastMessage = "Did you check out the new Room KSP release? So much faster!",
            lastMessageTime = "1h",
            unreadCount = 0,
            note = "Building Compose apps 🚀"
        ),
        ConversationEntity(
            id = "conv_3",
            otherUserId = "u3",
            otherUsername = "emma_travel",
            otherUserAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=500&q=80",
            lastMessage = "Sent a video reel to your DM",
            lastMessageTime = "3h",
            unreadCount = 0,
            note = "Kyoto travel guide coming soon ✈️"
        ),
        ConversationEntity(
            id = "conv_4",
            otherUserId = "u5",
            otherUsername = "ava_foodie",
            otherUserAvatar = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?auto=format&fit=crop&w=500&q=80",
            lastMessage = "We definitely need to try that ramen place!",
            lastMessageTime = "1d",
            unreadCount = 0,
            note = "Food tours everywhere 🍜"
        )
    )

    val sampleMessages = listOf(
        DirectMessageEntity(
            id = "m1",
            conversationId = "conv_1",
            senderId = "u1",
            text = "Hey Alex! Loved your latest photo post of the Golden Gate Bridge!",
            timestamp = "10:14 AM",
            isMe = false
        ),
        DirectMessageEntity(
            id = "m2",
            conversationId = "conv_1",
            senderId = "me",
            text = "Thank you so much Sophia! The lighting was perfect yesterday afternoon.",
            timestamp = "10:16 AM",
            isMe = true
        ),
        DirectMessageEntity(
            id = "m3",
            conversationId = "conv_1",
            senderId = "u1",
            text = "Thanks Alex! I'll send you the high-res render tomorrow 😊",
            timestamp = "10:20 AM",
            isMe = false
        )
    )

    val sampleNotifications = listOf(
        NotificationEntity(
            id = "n1",
            type = "LIKE",
            actorUsername = "sophia_art",
            actorAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=500&q=80",
            postImageUrl = "https://images.unsplash.com/photo-1506146332389-18140dc7b2fb?auto=format&fit=crop&w=500&q=80",
            timeAgo = "15m"
        ),
        NotificationEntity(
            id = "n2",
            type = "COMMENT",
            actorUsername = "liam_tech",
            actorAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=500&q=80",
            postImageUrl = "https://images.unsplash.com/photo-1506146332389-18140dc7b2fb?auto=format&fit=crop&w=500&q=80",
            timeAgo = "1h"
        ),
        NotificationEntity(
            id = "n3",
            type = "FOLLOW",
            actorUsername = "marcus_design",
            actorAvatar = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?auto=format&fit=crop&w=500&q=80",
            timeAgo = "3h",
            isFollowingBack = false
        ),
        NotificationEntity(
            id = "n4",
            type = "MENTION",
            actorUsername = "emma_travel",
            actorAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=500&q=80",
            postImageUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=500&q=80",
            timeAgo = "5h"
        ),
        NotificationEntity(
            id = "n5",
            type = "FOLLOW",
            actorUsername = "chloe_style",
            actorAvatar = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=500&q=80",
            timeAgo = "1d",
            isFollowingBack = true
        )
    )
}
