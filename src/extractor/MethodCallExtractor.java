package extractor;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class MethodCallExtractor {

	List<String> calledMethodList;

	public MethodCallExtractor() {
		calledMethodList = new ArrayList<String>();
	}

	public List<String> extractMethodCallsInsideAMethod(String methodCode) {
		String code = "public class A{" + methodCode + "}";
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodCallExpr n, Object arg) {
				super.visit(n, arg);
				calledMethodList.add(n.getNameAsString());
			}
		}.visit(JavaParser.parse(code), null);

		return calledMethodList;
	}

	public List<String> getMethodCallsList() {
		return calledMethodList;
	}

}