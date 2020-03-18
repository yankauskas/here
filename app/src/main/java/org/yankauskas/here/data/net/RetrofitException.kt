package org.yankauskas.here.data.net

import java.io.IOException
import java.net.ConnectException

/**
 * Created by alex_litvinenko on 03.08.17.
 */
sealed class RetrofitException constructor(val kind: Kind) : RuntimeException() {

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * An [ConnectException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    class NetworkRetrofitException(val exception: IOException) : RetrofitException(Kind.NETWORK)
    class HttpRetrofitException(val responseCode: Int) : RetrofitException(Kind.HTTP)
    class UnexpectedRetrofitException(val exception: Throwable) : RetrofitException(Kind.UNEXPECTED)
}