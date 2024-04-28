package com.esampaio.core.usecases

interface UseCase<T> {

    fun <T> start(params:T);

    fun finish();
}