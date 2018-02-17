package extractor;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import model.Variable;

public class VariablesExtractor {

	public Set<Variable> varaibleSet;

	public VariablesExtractor() {
		varaibleSet = new HashSet<Variable>();
	}

	public void extractVariablesInsideMethods(String methodCode) {
		String code = "public class A{" + methodCode + "}";
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(VariableDeclarator n, Object arg) {
				super.visit(n, arg);
				Variable v = new Variable(n.getName().toString(), n.getType().toString());
				varaibleSet.add(v);
			}
		}.visit(JavaParser.parse(code), null);

	}


	public Set<Variable> getVaraibleList() {
		return varaibleSet;
	}
}
