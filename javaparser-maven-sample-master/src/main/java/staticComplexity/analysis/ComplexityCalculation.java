package staticComplexity.analysis;

import java.util.LinkedList;

import parsing.DBManager;
import parsing.metric;

public class ComplexityCalculation {
	DBManager dbm=new DBManager("CodeMR");
	public void codeComplexityAnalysis() {
	   LinkedList<metric> codeMRs=dbm.returnCodeMR();
	}
}

