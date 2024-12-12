import java.io.IOException;
import java.text.ParseException;

public class Bank {

	private int swiftCode;
	private String address;
	private Customer customer;
	private Checking checking;
	private Savings savings;
	private String acc_type;

	private static int counter=0;
	public Bank() throws IOException, ParseException {
		counter++;
		//System.out.println("count:"+counter);
		checking =  new Checking();
		savings = new Savings();
		customer = new Customer(checking, savings) ;


	}


	//for verification of pin and card numbers
	public boolean manages(int pin, long CardNumber, String acc_type) throws IOException, ParseException {
		boolean b= this.customer.verifyPassword(pin, CardNumber, acc_type);

			return  b;

	}


	//for withrawing and depositing of money
	public boolean manages(double amount,  String type, String acc_type) throws IOException, ParseException {

		if (type.equalsIgnoreCase("withdraw")){
			return checking.withdraw(amount);
		} else if(type.equalsIgnoreCase("deposit")){
			if(acc_type.equalsIgnoreCase("checking"))
				return checking.deposit(amount,acc_type);
			else
				return savings.deposit(amount,acc_type);
		}else
			return false;

	}

	//for opening new account
	public String[] manages(String fname, String lname, String dob, String address, int pin_1) throws IOException, ParseException {
		checking.setAccount(fname,lname,dob,address,pin_1);
	return checking.getAccount();
	}

	public void maintains() {

	}

	public void print(int pin, long CardNumber) throws IOException, ParseException {
		checking.print(pin,CardNumber);
	}

}
