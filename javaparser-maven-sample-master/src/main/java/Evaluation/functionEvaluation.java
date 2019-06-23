package Evaluation;

import java.util.LinkedList;

import parsing.DBManager;
import parsing.metric;

public class functionEvaluation implements codeEvaluation {

	public void gatherData() {
		DBManager dbm=new DBManager("metadata");
	 LinkedList<metric> metrics=	dbm.returnMeasurement();
	}
	
	@Override
	public void evaluateFunction(LinkedList<metric> metrics) {
		// TODO Auto-generated method stub
		double score=1;
          for(int i=0;i<metrics.size();i++) {
        	  score=score*Math.pow(metrics.get(i).value, metrics.get(i).weight);
          }
          score=this.normalize(score);
	}
	
	public double normalize(double value) {
		/// normalize methode
		return value;
	}

}
