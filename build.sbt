name := "SudokuSolver"

version := "2.0"

scalaVersion := "2.10+"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies += "org.scala-lang" % "scala-swing" % scalaVersion.value

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"