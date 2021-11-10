package io.afdon.bitcoinprice.domain.entity

sealed class RequestState<T> {

    class Loading<S>(val isLoading: Boolean) : RequestState<S>()

    class Success<S>(val data: S) : RequestState<S>()

    class Error<S>(val error: String) : RequestState<S>()
}