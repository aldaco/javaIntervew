package crm;

import java.io.IOException;

public class AddressBookFixerApp {
	public static void main(String[] args) {
		AddressBookFixer fix = new AddressBookFixer();

		try {
			fix.fixAddressBook(
					// Change path for personal use.
					"C:\\Users\\MarthaAlexandra\\workspace\\CRM\\addressbook.txt",
					"C:\\Users\\MarthaAlexandra\\workspace\\CRM\\addressbook-fixed.txt");
		} catch (IOException e) {
			System.out.println("Could not read or write to files.");
			e.printStackTrace();
		}
	}
}
