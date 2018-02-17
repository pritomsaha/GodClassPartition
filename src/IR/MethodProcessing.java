package IR;

import model.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MethodProcessing {

    public List<List<String>> process(List<Method> methods){
        List<List<String>> processedMethods = new ArrayList<>();
        for(Method method: methods){
            List<String> statementList = camelCaseSplit(method.getStatementList());
            statementList = makeLowerCase(statementList);
            statementList = removeSpecialCharacter(statementList);
            statementList = stem(statementList);
            processedMethods.add(statementList);
        }

        return processedMethods;
    }

    private List<String> camelCaseSplit(List<String> statementList){

        ArrayList<String> changedStatements = new ArrayList<>();
        for(String statement : statementList) {
            String tempStatement = "";
            String[] splited = statement.trim().split("\\s+");
            for(String word : splited) {
                tempStatement += splitWord(word.trim());
            }
            changedStatements.add(tempStatement);
        }

        return changedStatements;
    }

    private String splitWord(String word) {
        String words = "";
        for (String w : word.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            words += (" " + w);
        }
        return words;
    }

    private List<String> makeLowerCase(List<String> statementList){
        List<String> changedStatements = statementList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return changedStatements;
    }

    private List<String> removeSpecialCharacter(List<String> statementList){
        List<String> changedStatements = new ArrayList<>();
        for(String statement : statementList) {
            changedStatements.add(statement.replaceAll("[^A-Za-z]+", " ").trim());
        }
        return changedStatements;
    }


    private List<String> stem(List<String> statementList){
        List<String> changedStatements = new ArrayList<>();

        for(String statement: statementList){
            String[] words = statement.trim().split("\\s+");
            String temp = "";
            for(String word: words){
                Porterstemmer stemmer = new Porterstemmer();
                stemmer.add(word.toCharArray(), word.length());
                stemmer.stem();
                temp += (" " + stemmer.toString());
            }
            changedStatements.add(temp);
        }

        return changedStatements;
    }

}
