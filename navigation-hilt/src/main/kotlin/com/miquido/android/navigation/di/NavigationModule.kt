package com.miquido.android.navigation.di

import androidx.lifecycle.SavedStateHandle
import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.getNavEntryId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object NavigationModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNavigation(): Navigation = Navigation.Default()
}

@Module
@InstallIn(ViewModelComponent::class)
internal object NavigatorModule {
    @Provides
    @ViewModelScoped
    fun provideNavigator(
        navigation: Navigation,
        savedStateHandle: SavedStateHandle
    ): Navigator = Navigator.Default(navigation, savedStateHandle.getNavEntryId())
}
