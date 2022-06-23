name := """scala-benchmarks"""

version := "1.1.0"

scalaVersion := "2.13.8"

scalacOptions := Seq(
  "-opt:l:inline",
  "-opt-inline-from:**",
  "-deprecation",
//  "-Ypartial-unification",
  "-Ywarn-value-discard",
//  "-Ywarn-unused-import",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen"
)

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.3.6"
)

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 10 -i 10 -r 1 -w 1 .*Bench.*
 */
enablePlugins(JmhPlugin)
