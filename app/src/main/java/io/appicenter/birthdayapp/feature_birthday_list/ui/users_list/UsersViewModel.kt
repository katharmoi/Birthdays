package io.appicenter.birthdayapp.feature_birthday_list.ui.users_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appicenter.birthdayapp.common.Result
import io.appicenter.birthdayapp.feature_birthday_list.domain.interactor.GetUsers
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    getUsers: GetUsers
) : ViewModel() {

    private val _state = MutableLiveData<Result<List<User>>>()
    val state: LiveData<Result<List<User>>> = _state

    init {
        getUsers().onEach { users ->
            _state.postValue(users)
        }.launchIn(viewModelScope)
    }
}