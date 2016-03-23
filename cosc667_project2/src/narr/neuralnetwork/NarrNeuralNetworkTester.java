package narr.neuralnetwork;

import java.io.IOException;
import java.util.Scanner;

public class NarrNeuralNetworkTester {

	public static void main(String[] args) throws IOException {

		final Scanner keyboard = new Scanner(System.in);

		NarrNeuralNetworkClassifier neuralNetwork;

		final String root = "Resources/NeuralNetwork/";
		String path;

		String trainingFile;
		String inputFile;
		String outputFile;
		String validationFile;

		do {
			System.out.println("SELECT ONE:");
			System.out.println(" 1 Neural Network Part One");
			System.out.println(" 2 Neural Network Part Two");
			System.out.println(" 3 Neural Network Part Three");
			System.out.println(" 4 EXIT");
			final int menuChoice = keyboard.nextInt();
			keyboard.nextLine();

			switch (menuChoice) {
			case 1:
				path = "Part1/";
				trainingFile = "train.txt";
				inputFile = "test.txt";
				outputFile = "output.txt";

				neuralNetwork = new NarrNeuralNetworkClassifier();

				neuralNetwork.loadTrainingData(root + path + trainingFile);
				neuralNetwork.setParameters(4, 1000, 4539, 0.9);
				neuralNetwork.train();
				neuralNetwork.testData(root + path + inputFile, root + path
						+ outputFile);

				System.out.println("Output written to " + root + path
						+ outputFile);
				System.out.println();

				break;

			case 2:
				path = "Part2/";
				trainingFile = "train1.txt";
				inputFile = "test1.txt";
				outputFile = "output.txt";
				validationFile = "validate1.txt";

				neuralNetwork = new NarrNeuralNetworkClassifier();

				final String convertedTrainingFile = NarrConverter
						.convertSetOneTrainingData(root, path, trainingFile);
				final String convertedTestFile = NarrConverter
						.convertSetOneTestData(root, path, inputFile);

				neuralNetwork.loadTrainingData(convertedTrainingFile);
				neuralNetwork.setParameters(4, 1000, 4539, 0.9);
				neuralNetwork.train();
				neuralNetwork.testData(convertedTestFile, root + path
						+ outputFile);

				final String convertedOutputFile = NarrConverter
						.convertSetOneOutputData(root, path, outputFile);

				System.out.println("Output written to " + convertedOutputFile);
				System.out.println();

				final String convertedValidationFile = NarrConverter
						.convertSetOneValidationData(root, path, validationFile);

				neuralNetwork.validate(convertedValidationFile);

				neuralNetwork.writeTrainingErrorComparisonFile(root + path
						+ "trainingOutput.txt");

				final String convertedTrainingErrorComparisonFile = NarrConverter
						.convertSetOneTrainingErrorData(root, path,
								"trainingOutput.txt");

				neuralNetwork
						.calculateTrainingError(convertedTrainingErrorComparisonFile);
				break;

			case 4:
				System.exit(0);

			default:
				System.out.println("Invalid input.");
				System.out.println();
			}
		} while (true);

	}
}
