package ConceptualCohesionOfClasses;

import extractor.MethodExtractor;
import model.Method;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args){
        String classPath = "UserManagement.java";
        File file = new File(classPath);
        MethodExtractor methodEX = new MethodExtractor();
        List<Method> methods = methodEX.extractMethodsFromSourceFile(file);
        CohesionCalculation cohesionCalculation = new CohesionCalculation(methods);
        System.out.println(cohesionCalculation.getC3());
    }
}

