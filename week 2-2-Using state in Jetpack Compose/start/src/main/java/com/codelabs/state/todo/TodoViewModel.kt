/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    // 현재 수정중인 위치
    private var currentEditPosition by mutableStateOf(-1)

    // 아이템 리스트
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    // 현재 수정중인 아이템
    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)

    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
        onEditDone()
    }

    // 아이템 선택
    fun onEditItemSelected(item: TodoItem) {
        // 포지션 수정
        currentEditPosition = todoItems.indexOf(item)
    }

    // 수정 끝
    fun onEditDone() {
        currentEditPosition = -1
    }

    // 아이템 내용 변경
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems[currentEditPosition] = item // 리스트 아이템 수정
    }
}
