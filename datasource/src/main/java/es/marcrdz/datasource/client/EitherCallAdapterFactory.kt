package es.marcrdz.datasource.client

import arrow.core.Either
import es.marcrdz.data.domain.ErrorDTO
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Custom [CallAdapter.Factory] to handle Retrofit [Response] through [Either] type
 * parsing both exceptions and http error responses as [ErrorDTO]
 *
 * Original idea taken from:
 * https://proandroiddev.com/retrofit-calladapter-for-either-type-2145781e1c20
 */
internal class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        val leftType = getParameterUpperBound(0, responseType)
        if (getRawType(leftType) != ErrorDTO::class.java) return null

        val rightType = getParameterUpperBound(1, responseType)
        return EitherCallAdapter<Any>(rightType)
    }
}

private class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<ErrorDTO, R>>> {

    override fun adapt(call: Call<R>): Call<Either<ErrorDTO, R>> = EitherCall(call, successType)

    override fun responseType(): Type = successType
}

class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<ErrorDTO, R>> {

    override fun enqueue(callback: Callback<Either<ErrorDTO, R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither()))
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = when (throwable) {
                    is IOException -> ErrorDTO.Network
                    else -> ErrorDTO.Unknown
                }
                callback.onResponse(this@EitherCall, Response.success(Either.Left(error)))
            }
        }
    )

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<Either<ErrorDTO, R>> = EitherCall(delegate.clone(), successType)

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Either<ErrorDTO, R>> =
        throw UnsupportedOperationException()

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    private fun <R> Response<R>.toEither(): Either<ErrorDTO, R> {
        // Http error response (4xx - 5xx)
        if (!isSuccessful) {
            val errorBody = errorBody()?.string() ?: ""
            return Either.Left(when (code()) {
                401 -> ErrorDTO.Unauthorized
                403 -> ErrorDTO.Forbidden
                404 -> ErrorDTO.NoData
                else -> ErrorDTO.Exception(code = "${code()}", msg = errorBody)
            })
        }

        // Http success response with body
        body()?.let { body -> return Either.Right(body) }

        // if we defined Unit as success type it means we expected no response body
        // e.g. in case of 204 No Content
        return if (successType == Unit::class.java) {
            @Suppress("UNCHECKED_CAST")
            Either.Right(Unit) as Either<ErrorDTO, R>
        } else {
            @Suppress("UNCHECKED_CAST")
            Either.Left(UnknownError("Response body was null")) as Either<ErrorDTO, R>
        }
    }
}