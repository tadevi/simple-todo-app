package com.example.domain.usecases

import com.example.domain.entities.ToDoItem
import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.base.NoParam
import com.example.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetToDoListUseCase @Inject constructor(private val repository: ToDoRepository) :
    ObservableUseCase<NoParam, List<ToDoItem>> {
    override fun executeUseCase(param: NoParam): Observable<List<ToDoItem>> {
        return repository.getToDoList()
    }
}