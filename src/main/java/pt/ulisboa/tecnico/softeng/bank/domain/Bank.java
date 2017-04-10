package pt.ulisboa.tecnico.softeng.bank.domain;

import pt.ist.fenixframework.FenixFramework;

public class Bank extends Bank_Base {

	public Bank(String name, String code) {
		setName(name);
		setCode(code);

		FenixFramework.getDomainRoot().addBank(this);
	}

	public void delete() {
		setRoot(null);

		for (Account account : getAccountSet()) {
			account.delete();
		}
		deleteDomainObject();
	}

	public static Bank getBankByCode(String code) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			if (bank.getCode().equals(code)) {
				return bank;
			}
		}
		return null;
	}

	public static Account getAccountByIBAN(String IBAN) {
		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			for (Account account : bank.getAccountSet()) {
				if (account.getIBAN().equals(IBAN)) {
					return account;
				}
			}
		}
		return null;
	}

	public static int totalBalance() {

		int total = 0;

		for (Bank bank : FenixFramework.getDomainRoot().getBankSet()) {
			for (Account account : bank.getAccountSet()) {
				if (account.getBalance() > 0) {
					total = total + account.getBalance();
				}
			}
		}
		return total;
	}

}
