/*
 * Copyright (c) 2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package types

import scala.language.implicitConversions
import scala.languageFeature.higherKinds
/**
  * |
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/1/25 01:19
  */

object | {
  type |[A, B]

  sealed trait Evidence[-A, +B]

  private object ReusableEvidence extends Evidence[scala.Any, scala.Any]

  abstract sealed class EvidenceLowestPriorityImplicits { this: Evidence.type =>
    implicit def right[A, B1, B2](implicit ev: Evidence[A, B2]): Evidence[A, B1 | B2] =
      ReusableEvidence.asInstanceOf[Evidence[A, B1 | B2]]
    implicit def covariant[F[+_], A, B](implicit ev: Evidence[A, B]): Evidence[F[A], F[B]] =
      ReusableEvidence.asInstanceOf[Evidence[F[A], F[B]]]

    implicit def contravariant[F[-_], A, B](implicit ev: Evidence[B, A]): Evidence[F[A], F[B]] =
      ReusableEvidence.asInstanceOf[Evidence[F[A], F[B]]]
  }

  abstract sealed class EvidenceLowPriorityImplicits extends EvidenceLowestPriorityImplicits {
    this: Evidence.type =>

    implicit def left[A, B1, B2](implicit ev: Evidence[A, B1]): Evidence[A, B1 | B2] =
      ReusableEvidence.asInstanceOf[Evidence[A, B1 | B2]]
  }

  object Evidence extends EvidenceLowPriorityImplicits {
    implicit def base[A]: Evidence[A, A] =
      ReusableEvidence.asInstanceOf[Evidence[A, A]]

    implicit def allSubtypes[A1, A2, B](implicit ev1: Evidence[A1, B], ev2: Evidence[A2, B]): Evidence[A1 | A2, B] =
      ReusableEvidence.asInstanceOf[Evidence[A1 | A2, B]]
  }

  implicit def from[A, B1, B2](a: A)(implicit ev: Evidence[A, B1 | B2]): B1 | B2 =
    a.asInstanceOf[B1 | B2]

  implicit def fromTypeConstructor[F[_], A, B](a: F[A])(implicit ev: Evidence[F[A], F[B]]): F[B] =
    a.asInstanceOf[F[B]]

  implicit class UnionOps[A <: _ | _] private[|] (private val self: A) extends AnyVal {
    def merge[B](implicit ev: |.Evidence[A, B]): B =
      self.asInstanceOf[B]
  }

}
