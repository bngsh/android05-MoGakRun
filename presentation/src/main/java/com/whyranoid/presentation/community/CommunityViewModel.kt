package com.whyranoid.presentation.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.usecase.GetMyGroupListUseCase
import com.whyranoid.presentation.model.GroupInfoUiModel
import com.whyranoid.presentation.model.toGroupInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    getMyGroupListUseCase: GetMyGroupListUseCase
) : ViewModel() {

    private val _myGroupList = MutableStateFlow<List<GroupInfoUiModel>>(emptyList())
    val myGroupList: StateFlow<List<GroupInfoUiModel>>
        get() = _myGroupList.asStateFlow()

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event>
        get() = _eventFlow.asSharedFlow()

    fun onCategoryItemClicked(groupInfo: GroupInfoUiModel) {
        emitEvent(Event.CategoryItemClick(groupInfo))
    }

    private fun emitEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    init {
        getMyGroupListUseCase().onEach { groupInfoList ->
            _myGroupList.value = groupInfoList.map { groupInfo ->
                groupInfo.toGroupInfoUiModel()
            }
        }.launchIn(viewModelScope)
    }
}
