package org.fastframework.mvc.view.document;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.fastframework.mvc.view.AbstractView;

/**
 * Excel document view 用 Apache POI
 * 
 * @author Administrator
 *
 */
public abstract class AbstractExcelView extends AbstractView {

	public AbstractExcelView() {
		setContentType("application/vnd.ms-excel");
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 创建 workbook
		Workbook workbook = createWorkbook(model, request);
		// 绑定数据
		buildExcelDocument(model, workbook, request, response);
		// 输出
		renderWorkbook(workbook, response);
	}

	protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
		return new HSSFWorkbook();
	}

	protected abstract void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response);

	protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
		response.setContentType(getContentType());
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		// Closeable only implemented as of POI 3.10
		if (workbook instanceof Closeable) {
			((Closeable) workbook).close();
		}
	}
}
