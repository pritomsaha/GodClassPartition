package extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import model.Variable;

public class ClassVariableExtractor {

	public Set<Variable> classVariableSet;

	public ClassVariableExtractor() {
		classVariableSet = new HashSet<Variable>();
	}

	public void extractClassVariables(File file) throws FileNotFoundException {

		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(FieldDeclaration n, Object arg) {
				super.visit(n, arg);
				 //System.out.println(n.toString());
				//Object[] modifiers = n.getModifiers().toArray();

				// System.out.println(n.getCommonType());
				String type = n.getCommonType().toString();
				List<VariableDeclarator> nodes = n.getVariables();

				for (VariableDeclarator x : nodes) {
					boolean isStatic = n.getModifiers().contains(Modifier.STATIC);
					Variable v = new Variable(x.getName().toString(), type);
					v.setStatic(isStatic);
					classVariableSet.add(v);
				}
			}
		}.visit(JavaParser.parse(file), null);

	}

	public Set<Variable> getClassVaraibleSet() {
		return classVariableSet;
	}

}
