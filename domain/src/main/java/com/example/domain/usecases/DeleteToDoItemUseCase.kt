package com.example.domain.usecases

import com.example.domain.entities.ToDoItem
import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class DeleteToDoItemUseCase @Inject constructor(private val repository: ToDoRepository) :
    CompletableUseCase<ToDoItem> {
    override fun executeUseCase(param: ToDoItem): Completable {
        return repository.deleteToDoItem(param)
    }
}