package method_chains_extraction;

import com.github.javaparser.ast.expr.InstanceOfExpr;
import model.Method;

import java.util.ArrayList;
import java.util.List;

public class ChainExtraction {
    private final double minCoupling = 0.3;
    private final double minLength = 3;

    private double[][] matrix;
    private List<Method> methods;
    private List<List<Integer>> trivialChains;
    private List<List<Integer>> candidateChains;
    private int mLen;

    private static final int[] moveX = {0, 0, -1, 1};
    private static final int[] moveY = {-1, 1, 0, 0};

    public ChainExtraction(List<Method> methods){
        this.methods = methods;
        this.mLen = methods.size();
        this.trivialChains = new ArrayList<>();
        this.candidateChains = new ArrayList<>();
        this.constructCandidateChains();
    }

    private void constructCandidateChains(){
        MatrixConstruction matrixConstruction = new MatrixConstruction(this.methods);
        this.matrix = matrixConstruction.getMatrix();
        this.constructChains();
        this.mergeTrivialChains();
    }

    public List<List<Integer>> getCandidateChains() {
        return candidateChains;
    }

    private void mergeTrivialChains(){
        List<Integer> indexList = new ArrayList<>();
        for(List<Integer> tChain: this.trivialChains){
            double maxCoup = 0.0;
            int index = 0;
            for(int i=0; i<this.candidateChains.size(); i++){
                double coup = this.getCoupling(tChain, this.candidateChains.get(i));
                if(coup > maxCoup){
                    maxCoup = coup;
                    index = i;
                }
            }
            indexList.add(index);
        }

        for (int i=0; i<indexList.size(); i++){
            this.candidateChains.get(indexList.get(i)).addAll(this.trivialChains.get(i));
        }
    }

    private double getCoupling(List<Integer> tChain, List<Integer> cChain){
        double coupling = 0.0;
        for(Integer Ci: tChain)
            for (Integer Cj: cChain)
                coupling += this.matrix[Ci][Cj];
        return  coupling/(tChain.size()*cChain.size());
    }

    private void constructChains(){
        boolean[] visited = new boolean[this.mLen];
        List<Integer> connectedMethods;
        for(int i=0; i<this.mLen; i++){
            if(visited[i] == false){
                connectedMethods = new ArrayList<>();
                dfs(i, visited, connectedMethods);
                if(connectedMethods.size()<this.minLength){
                    this.trivialChains.add(connectedMethods);
                }
                else this.candidateChains.add(connectedMethods);
            }
        }
    }

    private void dfs(int u, boolean[] visited, List<Integer> connectedMethods){
        visited[u] = true;
        connectedMethods.add(u);
        for(int v=0; v<mLen; v++){
            if(this.matrix[u][v] >= this.minCoupling && visited[v] == false){
                dfs(v, visited, connectedMethods);
            }
        }
    }

}
