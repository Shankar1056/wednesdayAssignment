package com.wednesdayassignment.ui.artist

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wednesdayassignment.AppController
import com.wednesdayassignment.R
import com.wednesdayassignment.data.local.AppDatabase
import com.wednesdayassignment.data.local.AppExecutors
import com.wednesdayassignment.data.model.ArtistModel
import com.wednesdayassignment.data.model.ResultItems
import com.wednesdayassignment.data.network.ApiServices
import com.wednesdayassignment.data.network.RetroClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ArtistViewModel(val app: Application) : AndroidViewModel(app) {
    private lateinit var artistName: String
    var showMessage = MutableLiveData<String>()
    var showProgress = MutableLiveData<Boolean>()
    var onResponse = MutableLiveData<List<ResultItems>>()
    var onNoResponse = MutableLiveData<Boolean>(false)
    private var mDb: AppDatabase? = null

    init {
        mDb = AppDatabase.getInstance(app)
    }

    fun getRepo(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                artistName = editable.toString()
                getRepoFromServer()
            }
        }
    }

    private fun getRepoFromServer() {
        if (artistName.isNullOrEmpty()) {
            showMessage.value = app.resources.getString(R.string.empty_artist)
            return
        }
        if (AppController.instance?.networkStateMonitor?.isUp!!) {
            showProgress.value = true
            getRepoBase64Observable.subscribeWith(getRepoBase64Observer)
        } else {
            showProgress.value = false
            showMessage.value = app.resources.getString(R.string.no_internet_connection)
        }

    }


    private val getRepoBase64Observable: Observable<ArtistModel>
        get() = RetroClient.getRetrofit()!!.create(ApiServices::class.java)
            .getRepoList(artistName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private val getRepoBase64Observer: DisposableObserver<ArtistModel>
        get() = object : DisposableObserver<ArtistModel>() {
            override fun onNext(@NonNull response: ArtistModel) {
                Log.i("response:::", response.toString())
                showProgress.value = false
                insertDataInLocalDB(response.results)

            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                showProgress.value = false
                showMessage.value = e.message
                return
            }

            override fun onComplete() {
                // message.value = "completed"
            }
        }

    private fun insertDataInLocalDB(results: ArrayList<ResultItems>?) {
        AppExecutors.instance?.diskIO()?.execute(Runnable {

            mDb!!.personDao()?.deleteAll()
            mDb!!.personDao()?.insertArtist(results)
            getLocalData()

        })
    }

    fun getLocalData() {
        AppExecutors.instance?.diskIO()?.execute(Runnable {
            val artist: List<ResultItems>? = mDb?.personDao()?.loadAllArtist()

            if (artist.isNullOrEmpty()) {
                onNoResponse.postValue(true)
            } else {
                onNoResponse.postValue(false)
            }
            onResponse.postValue(artist)
        })
    }

}