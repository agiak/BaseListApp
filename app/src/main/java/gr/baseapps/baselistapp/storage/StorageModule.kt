package gr.baseapps.baselistapp.storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.baseapps.baselistapp.ui.Consts.MYAPP_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MyDatabase::class.java,
        MYAPP_DATABASE_NAME
    )
        .addCallback(object: RoomDatabase.Callback() {
            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {}
        })
        .fallbackToDestructiveMigration()
        .build()
}
