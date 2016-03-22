package narr.bayes;

import java.io.IOException;
import java.util.Scanner;

public class NarrBayesTester {

	public static void main(String[] args) throws IOException {

		final Scanner keyboard = new Scanner(System.in);
		NarrBayesClassifier classifier;

		final String root = "Resources/";
		String path;
		String trainingFile = "trainingfile.txt";
		String testFile = "testfile.txt";
		String classifiedFile = "classifiedfile.txt";
		String validationFile = "validationfile.txt";

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
				path = "BayesExample/Part1/";
				classifier = new NarrBayesClassifier();
				classifier.loadTrainingData(root + path + trainingFile);
				classifier.buildModel();
				classifier.classifyData(root + path + testFile, root + path
						+ classifiedFile);
				System.out.println("Output written to classifiedfile.txt");
				System.out.println();
				break;
			case 2:
				path = "BayesExample/Part2/";
				trainingFile = "train1.txt";
				testFile = "test1.txt";
				classifiedFile = "classified1.txt";
				validationFile = "validation1.txt";

				classifier = new NarrBayesClassifier();
				classifier.loadTrainingData(root + path + trainingFile);
				classifier.buildModel();
				classifier.classifyData(root + path + testFile, root + path
						+ classifiedFile);
				classifier.validate(root + path + validationFile);
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
