package ru.otus.L041;

import java.util.HashMap;
import java.util.Map;

// Helper class for storing information about the garbage collections
class GCStatistics {
	private static Map<String, Integer> statisticsCollection;
	private static Long collectionsTimePerMinute;

	GCStatistics() {
		statisticsCollection = new HashMap<>();
		collectionsTimePerMinute = 0L;
	}

	void commitGarbageCollection(String gcName, Long collectionTime) {
		statisticsCollection.put(gcName,
				(!statisticsCollection.containsKey(gcName)
						? 1
						: statisticsCollection.get(gcName) + 1));
		collectionsTimePerMinute += collectionTime;
	}

	String getCollectionsTypeAndAmount() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry entry : statisticsCollection.entrySet()) {
			stringBuilder.append("GC name: ")
					.append(entry.getKey())
					.append(", number of collections per minute: ")
					.append(entry.getValue()).append("\n");
		}
		return stringBuilder.toString();
	}

	String getCollectionsTimePerMinute() {
		return "Total collections time per minute: " + collectionsTimePerMinute + "\n";
	}

	void resetStatistics() {
		for (String gcName : statisticsCollection.keySet()) statisticsCollection.put(gcName, 0);
		collectionsTimePerMinute = 0L;
	}
}