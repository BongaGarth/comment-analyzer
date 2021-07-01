package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Analyzer {

    public CommentReport analyze() {
        var report = new CommentReport();
        try {
            var files = getCommentFiles();
            while (files.size() != 0) {
                // get file from the stack
                File cFile = files.pop();
                BufferedReader reader = new BufferedReader(new FileReader(cFile));
                List<String> lines = reader.lines().collect(Collectors.toList());

                // filter the information you want
                long movers = lines.stream().filter(s -> s.toLowerCase().contains("mover")).count();
                long shakers = lines.stream().filter(s -> s.toLowerCase().contains("shaker")).count();
                long shortComments = lines.stream().filter(s -> s.length() < 15).count();
                long questions = lines.stream().filter(s -> s.contains("?")).count();

                // add to a report
                report.Shakers += shakers;
                report.Movers += movers;
                report.Shorter += shortComments;
                report.Questions += questions;

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