package com.asiman.cryptotracker.ui.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import com.asiman.cryptotracker.support.tools.NavigationCommand
import com.asiman.module_network.model.response.ErrorResponse
import com.asiman.module_network.repo.base.BaseRepository
import com.asiman.module_network.support.SingleLiveEvent

open class BaseViewModel constructor(
    application: Application,
    private vararg val repositories: BaseRepository,
) : AndroidViewModel(application) {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _info = MutableLiveData<String>()
    val info: LiveData<String>
        get() = _info

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val operationError: MediatorLiveData<ErrorResponse> = MediatorLiveData()

    init {
        bindRepoObserver()
    }

    private fun bindRepoObserver() {
        repositories.forEach { repo ->
            operationError.addSource(repo.operationError) { error ->
                operationError.value = error
            }
        }
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }

    protected fun handleLoading(state: Boolean) {
        _isLoading.postValue(state)
    }

    protected fun handleInfo(info: String) {
        _info.postValue(info)
    }

    protected fun handleError(error: String) {
        _error.postValue(error)
    }

    fun getString(@StringRes resId: Int): String {
        return getApplication<Application>().resources.getString(resId)
    }

}