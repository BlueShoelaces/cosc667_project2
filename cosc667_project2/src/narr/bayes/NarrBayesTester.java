package narr.bayes;

import java.io.IOException;
import java.util.Scanner;

public class NarrBayesTester {

	public static void main(String[] args) throws IOException {

		final Scanner keyboard = new Scanner(System.in);
		NarrBayesClassifier bayesClassifier;

		final String root = "Resources/BayesClassifier/";
		String path;
		String trainingFile;
		String testFile;
		String classifiedFile;
		final String validationFile;

		do {
			System.out.println("SELECT ONE:");
			System.out.println(" 1 Bayes Part One");
			System.out.println(" 2 Bayes Part Two");
			System.out.println(" 3 Bayes Part Three");
			System.out.println(" 4 EXIT");
			final int menuChoice = keyboard.nextInt();
			keyboard.nextLine();

			switch (menuChoice) {
			case 1:
				path = "Part1/";
				trainingFile = "trainingfile.txt";
				testFile = "testfile.txt";
				classifiedFile = "classifiedfile.txt";

				bayesClassifier = new NarrBayesClassifier();

				buildModelAndClassifyRecords(bayesClassifier, root, path,
						trainingFile, testFile, classifiedFile);
				break;
			case 2:
				path = "Part2/";
				trainingFile = "train1.txt";
				testFile = "test1.txt";
				classifiedFile = "classified1.txt";

				bayesClassifier = new NarrBayesClassifier();

				buildModelAndClassifyRecords(bayesClassifier, root, path,
						trainingFile, testFile, classifiedFile);
				calculateValidationAndTrainingError(bayesClassifier);
				break;
			case 3:
				path = "Part3/";
				trainingFile = "train2.txt";
				testFile = "test2.txt";
				classifiedFile = "classified2.txt";

				bayesClassifier = new NarrBayesClassifier();

				buildModelAndClassifyRecords(bayesClassifier, root, path,
						trainingFile, testFile, classifiedFile);
				calculateValidationAndTrainingError(bayesClassifier);
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

	private static void calculateValidationAndTrainingError(
			NarrBayesClassifier bayesClassifier) throws IOException {
		bayesClassifier.validate();
		bayesClassifier.calculateTrainingError();
	}

	private static void buildModelAndClassifyRecords(
			NarrBayesClassifier bayesClassifier, final String root,
			String path, String trainingFile, String testFile,
			String classifiedFile) throws IOException {
		bayesClassifier.loadTrainingData(root + path + trainingFile);
		bayesClassifier.buildModel();
		bayesClassifier.classifyData(root + path + testFile, root + path
				+ classifiedFile);
	}

}
