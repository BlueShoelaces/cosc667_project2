package narr.neuralnetwork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NarrSetTwoConverter {

	public static String convertTrainingData(String root, String path,
			String filename) throws IOException {

		final String outputFile = root + path + "converted_" + filename;

		final Scanner inFile = new Scanner(new File(root + path + filename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		outFile.println("80 5 1");
		outFile.println();

		int creditScore;
		int income;
		int age;
		String sex;
		String maritalStatus;
		String atRiskClass;

		double creditScoreToWrite;
		double incomeToWrite;
		double ageToWrite;
		double sexToWrite;
		double maritalStatusToWrite;
		double classToWrite;

		for (int i = 0; i < 80; i++) {
			creditScore = inFile.nextInt();
			income = inFile.nextInt();
			age = inFile.nextInt();
			sex = inFile.next();
			maritalStatus = inFile.next();
			atRiskClass = inFile.next();

			creditScoreToWrite = convertCreditScoreToStandard(creditScore);
			incomeToWrite = convertIncomeToStandard(income);
			ageToWrite = convertAgeToStandard(age);
			sexToWrite = convertSexToStandard(sex);
			maritalStatusToWrite = convertMaritalStatusToStandard(maritalStatus);
			classToWrite = convertClassToStandard(atRiskClass);

			outFile.println("" + creditScoreToWrite + " " + incomeToWrite + " "
					+ ageToWrite + " " + sexToWrite + " "
					+ maritalStatusToWrite + " " + classToWrite);
		}

		inFile.close();
		outFile.close();

		return outputFile;
	}

	public static String convertTestData(String root, String path,
			String filename) throws IOException {

		final String outputFile = root + path + "converted_" + filename;

		final Scanner inFile = new Scanner(new File(root + path + filename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		outFile.println("8");
		outFile.println();

		int creditScore;
		int income;
		int age;
		String sex;
		String maritalStatus;

		double creditScoreToWrite;
		double incomeToWrite;
		double ageToWrite;
		double sexToWrite;
		double maritalStatusToWrite;

		for (int i = 0; i < 8; i++) {
			creditScore = inFile.nextInt();
			income = inFile.nextInt();
			age = inFile.nextInt();
			sex = inFile.next();
			maritalStatus = inFile.next();

			creditScoreToWrite = convertCreditScoreToStandard(creditScore);
			incomeToWrite = convertIncomeToStandard(income);
			ageToWrite = convertAgeToStandard(age);
			sexToWrite = convertSexToStandard(sex);
			maritalStatusToWrite = convertMaritalStatusToStandard(maritalStatus);

			outFile.println("" + creditScoreToWrite + " " + incomeToWrite + " "
					+ ageToWrite + " " + sexToWrite + " "
					+ maritalStatusToWrite);
		}

		inFile.close();
		outFile.close();

		return outputFile;
	}

	public static String convertOutputData(String root, String path,
			String filename) throws IOException {

		final String outputFile = root + path + "converted_" + filename;

		final Scanner inFile = new Scanner(new File(root + path + filename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		double originalClass;
		String classToWrite;

		for (int i = 0; i < 8; i++) {
			originalClass = inFile.nextDouble();

			classToWrite = convertStandardValueToClass(originalClass);

			outFile.println(classToWrite);
		}

		inFile.close();
		outFile.close();

		return outputFile;
	}

	public static String convertValidationData(String root, String path,
			String filename) throws IOException {
		final String outputFile = root + path + "converted_" + filename;

		final Scanner inFile = new Scanner(new File(root + path + filename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		outFile.println("15");
		outFile.println();

		int creditScore;
		int income;
		int age;
		String sex;
		String maritalStatus;
		String atRiskClass;

		double creditScoreToWrite;
		double incomeToWrite;
		double ageToWrite;
		double sexToWrite;
		double maritalStatusToWrite;
		double classToWrite;

		for (int i = 0; i < 15; i++) {
			creditScore = inFile.nextInt();
			income = inFile.nextInt();
			age = inFile.nextInt();
			sex = inFile.next();
			maritalStatus = inFile.next();
			atRiskClass = inFile.next();

			creditScoreToWrite = convertCreditScoreToStandard(creditScore);
			incomeToWrite = convertIncomeToStandard(income);
			ageToWrite = convertAgeToStandard(age);
			sexToWrite = convertSexToStandard(sex);
			maritalStatusToWrite = convertMaritalStatusToStandard(maritalStatus);
			classToWrite = convertClassToStandard(atRiskClass);

			outFile.println("" + creditScoreToWrite + " " + incomeToWrite + " "
					+ ageToWrite + " " + sexToWrite + " "
					+ maritalStatusToWrite + " " + classToWrite);
		}

		inFile.close();
		outFile.close();

		return outputFile;
	}

	public static String convertTrainingErrorData(String root, String path,
			String filename) throws IOException {

		final String outputFile = root + path + "converted_" + filename;

		final Scanner inFile = new Scanner(new File(root + path + filename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		final int numberOfRecords = inFile.nextInt();
		final int numberOfOutputs = inFile.nextInt();

		String actualClass;
		String predictedClass;

		for (int i = 0; i < numberOfRecords; i++) {
			for (int j = 0; j < numberOfOutputs; j++) {

				actualClass = convertStandardValueToClass(inFile.nextDouble());
				predictedClass = convertStandardValueToClass(inFile
						.nextDouble());

				outFile.print(actualClass + " " + predictedClass);
			}
			outFile.println();
		}

		inFile.close();
		outFile.close();

		return outputFile;
	}

	private static double convertCreditScoreToStandard(int creditScore) {
		return (creditScore - 500) / 400.0;
	}

	private static double convertIncomeToStandard(int income) {
		return (income - 30) / 60.0;
	}

	private static double convertAgeToStandard(int age) {
		return (age - 30) / 50.0;
	}

	private static double convertSexToStandard(String sex) {
		return (sex.equals("female") ? 1.0 : 0.0);
	}

	private static double convertMaritalStatusToStandard(String maritalStatus) {
		switch (maritalStatus) {
		case "single":
			return 1.0;
		case "divorced":
			return 0.5;
		case "married":
			return 0.0;
		default:
			return 0.0;
		}
	}

	private static double convertClassToStandard(String atRiskClass) {
		switch (atRiskClass) {
		case "low":
			return 1.0;
		case "medium":
			return 2.0 / 3.0;
		case "high":
			return 1.0 / 3.0;
		case "undetermined":
			return 0.0;
		default:
			return 0.0;
		}
	}

	private static String convertStandardValueToClass(double classValue) {
		if (classValue <= 0.25) {
			return "undetermined";
		} else if (classValue <= 0.5) {
			return "high";
		} else if (classValue <= 0.75) {
			return "medium";
		} else {
			return "low";
		}
	}
}
