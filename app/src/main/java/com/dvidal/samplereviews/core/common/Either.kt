package com.dvidal.samplereviews.core.common

import com.dvidal.samplereviews.core.common.Either.Companion.right

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun either(fnL: (L) -> Unit, fnR: (R) -> Unit): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }

    fun leftOrNull(): L? =
        when (this) {
            is Left -> a
            is Right -> null
        }

    fun rightOrNull(): R? =
        when (this) {
            is Left -> null
            is Right -> b
        }

    companion object {

        @JvmStatic
        fun <L> left(a: L) = Either.Left(a)

        @JvmStatic
        fun <R> right(b: R) = Either.Right(b)
    }

    /**
     * Represents the left side of [Either] class which by convention is a "Failure".
     */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     */
    data class Right<out R>(val b: R) : Either<Nothing, R>()
}

// region EitherResult
typealias EitherResult<T> = Either<Throwable, T>

typealias Success<T> = Either.Right<T>

typealias Failure = Either.Left<Throwable>

val <T> Success<T>.value get() = b

val Failure.error get() = a

val <R> EitherResult<R>.isSuccess get() = isRight

val <R> EitherResult<R>.isFailure get() = isLeft

fun <R> EitherResult<R>.getOrNull() = rightOrNull()

fun <R> EitherResult<R>.exceptionOrNull() = leftOrNull()

fun <R> EitherResult<R>.throwOnFailure() {
    if (this is Failure) throw this.error
}

fun <R> EitherResult<R>.getOrThrow(): R {
    return when (this) {
        is Success -> this.value
        is Failure -> throw this.error
    }
}

fun <R> EitherResult<R>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    return when (this) {
        is Success -> this.value
        is Failure -> onFailure(this.error)
    }
}

fun <R> EitherResult<R>.getOrDefault(defaultValue: R): R {
    return when (this) {
        is Success -> this.value
        is Failure -> defaultValue
    }
}

fun <R> EitherResult<R>.fold(onSuccess: (R) -> R, onFailure: (Throwable) -> R): R {
    return when (this) {
        is Success -> onSuccess(this.value)
        is Failure -> onFailure(this.error)
    }
}

fun <T, R> EitherResult<R>.mapCatching(fn: (R) -> T): EitherResult<T> {
    return when (this) {
        is Success -> catching { fn(this.value) }
        is Failure -> Failure(this.error)
    }
}

inline fun <R> catching(block: () -> R): EitherResult<R> {
    return try {
        Success(block())
    } catch (exception: Throwable) {
        Failure(exception)
    }
}

inline fun <T> EitherResult<T>.onFailure(action: (exception: Throwable) -> Unit): EitherResult<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

inline fun <T> EitherResult<T>.onSuccess(action: (value: T) -> Unit): EitherResult<T> {
    getOrNull()?.let { action(it) }
    return this
}
// endregion

// region Transformation
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = flatMap(fn.c(::right))

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.left(a)
        is Either.Right -> fn(b)
    }
// endregion