package model;

import java.util.List;
import java.util.Set;

public class Method {

	private int methodID;
	private String methodName;
	private String methodCode;
	private List<String> instanceVarList;
	private List<String> methodCallList;
	private List<String> statementList;
	private List<String> comments;

	public Method(String methodName,
			String methodCode, List<String> statementList, List<String> instanceVarList) {
		this.methodName = methodName;
		this.methodCode = methodCode;
		this.statementList = statementList;
		this.instanceVarList = instanceVarList;

	}

	public Method(String methodName, List<String> comments){
		this.methodName = methodName;
		this.comments = comments;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setStatementList(List<String> statementList) {
		this.statementList = statementList;
	}

	public List<String> getStatementList() {
		return statementList;
	}

	public int getMethodID() {
		return methodID;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public List<String> getMethodCallList() {
		return methodCallList;
	}

	public void setMethodID(int methodID) {
		this.methodID = methodID;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public void setMethodCallList(List<String> methodCallList) {
		this.methodCallList = methodCallList;
	}


	public void setInstanceVarList(List<String> instanceVarList) {
		this.instanceVarList = instanceVarList;
	}

	public List<String> getInstanceVarList() {
		return instanceVarList;
	}
}
