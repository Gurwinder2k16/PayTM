package com.example.daggerexample.di

import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.paytm.di.keys.ViewModelKey
import com.assignment.paytm.di.module.provides.ViewModelFactory
import com.assignment.paytm.module.fragments.viewmodels.DetailViewModel
import com.assignment.paytm.module.fragments.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal abstract class InjectionModules {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MainViewModel.class as key,
     * and a Provider that will build a MainViewModel
     * object.
     *
     * */

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun bindMainViewModel(moviesListViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    protected abstract fun bindDetailViewModel(moviesListViewModel: DetailViewModel): ViewModel
}