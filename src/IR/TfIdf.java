package IR;

import model.Method;

import java.util.*;

public class TfIdf {
    private List<List<String>> methods;
    private List<String> uniqueStatements;
    private double[][] tfIdfVectors;
    private double[] euclideanNorm;

    public TfIdf(List<Method> methods){
        this.methods = new MethodProcessing().process(methods);
        this.calculateVectors();
    }

    public double[][] getTfIdfVectors() {
        return tfIdfVectors;
    }

    public double[] getEuclideanNorm() {
        return euclideanNorm;
    }

    public void calculateVectors(){
        HashSet<String> set = new HashSet<>();
        for(List<String> statementList: this.methods){
            set.addAll(statementList);
        }
        this.uniqueStatements = new ArrayList<>(set);
        int numUniqueStatements = this.uniqueStatements.size();

        this.tfIdfVectors = new double[this.methods.size()][numUniqueStatements];
        this.euclideanNorm = new double[this.methods.size()];
        int mNum = 0;

        for(List<String> statementList: this.methods){
            double tf, idf;
            double[] tfIdfVec = new double[numUniqueStatements];
            int stNum = 0;
            for(String statement: this.uniqueStatements){
                tf = this.getTf(statement, statementList);
                idf = this.getIdf(statement);
                tfIdfVec[stNum++] = tf*idf;
            }
            this.tfIdfVectors[mNum] = tfIdfVec;
            this.euclideanNorm[mNum] = this.calculateEuclideanNorm(tfIdfVec);
            mNum++;
        }

    }

    private double calculateEuclideanNorm(double[] vec){
        double norm = 0.0;
        for(double v: vec){
            norm += v*v;
        }
        return Math.sqrt(norm);
    }

    private int getTf(String statement, List<String> statementList){
        return Collections.frequency(statementList, statement);
    }

    private double getIdf(String statement){
        double count = 0;
        for(List<String> statementList: this.methods){
            if(statementList.contains(statement)) count++;
        }
        return Math.log(this.methods.size()/count);
    }
}
