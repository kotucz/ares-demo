package kotu.cz.ares.subjectsearch

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotu.cz.ares.model.Address
import kotu.cz.ares.model.AresSubject
import kotu.cz.ares.rest.AresSubjectModule
import org.simpleframework.xml.core.PersistenceException
import java.io.IOException

class SubjectSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val aresSubjectModule = AresSubjectModule(application)
    private val subjectService = aresSubjectModule.aresSubjectService

    private val querySubmits = PublishRelay.create<String>()
    private val foundSubject = BehaviorRelay.create<AresSubject>()

    init {
        querySubmits.switchMap {
            Single.just(messageSubject("Načítání..."))
                .concatWith(
                    subjectService.getSubject(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn { error ->
                            val message = when {
                                error is IOException -> "Chyba přípojení"
                                error.cause is PersistenceException -> "Nelze načíst subjekt"
                                else -> "Neznámá chyba"
                            }
                            messageSubject(message)
                        }).toObservable()
        }.subscribe(foundSubject)
    }

    fun querySubmits(): Consumer<String> = querySubmits

    fun subjectNames(): Observable<String> = foundSubject
        .map { it.name }

    fun subjectAddress1(): Observable<String> = foundSubject
        .map { "${it.address.street} ${it.address.streetNumber}" }

    fun subjectAddress2(): Observable<String> = foundSubject
        .map { "${it.address.postCode} ${it.address.city}" }

    private fun messageSubject(message: String): AresSubject {
        return AresSubject(
            message,
            Address("", "", "", "")
        )
    }
}