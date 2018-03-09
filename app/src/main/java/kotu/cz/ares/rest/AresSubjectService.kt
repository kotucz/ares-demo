package kotu.cz.ares.rest

import io.reactivex.Single
import kotu.cz.ares.model.AresSubject
import retrofit2.http.GET
import retrofit2.http.Query

interface AresSubjectService {
    @GET("darv_rzp.cgi")
    fun getSubject(@Query("ico") ico: String): Single<AresSubject>
}