package com.example.domain.usecases

import com.example.domain.entities.ToDoItem
import com.example.domain.repositories.ToDoRepository
import io.reactivex.Observable

class GetToDoListUseCase(private val repository: ToDoRepository) :
    BaseUseCase<GetToDoListUseCase.Request, List<ToDoItem>> {
    override fun execute(request: Request): Observable<List<ToDoItem>> {
        return repository.getToDoList()
    }

    class Request : BaseUseCase.BaseRequest
}