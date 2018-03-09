package kotu.cz.ares.rest

import io.reactivex.Single
import kotu.cz.ares.model.Address
import kotu.cz.ares.model.AresSubject

class AresSubjectService {
    fun getSubject(ico: String): Single<AresSubject> = Single.just(
        AresSubject(
            name = "Subject # $ico",
            address = Address("Mesto")
        )
    )
}