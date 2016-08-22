package org.bysocket.excel;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.fastframework.mvc.view.document.AbstractExcelView;

public class ExcelOne extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) {
		Sheet sheet = workbook.createSheet();
		Set<Entry<String, Object>> keySet = model.entrySet();
		Iterator<Entry<String, Object>> keys = keySet.iterator();
		int i = 0;
		while(keys.hasNext()){
			Row row = sheet.createRow(i);
			Entry<String, Object> key = keys.next();
			row.createCell(0).setCellValue(key.getKey());
			row.createCell(1).setCellValue(key.getValue()+"");
			i++;
		}
	}
}
