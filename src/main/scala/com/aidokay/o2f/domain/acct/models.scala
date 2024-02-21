package com.aidokay.o2f.domain.acct

import java.util.{Calendar, Date}

object models {
  type Amount = BigDecimal

  def today: Date = Calendar.getInstance.getTime

  case class Balance(amount: Amount = 0)

  case class Account(no: String, name: String, dataOfOpen: Date, dateOfClose: Option[Date] = None, balance: Balance = Balance(0))

}
