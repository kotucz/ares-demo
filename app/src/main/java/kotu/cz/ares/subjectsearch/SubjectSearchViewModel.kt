package kotu.cz.ares.subjectsearch

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotu.cz.ares.model.AresSubject
import kotu.cz.ares.rest.AresSubjectService

class SubjectSearchViewModel : ViewModel() {
    private val subjectService = AresSubjectService()

    private val querySubmits = PublishRelay.create<String>()
    private val foundSubject = BehaviorRelay.create<AresSubject>()

    init {
        querySubmits.switchMapSingle {
            subjectService.getSubject(it)
        }.subscribe(foundSubject)
    }

    fun querySubmits(): Consumer<String> = querySubmits

    fun subjectNames(): Observable<String> = foundSubject
        .map { it.name }
}