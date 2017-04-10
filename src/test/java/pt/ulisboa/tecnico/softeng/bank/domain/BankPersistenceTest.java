package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class BankPersistenceTest {
	@Test
	public void success() {
		atomicProcess1();
		atomicAssert1();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess1() {
		Bank bank = new Bank("Money", "BK01");

		Account account1 = new Account(bank , "12AB");
		Account account2 = new Account(bank , "34CD");
		SavingsAccount account3 = new SavingsAccount(bank , "56EF");


		account1.deposit(500);
		account1.withdraw(200);

		account2.deposit(2500);
		account2.withdraw(100);

		account3.deposit(200);
		account3.withdraw(200);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert1() {
		Bank bank = Bank.getBankByCode("BK01");

		Account account1 = bank.getAccountByIBAN("12AB");
		Account account2 = bank.getAccountByIBAN("34CD");
		Account account3 = bank.getAccountByIBAN("56EF");


		assertEquals("Money", bank.getName());
		assertEquals(bank, account1.getBank());

		assertEquals(300, account1.getBalance());
		assertEquals(2400, account2.getBalance());
		assertEquals(0, account3.getBalance());


		assertEquals(2700, bank.totalBalance());
	}

	@Test
	public void notMultiple100Test() {
		atomicProcess2();
		atomicAssert2();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess2() {
		Bank bank = new Bank("Money", "BK01");

		SavingsAccount account3 = new SavingsAccount(bank , "56EF");

		account3.deposit(73);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert2() {
		Bank bank = Bank.getBankByCode("BK01");

		Account account3 = bank.getAccountByIBAN("56EF");

		assertEquals(0, account3.getBalance());
	}

	@Test
	public void notWithdrawTotalBalanceTest() {
		atomicProcess3();
		atomicAssert3();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess3() {
		Bank bank = new Bank("Money", "BK01");

		SavingsAccount account4 = new SavingsAccount(bank, "78GH");

		account4.deposit(200);
		account4.withdraw(100);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert3() {
		Bank bank = Bank.getBankByCode("BK01");
		Account account4 = bank.getAccountByIBAN("78GH");

		assertEquals(200, account4.getBalance());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			bank.delete();
		}
	}

}
