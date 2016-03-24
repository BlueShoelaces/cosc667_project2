package narr.regression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NarrSPRegressionConverter {

	private int numberOfInputs;
	private int numberOfOutputs;
	private int numberOfTestRecords;

	public void normalizeTrainingData(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		final int numberOfRecords = inFile.nextInt();
		this.numberOfInputs = inFile.nextInt();
		this.numberOfOutputs = inFile.nextInt();

		outFile.println(numberOfRecords + " " + this.numberOfInputs + " "
				+ this.numberOfOutputs);
		outFile.println();

		double value;
		double normalizedValue;

		for (int record = 0; record < numberOfRecords; record++) {
			for (int i = 0; i < this.numberOfInputs + this.numberOfOutputs; i++) {
				value = inFile.nextDouble();
				if (value < -2.0) {
					value = -2.0;
				} else if (value > 2.0) {
					value = 2.0;
				}
				normalizedValue = this.normalize(value);
				outFile.printf("%7.2f", normalizedValue);
			}

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	public void normalizeValidationData(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		final int numberOfRecords = inFile.nextInt();
		outFile.println(numberOfRecords);
		outFile.println();

		double value;
		double normalizedValue;

		for (int record = 0; record < numberOfRecords; record++) {
			for (int i = 0; i < this.numberOfInputs + this.numberOfOutputs; i++) {
				value = inFile.nextDouble();
				if (value < -2.0) {
					value = -2.0;
				} else if (value > 2.0) {
					value = 2.0;
				}
				normalizedValue = this.normalize(value);
				outFile.printf("%7.2f", normalizedValue);
			}

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	public void normalizeTestData(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		this.numberOfTestRecords = inFile.nextInt();
		outFile.println(this.numberOfTestRecords);
		outFile.println();

		double value;
		double normalizedValue;

		for (int record = 0; record < this.numberOfTestRecords; record++) {
			for (int i = 0; i < this.numberOfInputs; i++) {
				value = inFile.nextDouble();
				if (value < -2.0) {
					value = -2.0;
				} else if (value > 2.0) {
					value = 2.0;
				}
				normalizedValue = this.normalize(value);
				outFile.printf("%7.2f", normalizedValue);
			}

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	public void denormalizeOutputData(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		double convertedValue;
		double normalizedValue;

		for (int record = 0; record < this.numberOfTestRecords; record++) {
			for (int i = 0; i < this.numberOfOutputs; i++) {
				normalizedValue = inFile.nextDouble();
				convertedValue = this.denormalize(normalizedValue);
				outFile.printf("%7.2f", convertedValue);
			}

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	private double normalize(double value) {
		return (value + 2.0) / 4.0;
	}

	private double denormalize(double normalizedValue) {
		return normalizedValue * 4.0 - 2.0;
	}

}
