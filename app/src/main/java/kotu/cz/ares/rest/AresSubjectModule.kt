package kotu.cz.ares.rest

import android.app.Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.File

class AresSubjectModule(context: Application) {
    private val cacheDirectory: File = context.cacheDir
    private val cacheSize = 10L * 1024 * 1024 // 10 MiB
    private val cache = Cache(cacheDirectory, cacheSize)

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addNetworkInterceptor(ResponseCacheInterceptor())
        .cache(cache)
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
