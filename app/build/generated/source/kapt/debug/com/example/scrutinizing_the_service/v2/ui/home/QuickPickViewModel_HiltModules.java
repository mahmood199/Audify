package com.example.scrutinizing_the_service.v2.ui.home;

import androidx.lifecycle.ViewModel;

import com.example.scrutinizing_the_service.v2.ui.home.quick_pick.QuickPickViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap;
import dagger.hilt.codegen.OriginatingElement;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;
import java.lang.String;

@OriginatingElement(
    topLevelClass = QuickPickViewModel.class
)
public final class QuickPickViewModel_HiltModules {
  private QuickPickViewModel_HiltModules() {
  }

  @Module
  @InstallIn(ViewModelComponent.class)
  public abstract static class BindsModule {
    private BindsModule() {
    }

    @Binds
    @IntoMap
    @StringKey("com.example.scrutinizing_the_service.v2.ui.landing.QuickPickViewModel")
    @HiltViewModelMap
    public abstract ViewModel binds(QuickPickViewModel vm);
  }

  @Module
  @InstallIn(ActivityRetainedComponent.class)
  public static final class KeyModule {
    private KeyModule() {
    }

    @Provides
    @IntoSet
    @HiltViewModelMap.KeySet
    public static String provide() {
      return "com.example.scrutinizing_the_service.v2.ui.landing.QuickPickViewModel";
    }
  }
}
