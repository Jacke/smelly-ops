# smelly-ops
Cats &amp; ZIO ops made implicitly

Add *toZIO[Env]* and *toCatsEffect* to cats.effect.IO/zio.ZIO instances
where Env could be ZEnv, Any, ...

```scala
  val CIOE: cats.effect.IO[Int] = cats.effect.IO(1*100)

  CIOE.toZIO[ZEnv].toCatsEffect.toZIO[Any].toCatsEffect.unsafeRunSync shouldBe 100
```
