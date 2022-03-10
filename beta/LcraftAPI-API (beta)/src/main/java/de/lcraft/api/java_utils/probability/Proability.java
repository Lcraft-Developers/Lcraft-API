package de.lcraft.api.java_utils.probability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Proability<T extends Object> {

	private class Entry {
		double accumulatedWeight;
		T object;
	}

	private final List<Entry> entries = new ArrayList<>();
	private double accumulatedWeight;
	private final Random rand = new Random();

	public void addEntry(T object, double weight) {
		accumulatedWeight += weight;
		Entry e = new Entry();
		e.object = object;
		e.accumulatedWeight = accumulatedWeight;
		entries.add(e);
	}

	public T getRandom() {
		double r = rand.nextDouble() * accumulatedWeight;

		for (Entry entry: entries) {
			if (entry.accumulatedWeight >= r) {
				return entry.object;
			}
		}
		return null;
	}

}