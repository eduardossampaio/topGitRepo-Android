package com.esampaio.core.usecases

import io.reactivex.rxjava3.core.Observable

interface UseCase<T,R : Any> {

    fun start(params:T):Observable<R>

    fun finish();
}