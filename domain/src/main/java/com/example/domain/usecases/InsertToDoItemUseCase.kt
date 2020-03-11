package com.example.domain.usecases

import com.example.domain.entities.ToDoItem
import com.example.domain.repositories.ToDoRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class InsertToDoItemUseCase @Inject constructor(private val repository: ToDoRepository) :
    BaseUseCase<InsertToDoItemUseCase.Request, Int> {
    override fun execute(request: Request): Observable<Int> {
        return repository.insertToDoItem(request.toDoItem)
    }

    data class Request(val toDoItem: ToDoItem) : BaseUseCase.BaseRequest
}