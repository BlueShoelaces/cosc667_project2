package narr.bayes;

import java.util.ArrayList;

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

}
