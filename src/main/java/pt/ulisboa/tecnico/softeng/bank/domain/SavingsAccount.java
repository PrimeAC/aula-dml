package pt.ulisboa.tecnico.softeng.bank.domain;


public class SavingsAccount extends Account {

	public SavingsAccount(Bank bank, String IBAN) {
		super(bank, IBAN);
	}

	@Override
	public void deposit(int amount) {
		if(amount % 100 == 0){
			setBalance(getBalance() + amount);
		}
	}

	@Override
	public void withdraw(int amount) {
		if(amount == getBalance()){
			setBalance(getBalance() - amount);
		}
	}

}
