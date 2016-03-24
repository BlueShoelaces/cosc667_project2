package narr.regression;

import java.io.IOException;
import java.util.Scanner;

import narr.neuralnetwork.NarrNeuralNetworkClassifier;

public class NarrRegressionTester {

	public static void main(String[] args) throws IOException {

		final Scanner keyboard = new Scanner(System.in);

		final String root = "Resources/Regression/";
		String path;
		int iteration;

		String trainingFile;
		String inputFile;
		String outputFile;
		String validationFile;

		int numberOfHiddenNodes;
		int numberOfIterations;
		int seed;
		double learningRate;

		do {
			System.out.println("SELECT ONE:");
			System.out.println(" 1 Regression Part One");
			System.out.println(" 2 Regression Part Two");
			System.out.println(" 3 EXIT");
			final int menuChoice = keyboard.nextInt();
			keyboard.nextLine();

			switch (menuChoice) {
			case 1:
				for (iteration = 1; iteration <= 5; iteration++) {
					path = "Part1/Set" + iteration + "/";

					numberOfHiddenNodes = 4;
					numberOfIterations = 1000;
					seed = 4539;
					learningRate = 0.9;

					doAllTheThings(root, path, iteration, numberOfHiddenNodes, numberOfIterations,
							seed, learningRate);
				}
				break;

			case 2:
				path = "Part2/";
				trainingFile = root + path + "sp_training.txt";
				inputFile = root + path + "sp_test.txt";
				validationFile = root + path + "sp_validation.txt";
				outputFile = root + path + "sp_output.txt";

				final String normalizedTrainingFile = root + path + "normalized_sp_training.txt";
				final String normalizedValidationFile = root + path
						+ "normalized_sp_validation.txt";
				final String normalizedInputFile = root + path + "normalized_sp_test.txt";
				final String normalizedOutputFile = root + path + "normalized_output.txt";

				final NarrSPRegressionConverter converter = new NarrSPRegressionConverter();
				converter.normalizeTrainingData(trainingFile, normalizedTrainingFile);
				converter.normalizeValidationData(validationFile, normalizedValidationFile);
				converter.normalizeTestData(inputFile, normalizedInputFile);

				final NarrNeuralNetworkClassifier neuralNetwork = new NarrNeuralNetworkClassifier();

				numberOfHiddenNodes = 4;
				seed = 5678;

				neuralNetwork.loadTrainingData(normalizedTrainingFile);
				neuralNetwork.setParameters(numberOfHiddenNodes, 0, seed, 0);
				neuralNetwork.trainUsingRegression();
				neuralNetwork.validate(normalizedValidationFile);
				neuralNetwork.testData(normalizedInputFile, normalizedOutputFile);

				converter.denormalizeOutputData(normalizedOutputFile, outputFile);

				System.out.println("Output written to " + outputFile);
				System.out.println();

				break;

			case 3:
				keyboard.close();
				System.exit(0);

			default:
				System.out.println("Invalid input.");
				System.out.println();
			}
		} while (true);

	}

	private static void doAllTheThings(final String root, String path, int iteration,
			int numberOfHiddenNodes, int numberOfIterations, int seed, double learningRate)
					throws IOException {

		NarrNeuralNetworkClassifier neuralNetwork;

		String trainingFile;
		String inputFile;
		String outputFile;
		String validationFile;

		NarrRegressionConverter converter;

		trainingFile = root + path + "train" + iteration;
		inputFile = root + path + "test" + iteration;
		outputFile = root + path + "output" + iteration + ".txt";
		validationFile = root + path + "validate" + iteration;

		final String convertedTrainingFile = root + path + "normalized_train" + iteration + ".txt";
		final String convertedTestFile = root + path + "normalized_test" + iteration + ".txt";
		final String denormalizedOutputFile = root + path + "converted_output" + iteration + ".txt";
		final String convertedValidationFile = root + path + "normalized_validate" + iteration
				+ ".txt";

		converter = new NarrRegressionConverter();
		converter.normalizeTrainingData(trainingFile, convertedTrainingFile);
		converter.normalizeTestData(inputFile, convertedTestFile);

		neuralNetwork = new NarrNeuralNetworkClassifier();

		neuralNetwork.loadTrainingData(convertedTrainingFile);
		neuralNetwork.setParameters(numberOfHiddenNodes, numberOfIterations, seed, learningRate);
		neuralNetwork.trainUsingRegression();
		neuralNetwork.testData(convertedTestFile, outputFile);

		converter.convertOutputToOriginalValues(outputFile, denormalizedOutputFile);

		System.out.println("Output written to " + denormalizedOutputFile);
		System.out.println();

		converter.normalizeValidationData(validationFile, convertedValidationFile);

		neuralNetwork.validate(convertedValidationFile);
	}

}
