name := "interpSBT"

version := "1.0"

scalaVersion := "2.11.2"

javacOptions ++= Seq("-encoding", "UTF-8")

jfxSettings

JFX.addJfxrtToClasspath := true

JFX.mainClass := Some("Main")
