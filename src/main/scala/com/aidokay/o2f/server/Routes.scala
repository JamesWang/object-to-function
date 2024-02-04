package com.aidokay.o2f.server

import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.*
import io.circe.*


object Routes {
  def helloRoutes[F[_]: Monad]: HttpRoutes[F] =
    val dsl = Http4sDsl[F]
    import dsl.*
    import org.http4s.circe._

    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        Ok(s"""{"hello", $name}""")
    }
}
