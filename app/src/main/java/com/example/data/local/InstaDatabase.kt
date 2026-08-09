package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PostEntity::class,
        StoryEntity::class,
        ReelEntity::class,
        CommentEntity::class,
        ConversationEntity::class,
        DirectMessageEntity::class,
        NotificationEntity::class,
        UserProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class InstaDatabase : RoomDatabase() {
    abstract fun instaDao(): InstaDao

    companion object {
        @Volatile
        private var INSTANCE: InstaDatabase? = null

        fun getDatabase(context: Context): InstaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InstaDatabase::class.java,
                    "insta_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
