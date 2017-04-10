package pt.ulisboa.tecnico.softeng.bank.domain;


import pt.ist.fenixframework.FenixFramework;

public class Account extends Account_Base {

	public Account(Bank bank, String IBAN) {
		setBank(bank);
		setIBAN(IBAN);
		setBalance(0);
	}

	public void deposit(int amount) {

		setBalance(getBalance() + amount);

	}

	public void withdraw(int amount) {

		setBalance(getBalance() - amount);

	}

	public void delete() {
		setBank(null);

		deleteDomainObject();
	}

}
