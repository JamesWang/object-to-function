package com.aidokay.o2f.server

import cats.effect.{ExitCode, IO, IOApp}
import com.aidokay.o2f.server.Routes.helloRoutes
import com.comcast.ip4s.{ipv4, port}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router

object Server extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    val apis = Router(
      "api" -> helloRoutes[IO],
    ).orNotFound
    println("starting http server")

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8088")
      .withHttpApp(apis)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)

}
