package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analyzer {

    public Report analyze() {
        var report = new Report();
        try {
            var files = getCommentFiles();
            while (files.size() != 0) {
                var cFile = files.pop();
                BufferedReader reader = new BufferedReader(new FileReader(cFile));
                List<String> lines = reader.lines().collect(Collectors.toList());
                long movers = lines.stream().filter(line -> line.toLowerCase().contains("mover")).count();
                long shakers = lines.stream().filter(line -> line.toLowerCase().contains("shaker")).count();
                report.Shakers += shakers;
                report.Movers += movers;

                System.out.println("file name: " + cFile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return report;
    }
    private Stack<File> getCommentFiles() {
        Stack<File> fileStack = new Stack<>();
        File[] files = new File("docs")
                .listFiles((file, name) -> name.endsWith(".txt"));
        if (files != null && files.length > 0) {
            for (File file : files) {
                fileStack.push(file);
            }
        }
        return fileStack;
    }
}

class Report {
    public int Movers;
    public int Shakers;
}