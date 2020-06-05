import $ivy.`com.goyeau::mill-scalafix:0.1.1`
import com.goyeau.mill.scalafix.ScalafixModule
import mill._, scalalib._, publish._
import coursier.maven.MavenRepository

object Version {
  val scalaVersion = "2.13.2"

  lazy val zio = "1.0.0-RC20"
  lazy val zioConfig = "1.0.0-RC20"
  lazy val catsEffectVersion = "2.1.3"
  lazy val catsVersion = "2.1.1"
  lazy val circeVersion = "0.13.0"
  lazy val ScalatestVersion = "3.1.2"
  lazy val Specs2Version = "4.9.4"
  lazy val CatsScalaTestVersion            = "3.0.5"
  lazy val distageVersion = "0.10.10"

}

object Libs {
  val cats = ivy"org.typelevel::cats-core::${Version.catsVersion}"
  val zio = ivy"dev.zio::zio:${Version.zio}"
  val zioConfig = ivy"dev.zio::zio-config:${Version.zioConfig}"
  val zioConfigTypesafe = ivy"dev.zio::zio-config-typesafe:${Version.zioConfig}"
  val effect = ivy"org.typelevel::cats-effect::${Version.catsEffectVersion}"
  val zioTest = ivy"dev.zio::zio-test:${Version.zio}"
  val zioTestSbt = ivy"dev.zio::zio-test-sbt:${Version.zio}"
  val ziocats = ivy"dev.zio::zio-interop-cats::2.1.3.0-RC15"
  val catstests = ivy"com.ironcorelabs::cats-scalatest::${Version.CatsScalaTestVersion}"
  val minddistagecore = ivy"io.7mind.izumi::distage-core::${Version.distageVersion}"

  val specs2 = ivy"org.specs2::specs2-core::${Version.Specs2Version}"
  val scalatest = ivy"org.scalatest::scalatest:${Version.ScalatestVersion}"
  val scalamoch = ivy"org.scalamock::scalamock::4.4.0"

}

trait SmellyOpsModule extends ScalaModule with PublishModule {
  def forkArgs = Seq("-Xmx4g")
  def javacOptions = Seq("-Xmx4000m -Xms6000m")
  val scalaVersion = Version.scalaVersion
  def scalacOptions = T { super.scalacOptions().filterNot(Set("-Yno-imports")) ++ defaultScalaOpts }
  def publishVersion = "0.0.10"

  def repositories = super.repositories ++ Seq(
    MavenRepository("https://oss.sonatype.org/content/repositories/releases")
  )

  def pomSettings = PomSettings(
    description = "Cats IO and ZIO bi-conversions",
    organization = "com.github.jacke",
    url = "https://github.com/jacke/smelly-ops",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("jacke", "smelly-ops"),
    developers = Seq(
      Developer("jacke", "Stan Sobolev","https://github.com/Jacke")
    )
  )

  val defaultScalaOpts = Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "UTF-8", // Specify character encoding used by source files.
    "-Ymacro-annotations",
    "-language:implicitConversions",
    "-feature",
    "-language:existentials",
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-value-discard" ,
    //"-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
  //  "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-language:postfixOps",    
    "-language:higherKinds", // Allow higher-kinded types
    "-language:postfixOps", // Allows operator syntax in postfix position (deprecated since Scala 2.10)
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    //"-Xfatal-warnings"            // Fail the compilation if there are any warnings
  )
}

trait SmellyOpsModuleWithTests extends SmellyOpsModule {
  object test extends Tests {
    override def moduleDeps = super.moduleDeps
    override def ivyDeps = super.ivyDeps
    def testOne(args: String*) = T.command {
      super.runMain("org.scalatest.run", args: _*)
    }
    def testFrameworks = Seq(
      "org.scalatest.tools.Framework",
      //"org.specs2.runner.Specs2Framework"
    )
  }
}

object smelly extends SmellyOpsModuleWithTests with ScalafixModule {
  def repositories = super.repositories ++ Seq(
    MavenRepository("https://dl.bintray.com/iheartradio/maven"),
    MavenRepository("https://download.eclipse.org/jgit/maven"),
    MavenRepository("https://dl.bintray.com/scalaz/releases"),
    MavenRepository("https://dl.bintray.com/colisweb/maven"),
    MavenRepository("https://oss.sonatype.org/content/repositories/snapshots/"),
    MavenRepository(
      "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
    ),
    MavenRepository(
      "https://repository.apache.org/content/repositories/snapshots/"
    ),
    MavenRepository("https://oss.sonatype.org/content/repositories/releases")
  )

  def scalacPluginIvyDeps = Agg(
    ivy"org.typelevel:kind-projector_2.13.2:0.11.0",
    ivy"com.olegpy:better-monadic-for_2.13:0.3.1"
  )


  override def ivyDeps = {
    Agg(
      Libs.specs2,
      Libs.scalatest,
      Libs.minddistagecore,
      Libs.ziocats,
      Libs.catstests,
      Libs.effect,
      Libs.cats,
      Libs.zio,
      Libs.zioConfig,
      Libs.zioConfigTypesafe
    )
  }
}
