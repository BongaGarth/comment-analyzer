package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Analyzer implements Runnable {
    private final CommentReport report = new CommentReport();
    private File currentFile;

    public void analyze() {
        // fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(10);
        // get files
        Stack<File> files = getCommentFiles();
        while (files.size() != 0) {
            currentFile = files.pop();
            System.out.println(currentFile.getName());
            executor.execute(this);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    private Stack<File> getCommentFiles() {
        Stack<File> fileStack = new Stack<>();
        File[] files = new File("docs").listFiles((file, name) -> name.endsWith(".txt"));
        if (files != null && files.length > 0) {
            for (File file : files) {
                fileStack.push(file);
            }
        }
        return fileStack;
    }

    public CommentReport getCurrentReport() {
        return this.report;
    }

    @Override
    public void run() {
        try {
            if (currentFile != null) {
                // get file from the stack
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                List<String> lines = reader.lines().collect(Collectors.toList());

                // filter the information you want
                long movers = lines.stream().filter(s -> s.toLowerCase().contains("mover")).count();
                long shakers = lines.stream().filter(s -> s.toLowerCase().contains("shaker")).count();
                long shortComments = lines.stream().filter(s -> s.length() < 15).count();
                long questions = lines.stream().filter(s -> s.contains("?")).count();

                // regex to match all urls per line
                Pattern rgx = Pattern.compile("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)", Pattern.CASE_INSENSITIVE);
                long spam = lines.stream().filter(s -> rgx.matcher(s).find()).count();

                // add to a report
                report.Shakers += shakers;
                report.Movers += movers;
                report.Shorter += shortComments;
                report.Questions += questions;
                report.Spam += spam;

                System.out.println("file name: " + currentFile.getName());

            }
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }
}