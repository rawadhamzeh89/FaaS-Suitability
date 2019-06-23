package parsing;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.yourorganization.maven_sample.LogicPositivizer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class functionParser {
static LinkedList<operation> usecasetype;
static LinkedList<useCase> usecases=new LinkedList<useCase>();
static int numberOfMethodCall=0;
public void retrieveUseCAseType() {
	DBManager dbm=new DBManager();
	functionParser.usecasetype=	dbm.returnAllUseCaseType();
	
}
public void parse() {
    SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class).resolve("src/main/resources"));

    // Our sample is in the root of this directory, so no package name.
    CompilationUnit cu = sourceRoot.parse("", "Blabla.java");

    cu.accept(new ModifierVisitor<Void>() {
    	
        /**
         * For every if-statement, see if it has a comparison using "!=".
         * Change it to "==" and switch the "then" and "else" statements around.
         */
        @Override
        public Visitable visit(ExpressionStmt  n, Void arg) {
            // Figure out what to get and what to cast simply by looking at the AST in a debugger! 
        for(int i=0;i<functionParser.usecasetype.size();i++) {
        	String sp=(String)(functionParser.usecasetype.get(i).text);
        	
        	//System.out.println(".*."+sp+".*");
        	if(n.getExpression().toString().matches(".*."+sp+".*")){
        		 System.out.println(n.getExpression().toString()+"  "+n.getBegin());
        		 if(functionParser.usecasetype.get(i).type==1) {
        			 databaseOpeartion dop=new databaseOpeartion();
        			 dop.lineNumber=""+n.getBegin();
        			 dop.databaseType=functionParser.usecasetype.get(i).name;
        			 functionParser.usecases.addFirst(dop);
        		 }else if(functionParser.usecasetype.get(i).type==2) {
        			 messagingOperation mop=new messagingOperation();
        			 mop.lineNumber=""+n.getBegin();
        			 mop.messageType=functionParser.usecasetype.get(i).name;
        			 functionParser.usecases.addFirst(mop);
        		 }else {
        			 authOperation aop=new authOperation();
        			 aop.lineNumber=""+n.getBegin();
        			 aop.authType=functionParser.usecasetype.get(i).name;
        			 functionParser.usecases.addFirst(aop);
        		 }
        	}
        }        
        	 if(n.getExpression().toString().matches(".*.select\\s.*.from\\s.*")){
        		
        	 System.out.println(n.getExpression().toString()+"  "+n.getBegin());
        	 System.out.println(" ** ");
        	 }
           
            return super.visit(n, arg);
        }
        
        @Override
        public Visitable visit(MethodCallExpr  n, Void arg) {
        	//System.out.println("method call "+n.getNameAsString());
        	functionParser.numberOfMethodCall++;
        	 return super.visit(n, arg);
        }
    }, null);

    // This saves all the files we just read to an output directory.  
    sourceRoot.saveAll(
            // The path of the Maven module/project which contains the LogicPositivizer class.
            CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class) 
            // appended with a path to "output"
            .resolve(Paths.get("output")));
                    // appended with a path to "output"
 

}
	public static void main(String[] args) {
		functionParser fp=new functionParser();
		fp.retrieveUseCAseType();
		for(int j=0;j<functionParser.usecasetype.size();j++ ) {
			System.out.println(functionParser.usecasetype.get(j).text);
		}
		fp.parse();
		System.out.println(functionParser.numberOfMethodCall);
		DBManager dd=new DBManager("CodeMR");
		dd.returnCodeMR();
		DBManager ddd=new DBManager("metadata");
		ddd.returnMeasurement();
	/*	DBManager dbm =new DBManager();
	LinkedList<operation> res=	dbm.returnDBUseCase();
	LinkedList<operation> res2= dbm.returnAuthMessagingUseCase();
	for(int j=0;j< res2.size();j++) {
		System.out.println(""+res2.get(j).name+","+res2.get(j).text);
	}
		// TODO Auto-generated method stub
		// SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        // with src/main/resources appended.
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class).resolve("src/main/resources"));

        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Blabla.java");

        cu.accept(new ModifierVisitor<Void>() {
           
            @Override
            public Visitable visit(IfStmt n, Void arg) {
                // Figure out what to get and what to cast simply by looking at the AST in a debugger! 
                n.getCondition().ifBinaryExpr(binaryExpr -> {
                    if (binaryExpr.getOperator() == BinaryExpr.Operator.EQUALS && n.getElseStmt().isPresent()) {
                       
                        Statement thenStmt = n.getThenStmt().clone();
                        Statement elseStmt = n.getElseStmt().get().clone();
                       // System.out.println(n.getElseStmt());
                        n.setThenStmt(elseStmt);
                        n.setElseStmt(thenStmt);
                        binaryExpr.setOperator(BinaryExpr.Operator.EQUALS);
                    }
                });
                return super.visit(n, arg);
            }
        }, null);
        
        cu.accept(new ModifierVisitor<Void>() {
           
            @Override
            public Visitable visit(ExpressionStmt  n, Void arg) {
                // Figure out what to get and what to cast simply by looking at the AST in a debugger! 
            	 
            	 if(n.getExpression().toString().matches(".*.select\\s.*.from\\s.*")){
            		 
            	
            	 System.out.println(n.getExpression().toString()+"  "+n.getBegin());
            	 System.out.println(" ** ");
            	 }
               
                return super.visit(n, arg);
            }
        }, null);

        // This saves all the files we just read to an output directory.  
        sourceRoot.saveAll(
                // The path of the Maven module/project which contains the LogicPositivizer class.
                CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class) 
                // appended with a path to "output"
                .resolve(Paths.get("output")));
                        // appended with a path to "output"
     */
	}
	
	public LinkedList<useCase> FindUseCase() {
		LinkedList<useCase> result=new LinkedList<useCase>();
		return result;
	}

}
