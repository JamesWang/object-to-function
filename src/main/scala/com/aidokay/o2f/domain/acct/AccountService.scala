package com.aidokay.o2f.domain.acct

import com.aidokay.o2f.domain.acct.models.{Account, Amount, Balance, CheckingAccount, today}

import java.util.Date
import scala.util.{Failure, Success, Try}

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
      Failure(new Exception(s"Cannot open account in the past"))
    else Account.checkingAccount(no, name, openDate)
  }

  override def close(account: Account, closeDate: Option[Date]): Try[Account] = {
    val cd = closeDate.getOrElse(today)
    if (cd before account.dateOfOpen.getOrElse(today))
      Failure(new Exception(s"Close date $cd cannot be before opening date ${account.dateOfOpen}"))
    else Success(account.asInstanceOf[CheckingAccount].copy(dateOfClose = Some(cd)))
  }

  override def debit(account: Account, amount: Amount): Try[Account] = {
    if (account.balance.amount < amount)
      Failure(new Exception("Insufficient balance"))
    else
      Success(account.asInstanceOf[CheckingAccount].copy(balance = Balance(account.balance.amount - amount)))
  }

  override def credit(account: Account, amount: Amount): Try[Account] =
    Success(account.asInstanceOf[CheckingAccount].copy(balance = Balance(account.balance.amount + amount)))

  override def balance(account: Account): Try[Balance] = Success(account.balance)
}
