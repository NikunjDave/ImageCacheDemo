package com.test.gojek.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hkuaapp.network.ApiService
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

 abstract class  BaseViewModel<N : BaseNavigtor> : ViewModel() {

     private val mIsLoading = MutableLiveData<Boolean>()
     private var mNavigator: WeakReference<N>? = null

     val compositeDisposable: CompositeDisposable = CompositeDisposable()
      val mApiService by lazy {
         ApiService.create()
     }
     companion object {
         private val TAG = BaseViewModel::class.java.simpleName
     }


     override fun onCleared() {
         compositeDisposable.dispose()
         super.onCleared()
     }

     fun setIsLoading(isLoading: Boolean) {
         mIsLoading.value = isLoading
     }

     fun getNavigator(): N? {
         return mNavigator?.get()
     }

     fun setNavigator(navigator: N) {
         this.mNavigator = WeakReference(navigator)
     }

 }