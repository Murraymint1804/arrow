package arrow.typeclasses.suspended.monad.commutative.safe

import arrow.Kind
import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.typeclasses.Monad
import arrow.typeclasses.MonadContinuation
import arrow.unsafe

/**
 * Fx allows you to run pure sequential code as if it was imperative.
 *
 * @see [arrow.typeclasses.suspended.BindSyntax]
 * @see [arrow.typeclasses.suspended.monad.Fx]
 * @see [arrow.typeclasses.suspended.monaderror.Fx]
 * @see [arrow.effects.typeclasses.suspended.monaddefer.Fx]
 * @see [arrow.effects.typeclasses.suspended.concurrent.Fx]
 * @see [arrow.typeclasses.suspended.monad.commutative.safe.Fx]
 * @see [arrow.typeclasses.suspended.monad.commutative.unsafe.Fx]
 */
interface Fx<F> {

  fun monad(): Monad<F>

  fun <A, B, C> fx(
    fa: Kind<F, A>,
    fb: Kind<F, B>,
    f: (Tuple2<A, B>) -> C
  ): Kind<F, C> =
    monad().run { map(fa, fb, f) }

  fun <A, B, C, D> fx(
    fa: Kind<F, A>,
    fb: Kind<F, B>,
    fc: Kind<F, C>,
    f: (Tuple3<A, B, C>) -> D
  ): Kind<F, D> =
    monad().run {
      map(fa, fb, fc, f)
    }

  suspend fun <A> unsafe.fx(
    f: suspend MonadContinuation<F, *>.() -> A
  ): Kind<F, A> =
    monad().binding(f)

}

