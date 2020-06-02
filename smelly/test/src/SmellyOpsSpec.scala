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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SmellyOpsTest extends AnyWordSpec with Matchers {
  import SmellyOps._
  val CIOE: cats.effect.IO[Int] = cats.effect.IO(1*100)
  val ZIOE: ZIO[Any, Throwable, Int] = Task(1*100)
  implicit val R: zio.Runtime[zio.ZEnv] = Runtime.default

  "BI Conversion" must {
    "convert to cats.effect.IO" in {
        ZIOE.toCatsEffect.unsafeRunSync shouldBe 100
    }
    "convert to ZIO" in {
      CIOE.toZIO[ZEnv].toCatsEffect.unsafeRunSync shouldBe 100
    }
    "convert to ZIO to IO to ZIO to IO" in {
      CIOE.toZIO[ZEnv].toCatsEffect.toZIO[Any].toCatsEffect.unsafeRunSync shouldBe 100
    }    
  }

}
