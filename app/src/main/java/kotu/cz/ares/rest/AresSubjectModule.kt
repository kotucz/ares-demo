package kotu.cz.ares.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class AresSubjectModule {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://wwwinfo.mfcr.cz/cgi-bin/ares/")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        .build()

    val aresSubjectService = retrofit.create(AresSubjectService::class.java)

    // mock
//    val aresSubjectService = object : AresSubjectService {
//        override fun getSubject(ico: String): Single<AresSubject> = Single.just(
//            AresSubject(
//                name = "Subject # $ico",
//                address = Address("Mesto")
//            )
//        )
//    }
}
