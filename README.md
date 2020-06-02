# smelly-ops
Cats &amp; ZIO ops made implicitly

Add *toZIO[Env]* and *toCatsEffect* to cats.effect.IO/zio.ZIO instances
where Env could be ZEnv, Any, ...

```scala

  implicit val R: zio.Runtime[zio.ZEnv] = zio.Runtime.default
  val CIOE: cats.effect.IO[Int] = cats.effect.IO(1*100)

  import SmellyOps._
  // convert IO to ZIO[Zenv] to IO to ZIO[Any] to IO and run in
  CIOE.toZIO[ZEnv].toCatsEffect.toZIO[Any].toCatsEffect.unsafeRunSync shouldBe 100
```
