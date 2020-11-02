package com.rodolfonavalon.nbateamviewer.viewmodel

import androidx.lifecycle.ViewModel
import com.rodolfonavalon.nbateamviewer.data.NbaRepository
import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class TeamPageViewModel @Inject constructor(private val repository: NbaRepository): ViewModel() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun fetchTeam(teamId: Int, success: (Team) -> Unit, error: (Throwable) -> Unit) {
        repository.getTeam(teamId).subscribe(success, error).addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}