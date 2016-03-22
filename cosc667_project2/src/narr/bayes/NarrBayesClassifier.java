package narr.bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class NarrBayesClassifier {

	private class NarrRecord {
		private final int[] attributes;
		private final int className;

		private NarrRecord(int[] attributes, int className) {
			this.attributes = attributes;
			this.className = className;
		}
	}

	private ArrayList<NarrRecord> records;
	private int[] attributeValues;

	private int numberRecords;
	private int numberAttributes;
	private int numberClasses;

	double[] classTable;
	double[][][] table;

	private String[][] labelConversionArray;
	private int[] labelConversionPointers;

	public NarrBayesClassifier() {
		this.records = null;
		this.attributeValues = null;

		this.numberRecords = 0;
		this.numberAttributes = 0;
		this.numberClasses = 0;

		this.labelConversionArray = null;

		this.classTable = null;
		this.table = null;
	}

	public void loadTrainingData(String trainingFile) throws IOException {
		final Scanner inFile = new Scanner(new File(trainingFile));

		this.numberRecords = inFile.nextInt();
		this.numberAttributes = inFile.nextInt();
		this.numberClasses = inFile.nextInt();

		this.labelConversionArray = new String[this.numberAttributes + 1][];
		this.labelConversionPointers = new int[this.numberAttributes + 1];

		this.attributeValues = new int[this.numberAttributes];
		for (int i = 0; i < this.numberAttributes; i++) {
			this.attributeValues[i] = inFile.nextInt();
			this.labelConversionArray[i] = new String[this.attributeValues[i]];
		}

		this.labelConversionArray[this.numberAttributes] = new String[this.numberClasses];

		this.records = new ArrayList<NarrRecord>();

		for (int i = 0; i < this.numberRecords; i++) {
			final int[] attributeArray = new int[this.numberAttributes];

			for (int j = 0; j < this.numberAttributes; j++) {
				final String label = inFile.next();
				attributeArray[j] = this.convert(label, j + 1);
			}

			final String label = inFile.next();
			final int className = this
					.convert(label, this.numberAttributes + 1);

			final NarrRecord record = new NarrRecord(attributeArray, className);

			this.records.add(record);

		}

		inFile.close();
	}

	public void buildModel() {
		this.fillClassTable();

		this.fillProbabilityTable();
	}

	private void fillClassTable() {
		this.classTable = new double[this.numberClasses];

		for (int i = 0; i < this.numberClasses; i++) {
			this.classTable[i] = 0;
		}

		for (int i = 0; i < this.numberRecords; i++) {
			this.classTable[this.records.get(i).className - 1] += 1;
		}

		for (int i = 0; i < this.numberClasses; i++) {
			this.classTable[i] /= this.numberRecords;
		}
	}

	private void fillProbabilityTable() {
		this.table = new double[this.numberAttributes][][];

		for (int i = 0; i < this.numberAttributes; i++) {
			this.fill(i + 1);
		}
	}

	private void fill(int attribute) {
		final int attributeValues = this.attributeValues[attribute - 1];

		this.table[attribute - 1] = new double[this.numberClasses][attributeValues];

		for (int i = 0; i < this.numberClasses; i++) {
			for (int j = 0; j < attributeValues; j++) {
				this.table[attribute - 1][i][j] = 0;
			}
		}

		for (int i = 0; i < this.numberRecords; i++) {
			final int j = this.records.get(i).className - 1;
			final int k = this.records.get(i).attributes[attribute - 1] - 1;
			this.table[attribute - 1][j][k] += 1;
		}

		for (int i = 0; i < this.numberClasses; i++) {
			for (int j = 0; j < attributeValues; j++) {
				final double value = (this.table[attribute - 1][i][j] + 1)
						/ (this.classTable[i] * this.numberRecords + attributeValues);
				this.table[attribute - 1][i][j] = value;
			}
		}
	}

	private int classify(int[] attributes) {
		double maxProbability = 0;
		int maxClass = -1;

		for (int i = 0; i < this.numberClasses; i++) {
			final double probability = this.findProbability(i + 1, attributes);

			if (probability > maxProbability) {
				maxProbability = probability;
				maxClass = i;
			}
		}

		return maxClass + 1;
	}

	private double findProbability(int className, int[] attributes) {
		double value;
		double product = 1;

		for (int i = 0; i < this.numberAttributes; i++) {
			value = this.table[i][className - 1][attributes[i] - 1];
			product = product * value;
		}

		return product * this.classTable[className - 1];
	}

	public void classifyData(String testFile, String classifiedFile)
			throws IOException {
		final Scanner inFile = new Scanner(new File(testFile));
		final PrintWriter outFile = new PrintWriter(new FileWriter(
				classifiedFile));

		final int numberRecords = inFile.nextInt();

		for (int i = 0; i < numberRecords; i++) {
			final int[] attributeArray = new int[this.numberAttributes];

			for (int j = 0; j < this.numberAttributes; j++) {
				final String label = inFile.next();
				attributeArray[j] = this.convert(label, j + 1);
			}

			final int className = this.classify(attributeArray);

			final String label = this.convert(className);
			outFile.println(label);
		}

		inFile.close();
		outFile.close();
	}

	public void validate(String validationFile) throws IOException {
		// for each record
		for (int i = 0; i < this.numberRecords; i++) {
			final String tempTrainingFile = "tempTrain" + i + ".txt";

			// write temp training file with all but this record
			final PrintWriter trainingOutFile = new PrintWriter(new FileWriter(
					tempTrainingFile));

			trainingOutFile.println("" + (this.numberRecords - 1) + " "
					+ this.numberAttributes + " " + this.numberClasses);
			for (int j = 0; j < this.numberAttributes; j++) {
				trainingOutFile.print(this.attributeValues[j] + " ");
			}
			trainingOutFile.println();

			for (int j = 0; j < this.numberRecords; j++) {
				if (j == i) {
					continue;
				} else {
					for (int k = 0; k < this.numberAttributes; k++) {
						trainingOutFile.print(this.records.get(j).attributes[k]
								+ " ");
					}
					trainingOutFile.println(this.records.get(j).className);
				}
			}

			final String tempValidationFile = "tempValidation" + i + ".txt";
			// write this record to validation file
			final PrintWriter validationOutFile = new PrintWriter(
					new FileWriter(tempValidationFile));

			validationOutFile.println("1");
			for (int j = 0; j < this.numberAttributes; j++) {
				validationOutFile
						.print(this.records.get(i).attributes[j] + " ");
			}
			validationOutFile.println(this.records.get(i).className);

			trainingOutFile.close();
			validationOutFile.close();

			// build model
			final NarrBayesClassifier validationClassifier = new NarrBayesClassifier();
			validationClassifier.loadTrainingData(tempTrainingFile);
			validationClassifier.buildModel();

			this.validateOneRecord(tempValidationFile);

			File file = new File(tempTrainingFile);
			file.delete();
			file = new File(tempValidationFile);
			file.delete();
		}
	}

	private void validateOneRecord(String validationFile)
			throws FileNotFoundException {
		final Scanner inFile = new Scanner(new File(validationFile));

		final int numberRecords = inFile.nextInt();

		int numberErrors = 0;

		for (int i = 0; i < numberRecords; i++) {
			final int[] attributeArray = new int[this.numberAttributes];

			for (int j = 0; j < this.numberAttributes; j++) {
				final String label = inFile.next();
				System.out.println("column: " + (j + 1));
				attributeArray[j] = this.convert(label, j + 1);
			}

			final int predictedClass = this.classify(attributeArray);

			final String label = inFile.next();
			final int actualClass = this.convert(label,
					this.numberAttributes + 1);

			if (predictedClass != actualClass) {
				numberErrors++;
			}
		}

		final double errorRate = 100.0 * numberErrors / numberRecords;
		System.out.println(errorRate + "% error");

		inFile.close();
	}

	private int convert(String label, int column) {
		int loopControl;

		if (column > this.numberAttributes) {
			loopControl = this.numberClasses;
		} else {
			loopControl = this.attributeValues[column - 1];
		}

		for (int i = 0; i < loopControl; i++) {
			if (label.equals(this.labelConversionArray[column - 1][i])) {
				return i + 1;
			}
		}

		this.labelConversionArray[column - 1][this.labelConversionPointers[column - 1]] = label;
		return ++this.labelConversionPointers[column - 1];
	}

	private String convert(int value) {

		return this.labelConversionArray[this.numberAttributes][value - 1];
	}
}
