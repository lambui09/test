package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
public class AllTestsRunner {
	private static Double[][] readExcel() throws IOException {
		Double array[][] = new Double[8][4]; //8 is number of test case : can change
		int c=0, h=0;
		FileInputStream file = new FileInputStream("test.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);
		FormulaEvaluator formula = wb.getCreationHelper().createFormulaEvaluator();
		int r =0;
		for(Row row:sheet) {
			int i=0;
			for(Cell cell:row) {
				if(i>4) continue;
				else {
					switch(formula.evaluate(cell).getCellType()){
						case NUMERIC:
							array[h][c++] = cell.getNumericCellValue();
							break;
					}
					i++;
				}
			}
			if(r!=0) {h++; c=0;}
			else r++;
		}
		wb.close();
		file.close();
		return array;
	}
	public static void writeExcel(int arrayFailIndex [],double arrayResult []) throws IOException{
		FileOutputStream file = new FileOutputStream("test.xlxx");
		XSSFWorkbook workbook = new XSSFWorkbook("test.xlsx");
		XSSFSheet workSheet=workbook.getSheet("Sheet1");
		XSSFCell cell;
		cell = workSheet.getRow(0).createCell(5);
		cell.setCellValue("Actual");
		cell = workSheet.getRow(0).createCell(6);
		cell.setCellValue("Result");
		for(int i=1;i<9;i++) {
			cell = workSheet.getRow(i).createCell(5);
			cell.setCellValue(arrayResult[i-1]);
			if(arrayFailIndex[i-1]==0) {				
				cell = workSheet.getRow(i).createCell(6);
				cell.setCellValue("fail");
			}
			else {
				cell = workSheet.getRow(i).createCell(6);
				cell.setCellValue("pass");
			}
		}
		workbook.write(file);
		workbook.close();
		file.close();
	}
	public static void main(String[] args) throws IOException {
		Double[][] arrayExpectedResult = readExcel();
		double arrayActualResult [] = new double [8];	
		for(int i=0;i<8;i++) {
			arrayActualResult[i] = arrayExpectedResult[i][3];
		}
		int arrayFailIndex [] =  {1,1,1,1,1,1,1,1};
		Result result = JUnitCore.runClasses(TestSuite.class);
		for(Failure failure : result.getFailures()) {
			String result_temp="";
			int index_temp= Integer.parseInt(String.valueOf(failure.getTestHeader().charAt(15)));
			arrayFailIndex[index_temp] = 0;
			String Message = failure.getMessage();
			for(int i=Message.length()-2; Message.charAt(i)!='<';i--) {
				result_temp = String.valueOf(Message.charAt(i)) + result_temp;
			}
			double result_temp_to_array = Double.parseDouble(result_temp);
			arrayActualResult[index_temp] = result_temp_to_array;
		}
		writeExcel(arrayFailIndex,arrayActualResult);
		System.out.println("Testing successfully");
	}

}
