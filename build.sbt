import Dependencies.Libraries
import xerial.sbt.Sonatype._

name := """smelly-ops"""

organization in ThisBuild := "com.github.jacke"

scalaVersion in ThisBuild := "2.13.2"

publishTo := sonatypePublishToBundle.value

skip in publish := true

//object {
  //val scalaVersion = "2.13.2"

  lazy val zio = "1.0.0-RC20"
  lazy val zioConfig = "1.0.0-RC20"
  lazy val catsEffectVersion = "2.1.3"
  lazy val catsVersion = "2.1.1"
  lazy val circeVersion = "0.13.0"
  lazy val ScalatestVersion = "3.1.2"
  lazy val Specs2Version = "4.9.4"
  lazy val CatsScalaTestVersion            = "3.0.5"
  lazy val distageVersion = "0.10.10"

//}

resolvers += "atlassian-maven" at "https://maven.atlassian.com/content/repositories/atlassian-public"
resolvers += "iheartradio-maven" at "https://dl.bintray.com/iheartradio/maven"
resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Sonatype OSS Snapshots1" at "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
resolvers += Resolver.jcenterRepo
resolvers += "Apache snap" at "https://repository.apache.org/content/repositories/snapshots/"
resolvers += Resolver.githubPackages("Jacke")
resolvers += Resolver.githubPackages("djspiewak")



lazy val commonSettings = Seq(
  scalaVersion := "2.13.2",
  organizationName := "com.github.spread0x",
  libraryDependencies ++= Seq(
    Libraries.catsEffect,
    Libraries.monix,
    Libraries.pureconfig,
    Libraries.betterFiles,
    Libraries.scalaLogging,
    Libraries.scalaTest  % Test,
    Libraries.scalaCheck % Test,
    compilerPlugin(Libraries.kindProjector),
    "org.typelevel" %% "cats-core" %  catsVersion,
    "dev.zio" %% "zio" % zio,
    "dev.zio" %% "zio-config" % zioConfig,
    "dev.zio" %% "zio-config-typesafe" % zioConfig,
    "org.typelevel" %% "cats-effect" %  catsEffectVersion,
    "dev.zio" %% "zio-test" % zio,
    "dev.zio" %% "zio-test-sbt" % zio,
    "dev.zio" %% "zio-interop-cats" % "2.1.3.0-RC15",
    "com.ironcorelabs" %% "cats-scalatest" %  CatsScalaTestVersion,
    "io.7mind.izumi" %% "distage-core" %  distageVersion,
    "org.specs2" %% "specs2-core" %  Specs2Version,
    "org.scalatest" %% "scalatest" % ScalatestVersion,
    "org.scalamock" %% "scalamock" % "4.4.0",

    compilerPlugin(Libraries.betterMonadicFor)
  ),
  sonatypeProjectHosting := Some(GitHubHosting("Spread0x", "smelly-ops", "iamjacke@gmail.com")),
  developers := List(
    Developer(id = "jacke", name = "jacke", email = "iamjacke@gmail.com", url = url("https://github.com/Jacke"))
  ),
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  sonatypeProfileName := "com.github.jacke",
  publishMavenStyle := true
)

lazy val testSettings = Seq(
  fork in Test := true,
  parallelExecution in Test := false
)

lazy val `better-files-cats-root` =
  (project in file("."))
    //.settings(publishTo := sonatypePublishToBundle.value)
    .settings(githubOwner := "Jacke")
    .settings(githubRepository := "smelly-ops")
    .settings(githubTokenSource := TokenSource.GitConfig("github.token"))

    .aggregate(`better-files-cats-core`)

lazy val `better-files-cats-core` = project
  .in(file("./smelly"))
  .settings(commonSettings)
    .settings(githubOwner := "Jacke")
    .settings(githubRepository := "smelly-ops")
    .settings(githubTokenSource := TokenSource.GitConfig("github.token"))
    .settings(publishTo := githubPublishTo.value)
  .settings(testSettings)

val RELEASE_TO_SONATYPE = sys.env.getOrElse("RELEASE_SONATYPE", "false").toBoolean 
publishTo := githubPublishTo.value