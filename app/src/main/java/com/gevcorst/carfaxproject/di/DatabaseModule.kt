package com.gevcorst.carfaxproject.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gevcorst.carfaxproject.model.Converters
import com.gevcorst.carfaxproject.model.Listings
import com.gevcorst.carfaxproject.repository.CarListRepository
import com.gevcorst.carfaxproject.repository.database.CarObjectDAO
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class CarListsRepositoryModule {
    @Binds
    abstract fun getTaskRepository(
        carListsRepository: CarListRepository
    ): CarListRepository
}

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideCarObjectDAO(database:AppDatabase):CarObjectDAO{
        return database.carObjectDao()
    }
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appcontext:Context):AppDatabase{
        return Room.databaseBuilder(appcontext,
            AppDatabase::class.java,
            "carfaxDatabase.db"
        ).build()
    }
}
@Database(entities = arrayOf(Listings::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carObjectDao(): CarObjectDAO
}