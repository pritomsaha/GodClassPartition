package IR;

import model.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MethodProcessing {

    public List<List<String>> processWithStatements(List<Method> methods){
        List<List<String>> processedMethods = new ArrayList<>();
        for(Method method: methods){
            List<String> processedMethod = camelCaseSplit(method.getStatementList());
            processedMethod = makeLowerCase(processedMethod);
            processedMethod = removeSpecialCharacter(processedMethod);
            processedMethod = stem(processedMethod);
            processedMethod = removeStopWord(processedMethod);
            processedMethods.add(processedMethod);
        }

        return processedMethods;
    }

    public List<List<String>> processWithComments(List<Method> methods){
        List<List<String>> processedMethods = new ArrayList<>();
        for(Method method: methods){
            List<String> processedMethod = camelCaseSplit(method.getComments());
            processedMethod = makeLowerCase(processedMethod);
            processedMethod = removeSpecialCharacter(processedMethod);
            processedMethod = stem(processedMethod);
            processedMethods.add(processedMethod);
        }

        return processedMethods;
    }

    private List<String> camelCaseSplit(List<String> method){

        ArrayList<String> changedMethod = new ArrayList<>();
        for(String statement : method) {
            String temp = "";
            String[] splited = statement.trim().split("\\s+");
            for(String word : splited) {
                temp += splitWord(word.trim());
            }
            changedMethod.add(temp);
        }

        return changedMethod;
    }

    private String splitWord(String word) {
        String words = "";
        for (String w : word.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            words += (" " + w);
        }
        return words;
    }

    private List<String> makeLowerCase(List<String> method){
        List<String> changedMethod = method.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return changedMethod;
    }

    private List<String> removeSpecialCharacter(List<String> method){
        List<String> changedMethod = new ArrayList<>();
        for(String word : method) {
            changedMethod.add(word.replaceAll("[^A-Za-z]+", " ").trim());
        }
        return changedMethod;
    }


    private List<String> stem(List<String> method){
        List<String> changedMethod = new ArrayList<>();

        for(String str: method){
            String[] words = str.trim().split("\\s+");
            String temp = "";
            for(String word: words){
                Porterstemmer stemmer = new Porterstemmer();
                stemmer.add(word.toCharArray(), word.length());
                stemmer.stem();
                temp += (" " + stemmer.toString());
            }
            changedMethod.add(temp.trim());
        }
        changedMethod.removeAll(Collections.singleton(""));
        return changedMethod;
    }

    private List<String> removeStopWord(List<String> method){
        List<String> changedMethod = StopWordRemover.removeStopWord(method);
        return changedMethod;
    }

}
