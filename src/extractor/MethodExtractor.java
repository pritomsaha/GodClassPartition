package extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.body.VariableDeclarator;


import model.Method;

public class MethodExtractor {

	public List<Method> methods;
	public List<String> statementList;
	private String parsedText = "";
	private List<String> instanceVariables;

	public MethodExtractor() {

		this.methods = new ArrayList<>();

	}

	public List<Method> extractMethodsFromSourceFile(File file) {

		try {
			new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(MethodDeclaration n, Object arg) {
					String methodName = n.getNameAsString();
					statementList = new ArrayList<>();
					instanceVariables = new ArrayList<>();
					if(n.getChildNodes().size()>0) {

						for (Node node: n.getChildNodes()){

							processNode(node);
						}

					}

					String methodCode = n.toString();
					methods.add(new Method(methodName, methodCode, statementList, instanceVariables));

					super.visit(n, arg);
				}
			}.visit(JavaParser.parse(file), null);

		} catch (IOException e) {
			System.out.println("Parsing error occur-->>" + file);
			// new RuntimeException(e);
		}

		return methods;
	}

	public List<Method> getMethods() {
		return methods;
	}

	void processNode(Node node)
	{
		if (node instanceof FieldAccessExpr)
		{
			instanceVariables.add(((FieldAccessExpr) node).getNameAsString());
		}
		else if (node instanceof MethodDeclaration)
		{
			String[] strs = ((MethodDeclaration) node).getNameAsString().split("\\s+");
			this.statementList.addAll(Arrays.asList(strs));
		}
		else if (node instanceof VariableDeclarator)
		{
			String[] strs = node.toString().split("\\s+");
			this.statementList.addAll(Arrays.asList(strs));
		}
		else if (node instanceof AssignExpr)
		{
			String[] strs = node.toString().split("\\s+");
			this.statementList.addAll(Arrays.asList(strs));
		}
		else if (node instanceof Parameter)
		{
			String[] strs = node.toString().split("\\s+");
			this.statementList.addAll(Arrays.asList(strs));
			return;
		}

		if(node.getChildNodes().size() > 0)
		{
			for (Node child : node.getChildNodes())
			{
				processNode(child);
			}
		}
		else
		{
			return;
		}
	}

}
