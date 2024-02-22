package com.aidokay.o2f.domain.acct

import java.util.{Calendar, Date}
import scala.util.{Failure, Success, Try}

object models {
  type Amount = BigDecimal

  def today: Date = Calendar.getInstance.getTime

  case class Balance(amount: Amount = 0)

  sealed trait Account {
    def no: String

    def name: String

    def dateOfOpen: Option[Date]

    def dateOfClose: Option[Date]

    def balance: Balance
  }

  final case class CheckingAccount (no: String, name: String, dateOfOpen: Option[Date], dateOfClose: Option[Date], balance: Balance) extends Account

  private final case class SavingsAccount (no: String, name: String, rateOfInterest: Amount, dateOfOpen: Option[Date], dateOfClose: Option[Date], balance: Balance) extends Account

  object Account {
    def checkingAccount(no: String, name: String, openDate: Option[Date], closeDate: Option[Date] = None, balance: Balance = Balance(0)): Try[Account] = {
      closeDateCheck(openDate, closeDate) map { d => CheckingAccount(no, name, Some(d._1), d._2, balance) }
    }

    def savingsAccount(no: String, name: String, rate: BigDecimal,
                       openDate: Option[Date], closeDate: Option[Date] = None,
                       balance: Balance = Balance(0)): Try[Account] = {
      closeDateCheck(openDate, closeDate).map { d =>
        if (rate <= BigDecimal(0))
          throw new Exception(s"Interest rate $rate must be > 0")
        else
          SavingsAccount(no, name, rate, Some(d._1), d._2, balance)
      }
    }
    private def closeDateCheck(openDate: Option[Date], closeDate: Option[Date]): Try[(Date, Option[Date])] = {
      val od = openDate.getOrElse(today)
      closeDate.map { cd =>
        if (cd before od)
          Failure(new Exception(s"Close date[$cd] cannot be earlier than open date [$od]"))
        else Success((od, Some(cd)))
      }.getOrElse(Success(od, closeDate))
    }
  }

  sealed trait DayOfWeek {
    val value: Int

    override def toString: String = value match {
      case 1 => "Monday"
      case 2 => "Tuesday"
      case 3 => "Wednesday"
      case 4 => "Thursday"
      case 5 => "Friday"
      case 6 => "Saturday"
      case 7 => "Sunday"
    }
  }

  object DayOfWeek {
    private def unsafeDayOfWeek(day: Int) = new DayOfWeek {
      val value: Int = day
    }

    private val isValid: Int => Boolean = { i => i >= 1 && i <= 7 }

    def dayOfWeek(day: Int): Option[DayOfWeek] = if (isValid(day)) Some(unsafeDayOfWeek(day)) else None
  }
}
