package com.tvmaze.tvseriessearch

import com.tvmaze.tvseriessearch.data.source.SourceProvider
import com.tvmaze.tvseriessearch.data.source.network.TVMazeSourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MainModule {
    @Provides
    @Singleton
    fun provideSourceEndpoints() : SourceProvider {
        return TVMazeSourceProvider.buildProvider(SourceProvider::class.java)
    }
}