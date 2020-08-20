package db.doobie

import cats.effect.{Blocker, IO, Resource}
import com.typesafe.config.Config
import doobie.hikari.HikariTransactor
import db.doobie.config.DatabaseConfig
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import javax.inject.Inject

class DoobieDatabase @Inject()(databaseConfig: DatabaseConfig) {

  implicit val cs = IO.contextShift(scala.concurrent.ExecutionContext.global)

  val transactor: Resource[IO, HikariTransactor[IO]] = {
    for {
      ecd <- ExecutionContexts.fixedThreadPool[IO](10) // our connect EC
      be <- Blocker[IO]    // our blocking EC
      xa <-HikariTransactor.newHikariTransactor[IO](
        databaseConfig.driver,    // driver classname
        databaseConfig.url,       // connect URL
        databaseConfig.user,      // username
        databaseConfig.password,  // password
        ecd,                                             // await connection here
        be                                              // execute JDBC operations here
      )
    } yield xa
  }

  val xa = Transactor.fromDriverManager[IO](
    databaseConfig.driver,    // driver classname
    databaseConfig.url,       // connect URL
    databaseConfig.user,      // username
    databaseConfig.password,  // password                      // password
    Blocker.liftExecutionContext(ExecutionContexts.synchronous) // just for testing
  )

  val y = xa.yolo


}
