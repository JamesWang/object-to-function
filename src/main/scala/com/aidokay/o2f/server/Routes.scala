package com.aidokay.o2f.server

import cats.Monad
import com.aidokay.o2f.server.Handlers.Razor
import org.http4s.*
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.`Content-Type`


object Routes {
  private val HTML = `Content-Type`(MediaType.text.html);

  def toRoutes[F[_] : Monad]: HttpRoutes[F] =
    val dsl = Http4sDsl[F]
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / user / list =>
        Ok(Razor.showList(user, list), HTML)
    }

  def helloRoutes[F[_] : Monad]: HttpRoutes[F] =
    val dsl = Http4sDsl[F]
    import dsl.*

    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        Ok(s"""{"hello", $name}""")
      case GET -> Root / "index.html" =>
        Ok(helloPage, HTML)
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
