package org.availity.codingexercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnrollmentProcessor {

	public static void processFile(String inputFile) {
		String delimiter = ",";
		Map<String, Map<String, Enrollee>> enrolleesMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

			br.lines().skip(1) // Skip header line if present

					// Each line is split by a delimiter (,) to separate the fields in the CSV.
					.map(line -> line.split(delimiter))
					// This array of strings (data) is then mapped to a new User object. Each User
					// object represents a record from the file,
					// with properties for user ID, first name, last name, version, and insurance
					// company.
					.map(data -> new Enrollee(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]))
					// This part processes each User object. It uses a map (UsersMap) where each key
					// is an insurance company and each value is another map of user IDs to User
					// objects. The computeIfAbsent method ensures that there is a map for each
					// insurance company; if not, it creates one.


					.forEach(enrollee -> enrolleesMap
							.computeIfAbsent(enrollee.getInsuranceCompany(), k -> new HashMap<>())
							//For each User, the inner map for their insurance company is updated with the merge function.
							//This function checks if there is already a User with the same ID in the map:
							//If not, the current User is added.
							//If there is, it compares the versions of the existing User and the new one, keeping only the one with the higher version.
							//This effectively removes duplicates, retaining only the record with the highest version for each user ID within each insurance company.
							.merge(enrollee.getUserId(), enrollee, (existing,
									newOne) -> existing.getVersion() > newOne.getVersion() ? existing : newOne));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		// Process each insurance company's enrollees, sort them, and write to separate files
		enrolleesMap.forEach((insuranceCompany, users) -> {
			List<Enrollee> sortedEnrollees = users.values().stream()
					.sorted(Comparator.comparing(Enrollee::getLastName).thenComparing(Enrollee::getFirstName))
					.collect(Collectors.toList());

			String fileName = insuranceCompany.replaceAll("\\s+", "_") + ".csv";
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
				writer.println("User Id,First Name,Last Name,Version,Insurance Company");
				sortedEnrollees.forEach(enrollee -> writer.println(enrollee.toCsvString()));// files will be created in the project root folder
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		});
	}

	public static void main(String[] args) {
		String inputFile = "/Users/sathyajithjaligama/Desktop/Users.csv"; // Replace with the path to your input CSV file
		processFile(inputFile);
	}

	static class Enrollee {
		private String userId;
		private String firstName;
		private String lastName;
		private int version;
		private String insuranceCompany;

		public Enrollee(String userId, String firstName, String lastName, int version, String insuranceCompany) {
			this.userId = userId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.version = version;
			this.insuranceCompany = insuranceCompany;
		}

		public String getUserId() {
			return userId;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public int getVersion() {
			return version;
		}

		public String getInsuranceCompany() {
			return insuranceCompany;
		}

		public String toCsvString() {
			return String.join(",", userId, firstName, lastName, String.valueOf(version), insuranceCompany);
		}
	}
}