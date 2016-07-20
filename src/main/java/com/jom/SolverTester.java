/*******************************************************************************
 * Copyright (c) 2015 Pablo Pavon Mari�o.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * Pablo Pavon Mari�o - initial API and implementation
 ******************************************************************************/

package com.jom;

import java.io.PrintWriter;
import java.io.StringWriter;

import cern.colt.matrix.tdouble.DoubleMatrix1D;

/** This class contains methods for performing tests in the solvers. They are not performance tests, but to check if
 *  the libraries, licenses etc. are correctly installed, and then JOM can solve problems with the solver.
 * @author Pablo Pavon Mariño
 * @see <a href="http://www.net2plan.com/jom">http://www.net2plan.com/jom</a>
 */
public class SolverTester
{
	final static String RETURN = System.getProperty("line.separator");

//	/** Creates an optimization problem object
//	 *
//	 */
//	public static void main (String [] args)
//	{
//		String res = com.jom.SolverTester.check_xpress("c:\\xpressmp\\xpauth.xpr");
//		System.out.println("XPRESS: " + res);
//		res = com.jom.SolverTester.check_cplex("c:\\windows\\system32\\cplex.dll");
//		System.out.println("CPLEX: " + res);
//		res = com.jom.SolverTester.check_glpk("c:\\windows\\system32\\glpk.dll");
//		System.out.println("GLPK: " + res);
//		res = com.jom.SolverTester.check_ipopt("c:\\windows\\system32\\ipopt.dll");
//		System.out.println("IPOPT: " + res);
//	}

	private static void checkLinearSolver (String solverName , String auxFileName)
	{
  		{
			OptimizationProblem op = new OptimizationProblem();
			op.addDecisionVariable("x", false, new int[] { 1,3 }, 0, 1);  // name, isInteger, size , minValue, maxValue
			op.setObjectiveFunction("maximize", "[1 ; 2 ; 3] * x'");
		  	op.addConstraint("sum(x) <= 1.5");  
	  		op.solve(solverName , "solverLibraryName" , auxFileName);
			DoubleMatrix1D sol = op.getPrimalSolution("x").view1D();
			if (!equalWithMargin (sol.toArray() , new double [] {0 , 0.5 , 1})) throw new RuntimeException ("The solution returned by the solver is not correct");
  		}
  		{
			OptimizationProblem op = new OptimizationProblem();
			op.addDecisionVariable("x", true, new int[] { 1,3 }, 0, 1);  // name, isInteger, size , minValue, maxValue
			op.setObjectiveFunction("maximize", "[1 ; 2 ; 3] * x'");
		  	op.addConstraint("sum(x) <= 1.5");  
	  		op.solve(solverName , "solverLibraryName" , auxFileName);
			DoubleMatrix1D sol = op.getPrimalSolution("x").view1D();
			if (!equalWithMargin (sol.toArray() , new double [] {0 , 0 , 1})) throw new RuntimeException ("The solution returned by the solver is not correct");
  		}
	}
	
	/** Performs the check for the solver XPRESS, returns a "" String if everything ok. If not, the returned String 
	 * contains the error message including a description message the stack trace of the Exception raised.
	 * @param licenseFileName The name of the license file name (e.g. xpauth.xpr) for XPRESS (including its path). 
	 * @return the check String
	 */
	public static String check_xpress (String licenseFileName)
	{
		StringBuffer sb = new StringBuffer();
		{
		  	try
		  	{
		  		checkLinearSolver ("xpress" , licenseFileName);
		  	} catch (java.lang.NoClassDefFoundError e)
		  	{
		  		sb.append("MESSAGE: Class not found. If the class not found is of the XPRESS libraries, this error can correspond "
		  				+ "to the case when the file xprs.jar "
		  				+ "(typically in xpressmp/lib directory) is not accessible to JOM library. Add it to the classpath, "
		  				+ "or in Net2Plan use the option File->Classpath editor to make it accessible to the algorithms." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	} catch (Exception e)
		  	{
		  		sb.append("MESSAGE: Check failed." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	}
		}
		return sb.toString();
		
	}

	/** Performs the check for the solver CPLEX, returns a "" String if everything ok. If not, the returned String 
	 * contains the error message including a description message the stack trace of the Exception raised.
	 * @param solverLibraryName The name of the dynamic library file (DLL or .SO file) with the solver 
	 * @return the check String
	 */
	public static String check_cplex (String solverLibraryName)
	{
		StringBuffer sb = new StringBuffer();
		{
		  	try
		  	{
		  		checkLinearSolver ("cplex" , solverLibraryName);
		  	} catch (java.lang.NoClassDefFoundError e)
		  	{
		  		sb.append("MESSAGE: Class not found. This error can correspond "
		  				+ "to the case when a JAR file "
		  				+ " is not accessible to JOM library. Add it to the classpath, "
		  				+ "or in Net2Plan use the option File->Classpath editor to make it accessible to the algorithms." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	} catch (Exception e)
		  	{
		  		sb.append("MESSAGE: Check failed." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	}
		}
		return sb.toString();
	}

	/** Performs the check for the solver GLPK, returns a "" String if everything ok. If not, the returned String 
	 * contains the error message including a description message the stack trace of the Exception raised.
	 * @param solverLibraryName The name of the dynamic library file (DLL or .SO file) with the solver 
	 * @return the check String
	 */
	public static String check_glpk (String solverLibraryName)
	{
		StringBuffer sb = new StringBuffer();
		{
		  	try
		  	{
		  		checkLinearSolver ("glpk" , solverLibraryName);
		  	} catch (java.lang.NoClassDefFoundError e)
		  	{
		  		sb.append("MESSAGE: Class not found. This error can correspond "
		  				+ "to the case when a JAR file "
		  				+ " is not accessible to JOM library. Add it to the classpath, "
		  				+ "or in Net2Plan use the option File->Classpath editor to make it accessible to the algorithms." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	} catch (Exception e)
		  	{
		  		sb.append("MESSAGE: Check failed." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	}
		}
		return sb.toString();
	}

	/** Performs the check for the solver IPOPT, returns a "" String if everything ok. If not, the returned String 
	 * contains the error message including a description message the stack trace of the Exception raised.
	 * @param solverLibraryName The name of the dynamic library file (DLL or .SO file) with the solver 
	 * @return the check String
	 */
	public static String check_ipopt (String solverLibraryName)
	{
		StringBuffer sb = new StringBuffer();
		{
		  	try
		  	{
				OptimizationProblem op = new OptimizationProblem();
				op.addDecisionVariable("x", false, new int[] { 1,3 }, 0, 1);  // name, isInteger, size , minValue, maxValue
				op.setObjectiveFunction("minimize", "x*[1;0;0;;0;1;0;;0;0;1]*x'");
			  	op.addConstraint("sum(x) >= 1.5");  
		  		op.solve("ipopt" , "solverLibraryName" , solverLibraryName);
				DoubleMatrix1D sol = op.getPrimalSolution("x").view1D();
				if (!equalWithMargin (sol.toArray() , new double [] {0.5 , 0.5 , 0.5})) throw new RuntimeException ("The solution returned by the solver is not correct");
		  	} catch (java.lang.NoClassDefFoundError e)
		  	{
		  		sb.append("MESSAGE: Class not found. This error can correspond "
		  				+ "to the case when a JAR file "
		  				+ " is not accessible to JOM library. Add it to the classpath, "
		  				+ "or in Net2Plan use the option File->Classpath editor to make it accessible to the algorithms." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	} catch (Exception e)
		  	{
		  		sb.append("MESSAGE: Check failed." + RETURN);
		  		StringWriter sw = new StringWriter ();
		  		e.printStackTrace(new PrintWriter (sw));
		  		sb.append(sw.toString());
		  	}
		}
		return sb.toString();
	}

	private static boolean equalWithMargin (double [] x , double [] y)
	{
		for (int cont = 0; cont < x.length ; cont ++) if (Math.abs(x[cont] - y[cont]) > 1e-5) return false; 
		return true;
	}
	
}