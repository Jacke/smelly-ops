import sbt._

object Dependencies {

  object Versions {
    val catsEffect  = "2.1.2"
    val monix       = "3.1.0"
    val pureconfig  = "0.12.3"
    val betterFiles = "3.8.0"

    // Logging
    val scalaLogging = "3.9.2"

    // Test
    val scalaTest  = "3.1.1"
    val scalaCheck = "1.14.3"

    // Compiler
    val kindProjector    = "0.10.3"
    val betterMonadicFor = "0.3.1"
  }

  object Libraries {
    lazy val catsEffect  = "org.typelevel"         %% "cats-effect"  % Versions.catsEffect
    lazy val monix       = "io.monix"              %% "monix"        % Versions.monix
    lazy val pureconfig  = "com.github.pureconfig" %% "pureconfig"   % Versions.pureconfig
    lazy val betterFiles = "com.github.pathikrit"  %% "better-files" % Versions.betterFiles

    // Logging
    lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging

    // Test
    lazy val scalaTest  = "org.scalatest"  %% "scalatest"  % Versions.scalaTest
    lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Versions.scalaCheck

    // Compiler
    lazy val kindProjector    = "org.typelevel" %% "kind-projector"     % Versions.kindProjector
    lazy val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
  }

}
