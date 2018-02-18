import java.io.File;

import java.util.List;

import extractor.MethodCallExtractor;
import extractor.MethodExtractor;
import method_chains_extraction.ChainExtraction;
import model.Method;

public class Main {

	public static void main(String[] args) {
		String projectPath = "/home/pritom/Downloads/xerces2-j-Xerces-J_2_7_0";
		//String projectPath = "UserManagement.java";
		FileHandler fileHandler = new FileHandler();
		List<File>  files = fileHandler.getJavaFiles(projectPath);
		for (File file: files){
			System.out.println(file.getAbsolutePath());
			proceed(file);
			System.out.println();
		}

	}

	public static void proceed(File file){
		//Extract all methods
		MethodExtractor methodEX = new MethodExtractor();
		List<Method> methods = methodEX.extractMethodsFromSourceFile(file);

		for (int i = 0; i < methods.size(); i++) {
			//set ID for each methods
			Method m = methods.get(i);
			m.setMethodID(i);

			//Extract and set method calls
			MethodCallExtractor methodCallEX = new MethodCallExtractor();
			methodCallEX.extractMethodCallsInsideAMethod(m.getMethodCode());
			List<String> methodCallList = methodCallEX.getMethodCallsList();
			m.setMethodCallList(methodCallList);


		}

		ChainExtraction chainExtraction = new ChainExtraction(methods);
		List<List<Integer>> candidateChains = chainExtraction.getCandidateChains();

		for(List<Integer> cChain: candidateChains){
			for (Integer mIndex: cChain){
				System.out.print(methods.get(mIndex).getMethodName()+", ");
			}
			System.out.println("\n");
		}
	}

}
