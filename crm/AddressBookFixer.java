package crm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class AddressBookFixer {
	void fixAddressBook(String inputFilePath, String outputFilePath) throws IOException {
		HashMap<String, Customer> customers = new HashMap<String, Customer>(1000000);

		// Begin reading of input file and store Customers into map.
		
		// Unicode support.
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), "UTF-8"));
		String line;
		
		Customer current;

		while ((line = br.readLine()) != null) {
			// fields[0] is always the name, the rest (if any) are data fields.
			final String[] fields = line.split("#");

			// Search customer by name in map.
			if (!customers.containsKey(fields[0])) {
				// Create Customer and add to map if it hasn't been added before.
				customers.put(fields[0], new Customer());
			}

			// Get current Customer object.
			current = customers.get(fields[0]);

			// Cycle through all found fields for current input line.
			for (int i = 1; i < fields.length; ++i) {
				// Get field index from input String.
				final int ix = Character.getNumericValue(fields[i].charAt(0)) - 1;

				if (current.data[ix] == null) {
					// Remove field index from String and assign to corresponding
					// field in Customer class if hasn't been added before.
					current.data[ix] = fields[i].substring(2);
				}
			}
		}

		br.close();
		
		if(customers.isEmpty())
		{
			System.out.println("Input file is empty.");
			
			return;
		}
		
		// Begin writing of output (fixed) file with data in customers map.

		// Unicode support.
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath), "UTF-8"));

		// Cycle through all found customers, and write complete customer line to output file.
		for (Map.Entry<String, Customer> entry : customers.entrySet()) {
			StringBuilder docBuilder = new StringBuilder(entry.getKey());

			current = entry.getValue();

			for (int i = 0; i < Customer.kNumFields; ++i) {
				docBuilder.append('#');
				docBuilder.append(Integer.toString(i + 1));
				docBuilder.append('|');
				docBuilder.append(current.data[i]);
			}

			docBuilder.append(System.lineSeparator());
			bw.write(docBuilder.toString());
		}
		
		bw.close();
	}
}

class Customer {
	/*
	 * index 0 = address index 1 = town index 2 = post code index 3 = phone
	 * number
	 */

	public static final int kNumFields = 4;

	String[] data = new String[kNumFields];

	public Customer() {
		for (int i = 0; i < kNumFields; ++i) {
			data[i] = null;
		}
	}
}