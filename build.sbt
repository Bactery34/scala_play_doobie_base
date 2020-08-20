name := "evolutions/gameApi"
 
version := "1.0" 
      
lazy val `gameapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

resolvers += Resolver.bintrayRepo("hmrc", "releases")

scalaVersion := "2.12.2"

val circeVersion = "0.12.3"

libraryDependencies += "uk.gov.hmrc" %% "emailaddress" % "3.5.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.3.6" % "provided"

libraryDependencies += "com.dripower" %% "play-circe" % "2812.0"

libraryDependencies += "com.softwaremill.macwire" %% "macrosakka" % "2.3.6" % "provided"

libraryDependencies += "com.softwaremill.macwire" %% "util" % "2.3.6"

libraryDependencies += "com.softwaremill.macwire" %% "proxy" % "2.3.6"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies ++= Seq(

  // Start with this one
  "org.tpolecat" %% "doobie-core"      % "0.9.0",

  // And add any of these as needed
  "org.tpolecat" %% "doobie-h2"        % "0.9.0",          // H2 driver 1.4.200 + type mappings.
  "org.tpolecat" %% "doobie-hikari"    % "0.9.0",          // HikariCP transactor.
  "org.tpolecat" %% "doobie-postgres"  % "0.9.0",          // Postgres driver 42.2.12 + type mappings.
  "org.postgresql" % "postgresql" % "42.2.14",
  "org.tpolecat" %% "doobie-quill"     % "0.9.0",          // Support for Quill 3.5.1
  "org.tpolecat" %% "doobie-specs2"    % "0.9.0" % "test", // Specs2 support for typechecking statements.
  "org.tpolecat" %% "doobie-scalatest" % "0.9.0" % "test"  // ScalaTest support for typechecking statements.

)

libraryDependencies += "com.iheart" %% "ficus" % "1.4.7"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(evolutions, jdbc)

libraryDependencies += "com.h2database" % "h2" % "1.4.192"

      