package ConceptualCohesionOfClasses;

import IR.TfIdf;
import model.Method;

import java.util.List;

public class CohesionCalculation {

    private List<Method> methods;
    private int numMethod;
    private double C3;

    public CohesionCalculation(List<Method> methods){
        this.methods = methods;
        this.numMethod = methods.size();
        this.calculateC3();
    }

    public double getC3() {
        return this.C3;
    }

    public void calculateC3(){
        TfIdf tfIdf = new TfIdf();
        tfIdf.calcWithStatements(this.methods);
        double[][] tfIdfVectors = tfIdf.getTfIdfVectors();
        double[] euclideanNorm = tfIdf.getEuclideanNorm();

        double numEdges = (this.numMethod*(this.numMethod-1))/2;
        double acsm = 0;

        for(int i=0; i<this.numMethod; i++) {
            for (int j = i + 1; j < this.numMethod; j++) {
                if (euclideanNorm[i] != 0 && euclideanNorm[i] != 0) {
                    double csm_ij = this.vecProduct(tfIdfVectors[i], tfIdfVectors[j]) / (euclideanNorm[i] * euclideanNorm[j]);
                    acsm += csm_ij;
                }
            }
        }
        acsm /= numEdges;
        this.C3 = (acsm>0)? acsm: 0;
    }

    private double vecProduct(double[] vector1, double[] vector2){
        double prod = 0;
        for(int i=0; i<vector1.length; i++){
            prod += vector1[i]*vector2[i];
        }
        return prod;
    }

}
