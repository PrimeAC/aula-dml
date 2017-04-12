package pt.ulisboa.tecnico.softeng.bank.domain;


public class SavingsAccount extends SavingsAccount_Base {

	public SavingsAccount() {
		super();
	}

	public SavingsAccount(Bank bank, String IBAN) {
		super();
		init(bank, IBAN);
	}

	protected void init(Bank bank, String IBAN) {
		super.init(bank, IBAN);
	}


	public void deposit(int amount) {
		if(amount % 100 == 0){
			setBalance(getBalance() + amount);
		}
	}


	public void withdraw(int amount) {
		if(amount == getBalance()){
			setBalance(getBalance() - amount);
		}
	}

}
