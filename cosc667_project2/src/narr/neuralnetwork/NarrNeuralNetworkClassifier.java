package narr.neuralnetwork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NarrNeuralNetworkClassifier {

	private class NarrRecord {
		private final double[] input;
		private final double[] output;

		private NarrRecord(double[] input, double[] output) {
			this.input = input;
			this.output = output;
		}
	}

	private int numberOfRecords;
	private int numberOfInputs;
	private int numberOfOutputs;

	private int numberOfHiddenNodes;
	private int numberOfIterations;
	private int seed;
	private double learningRate;

	private ArrayList<NarrRecord> records;

	private double[] input;
	private double[] outputAtHiddenNodes;
	private double[] outputAtOutputNodes;

	private double[] errorsAtHiddenNodes;
	private double[] errorsAtOutputNodes;

	private double[] thetasAtHiddenNodes;
	private double[] thetasAtOutputNodes;

	private double[][] weightsInputHidden;
	private double[][] weightsHiddenOutput;

	public NarrNeuralNetworkClassifier() {
		this.numberOfRecords = 0;
		this.numberOfInputs = 0;
		this.numberOfOutputs = 0;
		this.numberOfHiddenNodes = 0;
		this.numberOfIterations = 0;
		this.seed = 0;
		this.learningRate = 0;

		this.records = null;
		this.input = null;
		this.outputAtHiddenNodes = null;
		this.outputAtOutputNodes = null;
		this.errorsAtHiddenNodes = null;
		this.errorsAtOutputNodes = null;
		this.thetasAtHiddenNodes = null;
		this.thetasAtOutputNodes = null;
		this.weightsInputHidden = null;
		this.weightsHiddenOutput = null;
	}

	public void loadTrainingData(String trainingFile) throws IOException {
		final Scanner inFile = new Scanner(new File(trainingFile));

		this.numberOfRecords = inFile.nextInt();
		this.numberOfInputs = inFile.nextInt();
		this.numberOfOutputs = inFile.nextInt();

		this.records = new ArrayList<NarrRecord>();

		for (int i = 0; i < this.numberOfRecords; i++) {
			final double[] input = new double[this.numberOfInputs];
			for (int j = 0; j < this.numberOfInputs; j++) {
				input[j] = inFile.nextDouble();
			}

			final double[] output = new double[this.numberOfOutputs];
			for (int j = 0; j < this.numberOfOutputs; j++) {
				output[j] = inFile.nextDouble();
			}

			final NarrRecord record = new NarrRecord(input, output);

			this.records.add(record);
		}

		inFile.close();
	}

	public void setParameters(int numberOfHiddenNodes, int numberOfIterations,
			int seed, double learningRate) {
		this.numberOfHiddenNodes = numberOfHiddenNodes;
		this.numberOfIterations = numberOfIterations;
		this.seed = seed;
		this.learningRate = learningRate;

		final Random random = new Random(seed);

		this.input = new double[this.numberOfInputs];
		this.outputAtHiddenNodes = new double[this.numberOfHiddenNodes];
		this.outputAtOutputNodes = new double[this.numberOfOutputs];

		this.errorsAtHiddenNodes = new double[this.numberOfHiddenNodes];
		this.errorsAtOutputNodes = new double[this.numberOfOutputs];

		this.thetasAtHiddenNodes = new double[this.numberOfHiddenNodes];
		for (int i = 0; i < this.numberOfHiddenNodes; i++) {
			this.thetasAtHiddenNodes[i] = 2 * random.nextDouble() - 1;
		}

		this.thetasAtOutputNodes = new double[this.numberOfOutputs];
		for (int i = 0; i < this.numberOfOutputs; i++) {
			this.thetasAtOutputNodes[i] = 2 * random.nextDouble() - 1;
		}

		this.weightsInputHidden = new double[this.numberOfInputs][this.numberOfHiddenNodes];
		for (int i = 0; i < this.numberOfInputs; i++) {
			for (int j = 0; j < this.numberOfHiddenNodes; j++) {
				this.weightsInputHidden[i][j] = 2 * random.nextDouble() - 1;
			}
		}

		this.weightsHiddenOutput = new double[this.numberOfHiddenNodes][this.numberOfOutputs];
		for (int i = 0; i < this.numberOfHiddenNodes; i++) {
			for (int j = 0; j < this.numberOfOutputs; j++) {
				this.weightsHiddenOutput[i][j] = 2 * random.nextDouble() - 1;
			}
		}
	}

	public void train() {
		for (int i = 0; i < this.numberOfIterations; i++) {
			for (int j = 0; j < this.numberOfRecords; j++) {
				this.forwardCalculation(this.records.get(j).input);

				this.backwardCalculation(this.records.get(j).output);
			}
		}
	}

	public void trainUsingRegression() {
		for (int i = 0; i < this.numberOfRecords; i++) {
			this.forwardCalculation(this.records.get(i).input);
		}
	}

	private void forwardCalculation(double[] trainingInput) {
		for (int i = 0; i < this.numberOfInputs; i++) {
			this.input[i] = trainingInput[i];
		}

		for (int hiddenNode = 0; hiddenNode < this.numberOfHiddenNodes; hiddenNode++) {
			double sum = 0;

			for (int inputNode = 0; inputNode < this.numberOfInputs; inputNode++) {
				sum += this.input[inputNode]
						* this.weightsInputHidden[inputNode][hiddenNode];
			}

			sum += this.thetasAtHiddenNodes[hiddenNode];

			this.outputAtHiddenNodes[hiddenNode] = 1 / (1 + Math.exp(-sum));
		}

		for (int outputNode = 0; outputNode < this.numberOfOutputs; outputNode++) {
			double sum = 0;

			for (int hiddenNode = 0; hiddenNode < this.numberOfHiddenNodes; hiddenNode++) {
				sum += this.outputAtHiddenNodes[hiddenNode]
						* this.weightsHiddenOutput[hiddenNode][outputNode];
			}

			sum += this.thetasAtOutputNodes[outputNode];

			this.outputAtOutputNodes[outputNode] = 1 / (1 + Math.exp(-sum));
		}
	}

	private void backwardCalculation(double[] trainingOutput) {
		for (int outputNode = 0; outputNode < this.numberOfOutputs; outputNode++) {
			this.errorsAtOutputNodes[outputNode] = this.outputAtOutputNodes[outputNode]
					* (1 - this.outputAtOutputNodes[outputNode])
					* (trainingOutput[outputNode] - this.outputAtOutputNodes[outputNode]);
		}

		for (int hiddenNode = 0; hiddenNode < this.numberOfHiddenNodes; hiddenNode++) {
			double sum = 0;

			for (int outputNode = 0; outputNode < this.numberOfOutputs; outputNode++) {
				sum += this.weightsHiddenOutput[hiddenNode][outputNode]
						* this.errorsAtOutputNodes[outputNode];
			}

			this.errorsAtHiddenNodes[hiddenNode] = this.outputAtHiddenNodes[hiddenNode]
					* (1 - this.outputAtHiddenNodes[hiddenNode]) * sum;
		}

		for (int hiddenNode = 0; hiddenNode < this.numberOfHiddenNodes; hiddenNode++) {
			for (int outputNode = 0; outputNode < this.numberOfOutputs; outputNode++) {
				this.weightsHiddenOutput[hiddenNode][outputNode] += this.learningRate
						* this.outputAtHiddenNodes[hiddenNode]
								* this.errorsAtOutputNodes[outputNode];
			}
		}

		for (int inputNode = 0; inputNode < this.numberOfInputs; inputNode++) {
			for (int hiddenNode = 0; hiddenNode < this.numberOfHiddenNodes; hiddenNode++) {
				this.weightsInputHidden[inputNode][hiddenNode] += this.learningRate
						* this.input[inputNode]
								* this.errorsAtHiddenNodes[hiddenNode];
			}
		}

		for (int outputNode = 0; outputNode < this.numberOfOutputs; outputNode++) {
			this.thetasAtOutputNodes[outputNode] += this.learningRate
					* this.errorsAtOutputNodes[outputNode];
		}

		for (int hiddenNode = 0; hiddenNode < this.numberOfHiddenNodes; hiddenNode++) {
			this.thetasAtHiddenNodes[hiddenNode] += this.learningRate
					* this.errorsAtHiddenNodes[hiddenNode];
		}
	}

	private double[] test(double[] input) {
		this.forwardCalculation(input);

		return this.outputAtOutputNodes;
	}

	public void testData(String inputFile, String outputFile)
			throws IOException {
		final Scanner inFile = new Scanner(new File(inputFile));
		final PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		final int numberRecords = inFile.nextInt();

		for (int i = 0; i < numberRecords; i++) {
			final double[] input = new double[this.numberOfInputs];

			for (int j = 0; j < this.numberOfInputs; j++) {
				input[j] = inFile.nextDouble();
			}

			final double[] output = this.test(input);

			for (int j = 0; j < this.numberOfOutputs; j++) {
				outFile.print(output[j] + " ");
			}
			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	public void validate(String validationFile) throws IOException {
		final Scanner inFile = new Scanner(new File(validationFile));

		final int numberRecords = inFile.nextInt();

		double error = 0;

		for (int i = 0; i < numberRecords; i++) {
			final double[] input = new double[this.numberOfInputs];
			for (int j = 0; j < this.numberOfInputs; j++) {
				input[j] = inFile.nextDouble();
			}

			final double[] actualOutput = new double[this.numberOfOutputs];
			for (int j = 0; j < this.numberOfOutputs; j++) {
				actualOutput[j] = inFile.nextDouble();
			}

			final double[] predictedOutput = this.test(input);

			error += this.computeError(actualOutput, predictedOutput);
		}

		inFile.close();

		System.out.printf("Validation error = %.2f/%d = %.2f%%%n", error,
				numberRecords, (error / numberRecords * 100));
		System.out.println();
	}

	private double computeError(double[] actualOutput, double[] predictedOutput) {
		double error = 0;

		for (int i = 0; i < actualOutput.length; i++) {
			error += Math.pow(actualOutput[i] - predictedOutput[i], 2);
		}

		return Math.sqrt(error / actualOutput.length);
	}

	public void writeTrainingErrorComparisonFile(String trainingErrorOutput)
			throws IOException {

		final PrintWriter outFile = new PrintWriter(new FileWriter(
				trainingErrorOutput));

		outFile.println(this.numberOfRecords + " " + this.numberOfOutputs);
		outFile.println();

		for (int i = 0; i < this.numberOfRecords; i++) {
			double[] input = new double[this.numberOfInputs];
			input = this.records.get(i).input;

			final double[] output = this.test(input);

			for (int j = 0; j < this.numberOfOutputs; j++) {
				outFile.print(output[j] + " ");
				outFile.print(this.records.get(i).output[j] + " ");
			}
			outFile.println();
		}

		outFile.close();

	}

	public double calculateTrainingError(
			String convertedTrainingErrorComparisonFile) throws IOException {

		final Scanner inFile = new Scanner(new File(
				convertedTrainingErrorComparisonFile));

		boolean correctlyClassified;
		int numberIncorrectlyClassifiedRecords = 0;

		for (int i = 0; i < this.numberOfRecords; i++) {
			correctlyClassified = true;
			for (int j = 0; j < this.numberOfOutputs; j++) {
				final String actualOutput = inFile.next();
				final String predictedOutput = inFile.next();

				if (!predictedOutput.equals(actualOutput)) {
					correctlyClassified = false;
				}
			}
			if (!correctlyClassified) {
				numberIncorrectlyClassifiedRecords++;
			}
		}

		inFile.close();

		final double error = (double) numberIncorrectlyClassifiedRecords
				/ this.numberOfRecords;

		System.out.println("Training error = "
				+ numberIncorrectlyClassifiedRecords + "/"
				+ this.numberOfRecords + " = " + error * 100 + "%");
		System.out.println();

		return error;
	}

	public void displayWeights() {

		System.out.println("WEIGHTS");
		System.out.println();

		System.out.println("Input nodes - Hidden nodes");
		System.out.print("   ");
		for (int i = 0; i < this.weightsInputHidden[0].length; i++) {
			System.out.printf("%7s", "H" + i);
		}
		System.out.println();

		for (int inputNode = 0; inputNode < this.weightsInputHidden.length; inputNode++) {
			System.out.printf("%3s", "I" + inputNode);
			for (int hiddenNode = 0; hiddenNode < this.weightsInputHidden[inputNode].length; hiddenNode++) {
				System.out.printf("%7.2f",
						this.weightsInputHidden[inputNode][hiddenNode]);
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Hidden nodes - Output nodes");
		System.out.print("   ");
		for (int i = 0; i < this.weightsHiddenOutput[0].length; i++) {
			System.out.printf("%7s", "O" + i);
		}
		System.out.println();

		for (int hiddenNode = 0; hiddenNode < this.weightsHiddenOutput.length; hiddenNode++) {
			System.out.printf("%3s", "H" + hiddenNode);
			for (int outputNode = 0; outputNode < this.weightsHiddenOutput[hiddenNode].length; outputNode++) {
				System.out.printf("%7.2f",
						this.weightsHiddenOutput[hiddenNode][outputNode]);
			}
			System.out.println();
		}
		System.out.println();

	}

	public void displayThetas() {
		System.out.println("Thetas at hidden nodes:");
		for (int hiddenNode = 0; hiddenNode < this.thetasAtHiddenNodes.length; hiddenNode++) {
			System.out.printf("%7.2f", this.thetasAtHiddenNodes[hiddenNode]);
		}
		System.out.println();
		System.out.println();

		System.out.println("Thetas at output nodes:");
		for (int outputNode = 0; outputNode < this.thetasAtOutputNodes.length; outputNode++) {
			System.out.printf("%7.2f", this.thetasAtOutputNodes[outputNode]);
		}
		System.out.println();
		System.out.println();
	}
}
