name := "interpSBT"

version := "1.0"

scalaVersion := "2.11.2"

javacOptions ++= Seq("-encoding", "UTF-8")

jfxSettings

JFX.devKit := JFX.jdk("C:/Program Files/Java/jdk1.7.0_71")

JFX.addJfxrtToClasspath := true

JFX.mainClass := Some("Main")

//