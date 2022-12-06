package com.soprasteria.springbatchexcelfileupload.reader;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.soprasteria.springbatchexcelfileupload.model.User;

public class ExcelUserReader implements ItemReader<User> {

	Iterator<User> results;
	
	private String pathToFile;

	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		if (this.results == null) {
            Resource resource = new FileSystemResource(pathToFile);
            List<User> list = readFromExcel(resource);
            this.results = list.iterator();
        }
        return this.results.hasNext() ? this.results.next() : null;
	}
	
	public List<User> readFromExcel(Resource resource) throws IOException {
		List<User> list = null;
		if(pathToFile.endsWith(".xls")) {
			list = readFromXLS(resource);
		} else if(pathToFile.endsWith(".xlsx")) {
			list = readFromXLSX(resource);
		}
		return list;
	}

	private List<User> readFromXLS(Resource resource) throws IOException {

		List<User> result = new ArrayList<>();
		InputStream input = resource.getInputStream();
		int rowIndex = 1;

		HSSFWorkbook myExcelBook = new HSSFWorkbook(input);
		HSSFSheet myExcelSheet = myExcelBook.getSheet("Foglio1");
		
		String firstName = null, lastName = null, address = null, email = null, phone = null;
		do {
			HSSFRow row = myExcelSheet.getRow(rowIndex);
			if (row == null)
				break;
			if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				firstName = row.getCell(0).getStringCellValue().trim();
			}
			if (row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				lastName = row.getCell(1).getStringCellValue().trim();
			}
			if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				address = row.getCell(2).getStringCellValue().trim();
			}
			if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				email = row.getCell(3).getStringCellValue().trim();
			}
			if (row.getCell(4).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				DecimalFormat df = new DecimalFormat("#");
				phone = df.format(row.getCell(4).getNumericCellValue());
			}
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setEmail(email);
			user.setPhone(phone);
			result.add(user);

			rowIndex++;
		} while (true);

		myExcelBook.close();
		return result;

	}
	
	private List<User> readFromXLSX(Resource resource) throws IOException {

		List<User> result = new ArrayList<>();
		InputStream input = resource.getInputStream();
		int rowIndex = 1;

		XSSFWorkbook myExcelBook = new XSSFWorkbook(input);
		XSSFSheet myExcelSheet = myExcelBook.getSheet("Foglio1");
		
		String firstName = null, lastName = null, address = null, email = null, phone = null;
		do {
			XSSFRow row = myExcelSheet.getRow(rowIndex);
			if (row == null)
				break;
			if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				firstName = row.getCell(0).getStringCellValue().trim();
			}
			if (row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				lastName = row.getCell(1).getStringCellValue().trim();
			}
			if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				address = row.getCell(2).getStringCellValue().trim();
			}
			if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				email = row.getCell(3).getStringCellValue().trim();
			}
			if (row.getCell(4).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				DecimalFormat df = new DecimalFormat("#");
				phone = df.format(row.getCell(4).getNumericCellValue());
			}
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setEmail(email);
			user.setPhone(phone);
			result.add(user);

			rowIndex++;
		} while (true);

		myExcelBook.close();
		return result;

	}

	public void setPathToFile(final String pathToFile) {
		this.pathToFile = pathToFile;
	}

}
