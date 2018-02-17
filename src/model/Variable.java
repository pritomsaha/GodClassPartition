package model;

import java.util.Arrays;
import java.util.List;

public class Variable {

	private String name;
	private String type;
	private boolean isStatic;

	public Variable(String name, String type) {

		this.name = name;
		this.type = type;
		this.isStatic = false;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatic(boolean aStatic) {
		isStatic = aStatic;
	}

	public boolean isStatic(){
		return this.isStatic;
	}

	@Override
	public String toString() {

		return "Name:->" + name + '\n' + "Type:->" + type;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + name.hashCode();
		result = 31 * result + type.hashCode();

		return result;
	}

	@Override
	public boolean equals(Object v) {
		if (v == this)
			return true;
		if (!(v instanceof Variable)) {
			return false;
		}
		Variable variable = (Variable) v;
		return variable.name.equals(name) && variable.type == type;
	}

}
