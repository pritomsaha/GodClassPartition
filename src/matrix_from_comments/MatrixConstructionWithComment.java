package matrix_from_comments;

import IR.TfIdf;
import model.Method;

import java.util.List;

public class MatrixConstructionWithComment {
    private List<Method> methods;
    private int numMethod;
    private double[][] matrix;

    public MatrixConstructionWithComment(List<Method> methods){
        this.methods = methods;
        this.numMethod = methods.size();
        this.matrix = new double[this.numMethod][this.numMethod];
        calculateCSM();
    }

    public double[][] getMatrix() {
        return matrix;
    }

    private void calculateCSM(){
        TfIdf tfIdf = new TfIdf();
        tfIdf.calcWithComments(this.methods);
        double[][] tfIdfVectors = tfIdf.getTfIdfVectors();
        double[] euclideanNorm = tfIdf.getEuclideanNorm();

        for(int i=0; i<this.numMethod; i++)
            for(int j=i; j<this.numMethod; j++){
                if(i == j) {
                    this.matrix[i][j] += 1.0; continue;
                }

                if(euclideanNorm[i]!=0 && euclideanNorm[i]!=0){
                    double csm_ij = this.vecProduct(tfIdfVectors[i], tfIdfVectors[j])/(euclideanNorm[i]*euclideanNorm[j]);
                    this.matrix[i][j] += csm_ij;
                    this.matrix[j][i] += csm_ij;
                }
            }
    }

    private double vecProduct(double[] vector1, double[] vector2){
        double prod = 0;
        for(int i=0; i<vector1.length; i++){
            prod += vector1[i]*vector2[i];
        }
        return prod;
    }

    public void printMat(){
        for(int i=0; i<this.numMethod; i++) {
            for (int j = 0; j < this.numMethod; j++) {
                System.out.printf("%.3f ",this.matrix[i][j]);
            }
            System.out.println();
        }
    }
}
