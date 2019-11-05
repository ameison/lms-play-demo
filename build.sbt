name := """bittamina-web"""
organization := "org.bittamina"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  guice,
  evolutions,
  jdbc,
  javaForms,
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "com.auth0" % "java-jwt" % "3.8.3",
  "io.dropwizard.metrics" % "metrics-core" % "3.2.6",
  "com.palominolabs.http" % "url-builder" % "1.1.0",
  "net.jodah" % "failsafe" % "1.0.5",

  "org.mindrot" % "jbcrypt" % "0.4",
  "commons-io" % "commons-io" % "2.3",
  "org.webjars" % "satellizer" % "0.12.5",
  "org.mockito" % "mockito-core" % "1.8.5",
  "com.itextpdf" % "itextpdf" % "5.4.2",
  "com.itextpdf.tool" % "xmlworker" % "5.4.1",
  "org.apache.poi" % "poi" % "3.9",
  "javax.mail" % "mail" % "1.4.4"
)

