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

		int numberOfHiddenNodes;
		int numberOfIterations;
		int seed;
		double learningRate;

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

				numberOfHiddenNodes = 4;
				numberOfIterations = 1000;
				seed = 4539;
				learningRate = 0.9;

				neuralNetwork = new NarrNeuralNetworkClassifier();

				neuralNetwork.loadTrainingData(root + path + trainingFile);
				neuralNetwork.setParameters(numberOfHiddenNodes, numberOfIterations, seed,
						learningRate);
				neuralNetwork.train();
				neuralNetwork.testData(root + path + inputFile, root + path + outputFile);

				System.out.println("Output written to " + root + path + outputFile);
				System.out.println();

				break;

			case 2:
				path = "Part2/";
				trainingFile = "train1.txt";
				inputFile = "test1.txt";
				outputFile = "output.txt";
				validationFile = "validate1.txt";

				numberOfHiddenNodes = 4;
				numberOfIterations = 1000;
				seed = 4539;
				learningRate = 0.9;

				neuralNetwork = new NarrNeuralNetworkClassifier();

				final String convertedTrainingFile = NarrSetOneConverter.convertTrainingData(root,
						path, trainingFile);
				final String convertedTestFile = NarrSetOneConverter.convertTestData(root, path,
						inputFile);

				neuralNetwork.loadTrainingData(convertedTrainingFile);
				neuralNetwork.setParameters(numberOfHiddenNodes, numberOfIterations, seed,
						learningRate);
				neuralNetwork.train();
				neuralNetwork.testData(convertedTestFile, root + path + outputFile);

				final String convertedOutputFile = NarrSetOneConverter.convertOutputData(root, path,
						outputFile);

				System.out.println("Output written to " + convertedOutputFile);
				System.out.println();

				final String convertedValidationFile = NarrSetOneConverter
						.convertValidationData(root, path, validationFile);

				neuralNetwork.validate(convertedValidationFile);

				neuralNetwork.writeTrainingErrorComparisonFile(root + path + "trainingOutput.txt");

				final String convertedTrainingErrorComparisonFile = NarrSetOneConverter
						.convertTrainingErrorData(root, path, "trainingOutput.txt");

				neuralNetwork.calculateTrainingError(convertedTrainingErrorComparisonFile);

				neuralNetwork.displayWeights();

				neuralNetwork.displayThetas();

				break;

			case 3:
				path = "Part3/";
				trainingFile = "train2.txt";
				inputFile = "test2.txt";
				outputFile = "output.txt";
				validationFile = "validate2.txt";

				numberOfHiddenNodes = 4;
				numberOfIterations = 1000;
				seed = 4539;
				learningRate = 0.9;

				neuralNetwork = new NarrNeuralNetworkClassifier();

				final String convertedSetTwoTrainingFile = NarrSetTwoConverter
						.convertTrainingData(root, path, trainingFile);

				final String convertedSetTwoTestFile = NarrSetTwoConverter.convertTestData(root,
						path, inputFile);

				neuralNetwork.loadTrainingData(convertedSetTwoTrainingFile);
				neuralNetwork.setParameters(numberOfHiddenNodes, numberOfIterations, seed,
						learningRate);
				neuralNetwork.train();
				neuralNetwork.testData(convertedSetTwoTestFile, root + path + outputFile);

				final String convertedSetTwoOutputFile = NarrSetTwoConverter.convertOutputData(root,
						path, outputFile);

				System.out.println("Output written to " + convertedSetTwoOutputFile);
				System.out.println();

				final String convertedSetTwoValidationFile = NarrSetTwoConverter
						.convertValidationData(root, path, validationFile);

				neuralNetwork.validate(convertedSetTwoValidationFile);

				neuralNetwork.writeTrainingErrorComparisonFile(root + path + "trainingOutput.txt");

				final String convertedSetTwoTrainingErrorComparisonFile = NarrSetTwoConverter
						.convertTrainingErrorData(root, path, "trainingOutput.txt");

				neuralNetwork.calculateTrainingError(convertedSetTwoTrainingErrorComparisonFile);

				neuralNetwork.displayWeights();

				neuralNetwork.displayThetas();

				break;
			case 4:
				keyboard.close();
				System.exit(0);

			default:
				System.out.println("Invalid input.");
				System.out.println();
			}
		} while (true);

	}
}
