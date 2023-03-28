package com.miquido.android.navigation.di

import com.miquido.android.navigation.Navigation
import com.miquido.android.navigation.NavigationImpl
import com.miquido.android.navigation.Navigator
import com.miquido.android.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface NavigationModule {
    @Binds
    fun bindNavigation(impl: NavigationImpl): Navigation
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface NavigatorModule {
    @Binds
    fun bindNavigator(impl: NavigatorImpl): Navigator
}
