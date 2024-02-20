package com.aidokay.o2f.domain

object models {

  case class User(name: String)

  case class ListName(name: String)
  case class ToDoItem(desc: String)
  case class ToDoList(listName: ListName, items: List[ToDoItem])

  enum ToDoStatus {
    case Todo, InProgress, Done, Blocked
  }

  case class HtmlPage(raw: String)

}
