package com.ikhokha.techcheck;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		
		/* Map<String, Integer> totalResults = new HashMap<>();
				
		File docPath = new File("docs");
		File[] commentFiles = docPath.listFiles((d, n) -> n.endsWith(".txt"));
		
		for (File commentFile : commentFiles) {
			
			CommentAnalyzer commentAnalyzer = new CommentAnalyzer(commentFile);
			Map<String, Integer> fileResults = commentAnalyzer.analyze();
			addReportResults(fileResults, totalResults);
						
		}
		
		System.out.println("RESULTS\n=======");
		totalResults.forEach((k,v) -> System.out.println(k + " : " + v));*/
		/* Analyzer analyzer = new Analyzer();
		CommentReport report = analyzer.analyze();
		System.out.println("SHORTER_THAN_15: " + report.Shorter);
		System.out.println("SHAKER_MENTIONS: " + report.Shakers);
		System.out.println("MOVERS_MENTIONS: " + report.Movers);
		System.out.println("QUESTIONS: " + report.Questions);
		System.out.println("SPAM: " + report.Spam);*/
		
		/* ExecutorService executor = Executors.newFixedThreadPool(10);
		List<CommentReport> reports = Collections.emptyList();
		File[] files = new File("docs").listFiles((file, s) -> s.endsWith(".txt"));
		for (File file : files) {
			var work = new Analyzer(file);
			executor.execute(work);
			System.out.println(work.getCurrentReport());
		}
		executor.shutdown();*/

		var analyzer = new Analyzer();
		analyzer.analyze();
		var report = analyzer.getCurrentReport();

		System.out.println("===================================");

		System.out.printf("SPAM: %s%n", report.Spam);
		System.out.printf("QUESTIONS: %s%n", report.Questions);
		System.out.printf("MOVERS_MENTIONS: %s%n", report.Movers);
		System.out.printf("SHAKERS_MENTIONS: %s%n", report.Shakers);
		System.out.printf("SHORTER_THAN_15: %s%n", report.Shorter);

		System.out.println("===================================");


	}
	
	/**
	 * This method adds the result counts from a source map to the target map 
	 * @param source the source map
	 * @param target the target map
	 */
	private static void addReportResults(Map<String, Integer> source, Map<String, Integer> target) {

		for (Map.Entry<String, Integer> entry : source.entrySet()) {
			target.put(entry.getKey(), entry.getValue());
		}
		
	}

}
