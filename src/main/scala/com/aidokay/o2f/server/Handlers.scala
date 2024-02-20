package com.aidokay.o2f.server

import com.aidokay.o2f.domain.models.{ListName, ToDoList, User}
import org.http4s.*
import org.http4s.headers.`Content-Type`

object Handlers {
  private val HTML = `Content-Type`(MediaType.text.html);

  trait HttpHandler

  object Razor extends HttpHandler:

    def showList(user: String, bookList: String): String =
      s"""
         |<html>
         | <body>
         |   <h1>Razor</h1>
         |     <p>Here is the list <b>$bookList</b> of user <b>$user</b></p>
         | </body>
         |</html>
         |""".stripMargin

    def extractListData(user: String, list: String): (User, ListName) =
      (User(user), ListName(list))

    def fetchListContent(listId: (User, ListName)) : ToDoList =  ???

}