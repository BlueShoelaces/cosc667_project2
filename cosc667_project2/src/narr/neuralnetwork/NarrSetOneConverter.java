package narr.neuralnetwork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NarrSetOneConverter {
	public static String convertTrainingData(String root, String path,
			String filename) throws IOException {

		final String outputFile = root + path + "converted_" + filename;

		final Scanner inFile = new Scanner(new File(root + path + filename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		outFile.println("50 3 1");
		outFile.println();

		int score;
		double gpa;
		String grade;
		String classifyAs;

		double scoreToWrite;
		double gpaToWrite;
		double gradeToWrite;
		double classToWrite;

		for (int i = 0; i < 50; i++) {
			score = inFile.nextInt();
			gpa = inFile.nextDouble();
			grade = inFile.next();
			classifyAs = inFile.next();

			scoreToWrite = score / 100.0;
			gpaToWrite = gpa / 4.0;

			gradeToWrite = convertGradeToStandardValue(grade);
			classToWrite = convertClassToStandardValue(classifyAs);

			outFile.println("" + scoreToWrite + " " + gpaToWrite + " "
					+ gradeToWrite + " " + classToWrite);
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

		outFile.println("5");
		outFile.println();

		int score;
		double gpa;
		String grade;

		double scoreToWrite;
		double gpaToWrite;
		double gradeToWrite;

		for (int i = 0; i < 5; i++) {
			score = inFile.nextInt();
			gpa = inFile.nextDouble();
			grade = inFile.next();

			scoreToWrite = score / 100.0;
			gpaToWrite = gpa / 4.0;
			gradeToWrite = convertGradeToStandardValue(grade);

			outFile.println("" + scoreToWrite + " " + gpaToWrite + " "
					+ gradeToWrite);
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

		for (int i = 0; i < 5; i++) {
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

		outFile.println("10");
		outFile.println();

		int score;
		double gpa;
		String grade;
		String classifyAs;

		double scoreToWrite;
		double gpaToWrite;
		double gradeToWrite;
		double classToWrite;

		for (int i = 0; i < 10; i++) {
			score = inFile.nextInt();
			gpa = inFile.nextDouble();
			grade = inFile.next();
			classifyAs = inFile.next();

			scoreToWrite = score / 100.0;
			gpaToWrite = gpa / 4.0;

			gradeToWrite = convertGradeToStandardValue(grade);
			classToWrite = convertClassToStandardValue(classifyAs);

			outFile.println("" + scoreToWrite + " " + gpaToWrite + " "
					+ gradeToWrite + " " + classToWrite);
		}

		inFile.close();
		outFile.close();

		return outputFile;
	}

	public static String convertTrainingErrorData(String root,
			String path, String filename) throws IOException {

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

	private static double convertClassToStandardValue(String classifyAs) {
		double classToWrite;
		switch (classifyAs) {
		case "good":
			classToWrite = 1.0;
			break;
		case "average":
			classToWrite = 0.5;
			break;
		case "bad":
			classToWrite = 0.0;
			break;
		default:
			classToWrite = 0.0;
			break;
		}
		return classToWrite;
	}

	private static double convertGradeToStandardValue(String grade) {
		double gradeToWrite;
		switch (grade) {
		case "A":
			gradeToWrite = 1.0;
			break;
		case "B":
			gradeToWrite = 0.5;
			break;
		case "C":
			gradeToWrite = 0.0;
			break;
		default:
			gradeToWrite = 0.0;
			break;
		}
		return gradeToWrite;
	}

	private static String convertStandardValueToClass(double originalClass) {
		String classToWrite;
		if (originalClass <= 1.0 / 3.0) {
			classToWrite = "bad";
		} else if (originalClass <= 2.0 / 3.0) {
			classToWrite = "average";
		} else {
			classToWrite = "good";
		}
		return classToWrite;
	}
}
