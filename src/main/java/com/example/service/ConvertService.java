package com.example.service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;


@Component("convertService")
@Service
public class ConvertService {
	
	@Autowired UploadUtil uploadUtil;
	
	    //Retrieving data from excel sheet in JSON
		public List<Map<String, String>> retrieve() throws Exception {
			
			String excelFilePath = "C:\\Users\\pallab\\Documents\\EmployeeData.xlsx";
			
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));


			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);

			Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);
			
			//getting the header row
			Row headerRow = rowStreamSupplier.get().findFirst().get();
			
			//listing the headers
			List<String> headerCells = uploadUtil.getStream(headerRow)
					.map(Cell:: getStringCellValue)
					.collect(Collectors.toList());
			
			int colCount = headerCells.size();
			
			inputStream.close();
			
			 return rowStreamSupplier.get().map(row -> {
					List<String> cellList = uploadUtil.getStream(row)
							.map(Cell::getStringCellValue)
							.collect(Collectors.toList());
					
					return IntStream.range(0, colCount)
							.boxed()
							.collect(Collectors.toMap(index -> headerCells.get(index), index -> cellList.get(index)));
			 }).collect(Collectors.toList());
				
			 }
		
		
	    //Dynamically Adding data
		public void add(String name, String dept, String cmpny) throws Exception {
			Fillo fillo = new Fillo();
			
			Connection connection=fillo.getConnection("C:\\Users\\pallab\\Documents\\EmployeeData.xlsx");
			
			String strQuery="Insert into Sheet1 (EmpName, Department, Company) values ('"+name+"', '"+dept+"', '"+cmpny+"')";
			
			connection.executeUpdate(strQuery);
			
			connection.close();
			
		}
		
		//Dynamically updating data
		public int updateData(String empName, String dept, String cmpny) throws FilloException {
					
			Fillo fillo = new Fillo();
			
			Connection connection=fillo.getConnection("C:\\Users\\pallab\\Documents\\EmployeeData.xlsx");
			
			String strQuery="Update Sheet1 Set Company= '"+cmpny+"' where EmpName='"+empName+"' and Department='"+dept+"'";
			
			int result = connection.executeUpdate(strQuery);
			
			connection.close();
			
			return result;
			
		}
		
		//Dynamically deleting data
		public void delete(String cellValue) throws FilloException {
			
			Fillo fillo = new Fillo();
			
			Connection connection=fillo.getConnection("C:\\Users\\pallab\\Documents\\EmployeeData.xlsx");
			
			String strQury = "Delete from Sheet1 where Company='"+cellValue+"'";
			
			connection.executeUpdate(strQury);
			
			connection.close();
			
		}
}
