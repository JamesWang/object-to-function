package com.aidokay.o2f.domain

object models {

  case class User(name: String)

  case class ToDoItem(desc: String)

  enum ToDoStatus {
    case Todo, InProgress, Done, Blocked
  }

  case class HtmlPage(raw: String)

}
