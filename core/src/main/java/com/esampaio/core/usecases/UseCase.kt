package com.esampaio.core.usecases

interface UseCase<T> {

    fun start(params:T);

    fun finish();
}