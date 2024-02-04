package com.aidokay.o2f.server

import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.*
import io.circe.*
import org.http4s.headers.`Content-Type`
import org.http4s.syntax.*


object Routes {
  def helloRoutes[F[_]: Monad]: HttpRoutes[F] =
    val dsl = Http4sDsl[F]
    import dsl.*
    import org.http4s.circe._

    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        Ok(s"""{"hello", $name}""")
      case GET -> Root / "index.html" =>
        Ok(helloPage, `Content-Type`(MediaType.text.html))
    }


  private def helloPage =
    """
      |<html>
      | <body>
      |   <h1 style="text-align:center; font-size:3em;" >
      |     Hello Functional World!
      |   </h1>
      | </body>
      |</html>
      |""".stripMargin
}
