package com.example.domain.usecases

import com.example.domain.entities.ToDoItem
import com.example.domain.repositories.ToDoRepository
import io.reactivex.Completable
import io.reactivex.Observable

class UpdateToDoItemUseCase(private val repository: ToDoRepository) :
    BaseUseCase<UpdateToDoItemUseCase.Request, Int> {
    override fun execute(request: Request): Observable<Int> {
        return repository.updateToDoItem(request.toDoItem)
    }

    data class Request(val toDoItem: ToDoItem) : BaseUseCase.BaseRequest
}