package kotu.cz.ares

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotu.cz.ares.subjectsearch.SubjectSearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var subjectSearchViewModel: SubjectSearchViewModel

    private lateinit var subscriptions: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subjectSearchViewModel = ViewModelProviders.of(this).get(SubjectSearchViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        subjectSearchViewModel.let { vm ->
            subscriptions = CompositeDisposable(
                vm.subjectNames().subscribe(subjectName::setText),
                vm.subjectAddress1().subscribe(subjectAddress1::setText),
                vm.subjectAddress2().subscribe(subjectAddress2::setText),
                RxSearchView.queryTextChangeEvents(searchView)
                    .filter { it.isSubmitted }
                    .map { it.queryText().toString() }
                    .subscribe(vm.querySubmits())
            )
        }
    }

    override fun onPause() {
        subscriptions.dispose()
        super.onPause()
    }
}
