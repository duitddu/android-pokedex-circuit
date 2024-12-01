package com.duitddu.study.circuitpokdex.presentation.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.Multibinds

@Module
@InstallIn(ActivityRetainedComponent::class)
public abstract class CircuitModule {
    @Multibinds
    public abstract fun presenterFactories(): Set<Presenter.Factory>

    @Multibinds
    public abstract fun uiFactories(): Set<Ui.Factory>

    public companion object {
        @[Provides ActivityRetainedScoped]
        public fun provideCircuit(
            presenterFactories: @JvmSuppressWildcards Set<Presenter.Factory>,
            uiFactories: @JvmSuppressWildcards Set<Ui.Factory>,
        ): Circuit = Circuit.Builder()
            .addPresenterFactories(presenterFactories)
            .addUiFactories(uiFactories)
            .build()
    }
}
