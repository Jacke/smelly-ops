package com.github.jacke.smelly

import cats.effect._
import cats.effect.{ Effect, ExitCase, LiftIO }
import cats.syntax.all._
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import zio.interop.catz._
import zio.interop.catz.implicits._
import zio.interop.catz.taskEffectInstance
import zio.{ Runtime, ZEnv, ZIO, Schedule => ZSchedule }
import zio.{ RIO, Task, ZIO }
import izumi.functional._
import izumi.functional.bio._

object SmellyOps {

  implicit class SmellyCatty[A, U](zio: RIO[U, A])(implicit R: Runtime[U], F: LiftIO[cats.effect.IO]) {
    def toCatsEffect: cats.effect.IO[A] =
      F.liftIO(taskEffectInstance.toIO(zio))
  }

  implicit class SmellyZI[F[+_, +_]: BIOAsync: BIOFork, R[_]: Effect, B](t: R[B]) {
    def toZIO[U] = {
      import izumi.functional.bio.catz._
      Concurrent[F[Throwable, ?]].liftIO(Effect[R].toIO(t))
    }.asInstanceOf[ZIO[U, Throwable, B]]
  }

}
