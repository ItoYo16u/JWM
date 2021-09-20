import sbt._
import Dependencies._
import scala.sys.process._
resolvers += "jetbrain skija" at "https://packages.jetbrains.team/maven/p/skija/maven"

lazy val genIcon = taskKey[Unit]("generate icon for title bar")

genIcon := {
  System.getProperty("os.name").toLowerCase match {
    case mac if mac.contains("mac") => {
      Process(
        Seq(
          "iconutil",
          "-c",
          "icns",
          "macos.iconset"
        ),
        ( Compile / resourceDirectory).value
      )!
    }
    case win if win.contains("win") => {
      Process(
        "convert" +: Seq(16, 24, 32, 48, 256)
          .map(dim => s"windows/icon_${dim}x${dim}.png") :+ "windows.ico",
          (Compile / resourceDirectory).value
      )!
    }
  }
}

Compile / run := (Compile / run).dependsOn(genIcon).evaluated

ThisBuild / organization := "org.jetbrains.jwm"
javaOptions ++= Seq(
  "enablesystemassertions",
  "-enableassertions",
  "-Xcheck:jni",
  "-Dfile.encoding=UTF-8"
)
name := "metrics"
version := "0.1.0-SNAPSHOT"

ThisBuild / crossPaths := false
ThisBuild / autoScalaLibrary := false
lazy val metrics = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Dependencies.deps
  )
