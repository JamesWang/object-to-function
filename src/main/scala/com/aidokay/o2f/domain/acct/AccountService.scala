package com.aidokay.o2f.domain.acct

import com.aidokay.o2f.domain.acct.models.{Account, Amount, Balance, today}
import java.util.Date
import scala.util.{Success, Failure, Try}

trait AccountService[Account, Amount, Balance] {
  def open(no: String, name: String, openData: Option[Date]): Try[Account]

  def close(account: Account, closeDate: Option[Date]): Try[Account]

  def debit(account: Account, amount: Amount): Try[Account]

  def credit(account: Account, amount: Amount): Try[Account]

  def balance(account: Account): Try[Balance]

  def transfer(from: Account, to: Account, amount: Amount): Try[(Account, Account, Amount)] = for {
    a <- debit(from, amount)
    b <- credit(to, amount)
  } yield (a, b, amount)
}

object AccountService extends AccountService[Account, Amount, Balance] {
  override def open(no: String, name: String, openDate: Option[Date]): Try[Account] = {
    if (no.isEmpty || name.isEmpty)
      Failure(new Exception(s"Account no or name cannot be blank"))
    else if (openDate.getOrElse(today) before today)
      Failure(new Exception(s"Cannot oepn account in the past"))
    else Success(Account(no, name, openDate.getOrElse(today)))
  }

  override def close(account: Account, closeDate: Option[Date]): Try[Account] = {
    val cd = closeDate.getOrElse(today)
    if (cd before account.dataOfOpen)
      Failure(new Exception(s"Close date $cd cannot be before opening date ${account.dataOfOpen}"))
    else Success(account.copy(dateOfClose = Some(cd)))
  }

  override def debit(account: Account, amount: Amount): Try[Account] = {
    if (account.balance.amount < amount)
      Failure(new Exception("Insufficient balance"))
    else
      Success(account.copy(balance = Balance(account.balance.amount - amount)))
  }

  override def credit(account: Account, amount: Amount): Try[Account] =
    Success(account.copy(balance = Balance(account.balance.amount + amount)))

  override def balance(account: Account): Try[Balance] = Success(account.balance)
}
