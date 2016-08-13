package com.v2.booksys.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HtmlToPdf_IText {

	
	public void convert(File htmlContents, File pdfOutput, final String header) throws Exception{
		// step 1
        try {
			Document document = new Document();
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfOutput));
			// step 3
			document.open();
			// step 4
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,
			        new FileInputStream(htmlContents)); 
			//step 5
			 document.close();
 
			System.out.println( "PDF Created!" );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This is what we use.
	 * @param htmlContents
	 * @param pdfOutput
	 * @param header
	 * @throws Exception
	 */
	public void convertFlyingSoccer(File htmlContents, File pdfOutput, final String header) throws Exception{
		String html  = FileUtils.readFileToString(htmlContents);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString( html );
		renderer.layout();
		//String fileNameWithPath = outputFileFolder + "PDF-FromHtmlString.pdf";
		FileOutputStream fos = new FileOutputStream( pdfOutput);
		try {
			renderer.createPDF( fos );
		} catch (com.lowagie.text.DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fos.close();

		System.out.println( "File 2: flyingS.pdf created." );
	}
}
