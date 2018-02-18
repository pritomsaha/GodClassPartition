package method_chains_extraction;

import IR.TfIdf;
import model.Method;

import java.util.*;

public class MatrixConstruction {
    private final double Wssm = 0.5, Wcdm = 0.2, Wcsm = 0.3;
    private double[][] matrix;
    private int numMethod;
    private List<Method> methods;

    public MatrixConstruction(List<Method> methodes){
        this.methods = methodes;
        this.numMethod = methodes.size();
        this.matrix = new double[this.numMethod][this.numMethod];
        this.generateMatrix();
    }

    public double[][] getMatrix() {
        return matrix;
    }

    private void generateMatrix(){
        this.calculateSSM();
        this.calculateCDM();
        this.calculateCSM();
    }

    private void calculateSSM(){
        List<String> union, intersection;
        for(int i=0; i<this.numMethod; i++)
            for(int j=i; j<this.numMethod; j++){
                union = new ArrayList<>();
                union.addAll(this.methods.get(i).getInstanceVarList());
                union.addAll(this.methods.get(j).getInstanceVarList());

                if(i==j){
                    this.matrix[i][j] += 1.0*this.Wssm; continue;
                }

                double ssm_ij = union.size();
                if(ssm_ij == 0) continue;

                intersection = new ArrayList<>();
                intersection.addAll(this.methods.get(i).getInstanceVarList());
                intersection.retainAll(this.methods.get(j).getInstanceVarList());

                ssm_ij = (intersection.size()/ssm_ij)*this.Wssm;
                this.matrix[i][j] +=  ssm_ij;
                this.matrix[j][i] +=  ssm_ij;
            }
    }

    private void calculateCDM(){
        List<String> methodCalls = new ArrayList<>();
        for(Method method: this.methods){
            methodCalls.addAll(method.getMethodCallList());
        }
        for(int i=0; i<this.numMethod; i++)
            for(int j=i; j<this.numMethod; j++){
                if(i == j){
                    this.matrix[i][j] += 1.0*this.Wcdm; continue;
                }

                double cdm_ij = Collections.frequency(methodCalls, this.methods.get(j).getMethodName());
                if(cdm_ij != 0){
                    cdm_ij = Collections.frequency(this.methods.get(i).getMethodCallList(),
                            this.methods.get(j).getMethodName()) / cdm_ij;
                }

                double cdm_ji = Collections.frequency(methodCalls, this.methods.get(i).getMethodName());
                if(cdm_ji != 0){
                    cdm_ji = Collections.frequency(this.methods.get(j).getMethodCallList(),
                            this.methods.get(i).getMethodName()) / cdm_ji;
                }

                cdm_ij = Math.max(cdm_ij, cdm_ji)*this.Wcdm;
                this.matrix[i][j] += cdm_ij;
                this.matrix[j][i] += cdm_ij;
            }
    }

    private void calculateCSM(){
        TfIdf tfIdf = new TfIdf(this.methods);
        double[][] tfIdfVectors = tfIdf.getTfIdfVectors();
        double[] euclideanNorm = tfIdf.getEuclideanNorm();

        for(int i=0; i<this.numMethod; i++)
            for(int j=i; j<this.numMethod; j++){
                if(i == j) {
                    this.matrix[i][j] += 1.0*this.Wcsm; continue;
                }

                if(euclideanNorm[i]!=0 && euclideanNorm[i]!=0){
                    double csm_ij = this.vecProduct(tfIdfVectors[i], tfIdfVectors[j])/(euclideanNorm[i]*euclideanNorm[j]);
                    csm_ij *= this.Wcsm;
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
