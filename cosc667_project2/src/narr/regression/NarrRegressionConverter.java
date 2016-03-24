package narr.regression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NarrRegressionConverter {

	private int numberOfRecords;
	private int numberOfInputs;
	private int numberOfOutputs;

	private double[] inputMinimums;
	private double[] inputMaximums;
	private double[] outputMinimums;
	private double[] outputMaximums;

	private double[] inputRanges;
	private double[] outputRanges;
	private int numberOfTestRecords;
	private int numberOfValidationRecords;

	public void normalizeTrainingData(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		this.numberOfRecords = inFile.nextInt();
		this.numberOfInputs = inFile.nextInt();
		this.numberOfOutputs = inFile.nextInt();

		this.inputMinimums = new double[this.numberOfInputs];
		this.inputMaximums = new double[this.numberOfInputs];
		this.outputMinimums = new double[this.numberOfOutputs];
		this.outputMaximums = new double[this.numberOfOutputs];

		this.inputRanges = new double[this.numberOfInputs];
		this.outputRanges = new double[this.numberOfOutputs];

		outFile.println(this.numberOfRecords + " " + this.numberOfInputs + " "
				+ this.numberOfOutputs);

		this.normalize(originalFilename, convertedFilename,
				this.numberOfRecords, this.numberOfInputs, this.numberOfOutputs);

		inFile.close();
		outFile.close();
	}

	public void normalizeTestData(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		this.numberOfTestRecords = inFile.nextInt();
		final int numberOfTestOutputs = 0;

		outFile.println(this.numberOfTestRecords + " " + this.numberOfInputs
				+ " " + numberOfTestOutputs);

		this.normalize(originalFilename, convertedFilename,
				this.numberOfTestRecords, this.numberOfInputs,
				numberOfTestOutputs);

		inFile.close();
		outFile.close();
	}

	public void convertOutputToOriginalValues(String originalFilename,
			String convertedFilename) throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		double normalizedOutput;
		double convertedOutput;

		for (int record = 0; record < this.numberOfTestRecords; record++) {
			for (int output = 0; output < this.numberOfOutputs; output++) {
				normalizedOutput = inFile.nextDouble();
				convertedOutput = normalizedOutput * this.outputRanges[output]
						+ this.outputMinimums[output];

				outFile.print(convertedOutput + " ");
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

		this.numberOfValidationRecords = inFile.nextInt();
		final int numberOfValidationOutputs = this.numberOfOutputs;

		outFile.println(this.numberOfValidationRecords + " "
				+ this.numberOfInputs + " " + numberOfValidationOutputs);

		this.normalize(originalFilename, convertedFilename,
				this.numberOfValidationRecords, this.numberOfInputs,
				numberOfValidationOutputs);

		inFile.close();
		outFile.close();
	}

	private void normalize(String originalFilename, String convertedFilename,
			int numberOfRecords, int numberOfInputs, int numberOfOutputs)
					throws IOException {

		final Scanner inFile = new Scanner(new File(originalFilename));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				convertedFilename));

		final double[][] inputs = new double[numberOfRecords][numberOfInputs];
		final double[][] outputs = new double[numberOfRecords][numberOfOutputs];

		outFile.println(numberOfRecords + " " + numberOfInputs + " "
				+ numberOfOutputs);
		outFile.println();

		for (int record = 0; record < numberOfRecords; record++) {

			for (int input = 0; input < numberOfInputs; input++) {
				inputs[record][input] = inFile.nextDouble();
				if (inputs[record][input] < this.inputMinimums[input]) {
					this.inputMinimums[input] = inputs[record][input];
				}
				if (inputs[record][input] > this.inputMaximums[input]) {
					this.inputMaximums[input] = inputs[record][input];
				}
			}

			for (int output = 0; output < numberOfOutputs; output++) {
				outputs[record][output] = inFile.nextDouble();
				if (outputs[record][output] < this.outputMinimums[output]) {
					this.outputMinimums[output] = outputs[record][output];
				}
				if (outputs[record][output] > this.outputMaximums[output]) {
					this.outputMaximums[output] = outputs[record][output];
				}
			}
		}

		for (int i = 0; i < numberOfInputs; i++) {
			this.inputRanges[i] = this.inputMaximums[i] - this.inputMinimums[i];
		}

		for (int i = 0; i < numberOfOutputs; i++) {
			this.outputRanges[i] = this.outputMaximums[i]
					- this.outputMinimums[i];
		}

		final double[][] normalizedInputs = new double[numberOfRecords][numberOfInputs];
		final double[][] normalizedOutputs = new double[numberOfRecords][numberOfOutputs];

		for (int record = 0; record < numberOfRecords; record++) {

			for (int input = 0; input < numberOfInputs; input++) {
				normalizedInputs[record][input] = (inputs[record][input] - this.inputMinimums[input])
						/ this.inputRanges[input];
				outFile.print(normalizedInputs[record][input] + " ");
			}

			for (int output = 0; output < numberOfOutputs; output++) {
				normalizedOutputs[record][output] = (outputs[record][output] - this.outputMinimums[output])
						/ this.outputRanges[output];
				outFile.print(normalizedOutputs[record][output] + " ");
			}

			outFile.println();
		}

		inFile.close();
		outFile.close();
	}
}
