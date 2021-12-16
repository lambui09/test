package com.company;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.annotation.processing.Generated;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Generated(value = "org.junit-tools-1.1.0")
@RunWith(Parameterized.class)
public class TriangleTest {
	private final double expectedResult;
	private final double a;
	private final double b;
	private final double c;
	private Triangle triangle;
	@Before
	public void initialize() {
		triangle = new Triangle(a,b,c);
	}
	
	private static Double[][] readExcel() throws IOException {
		Double array[][] = new Double[8][4];
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

	public TriangleTest( double a, double b, double c, double expectedResult) {
		this.expectedResult = expectedResult;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Parameterized.Parameters
	public static Collection triangleNe() throws IOException {
		Double array[][] = readExcel();
		return Arrays.asList(array);
	}
	@Test
	public void testIsTriangle() throws Exception {
		assertEquals(expectedResult,triangle.isTriangle());
	}
}