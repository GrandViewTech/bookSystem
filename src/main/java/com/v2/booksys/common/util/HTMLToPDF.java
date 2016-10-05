package com.v2.booksys.common.util;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
public class HTMLToPDF {
	
	 protected Dimension format = PD4Constants.A4;
	   protected boolean landscapeValue = false;
	   protected int topValue = 10;
	   protected int leftValue = 10;
	   protected int rightValue = 10;
	   protected int bottomValue = 10;
	   protected String unitsValue = "mm";
	   protected String proxyHost = "";
	   protected int proxyPort = 0;
	   
	   protected int userSpaceWidth = 780;
	   
	   public void convert(File htmlContents, File pdfOutput, final String header) throws Exception{
		   PD4ML pd4ml = new PD4ML();
			 try {                                                              
	           //  pd4ml.setPageSize( landscapeValue ? pd4ml.changePageOrientation( format ): format );
				 pd4ml.setPageSize( PD4Constants.A4);
	          } catch (Exception e) {
	             e.printStackTrace();
	          }
	            
	      if ( unitsValue.equals("mm") ) {
	             pd4ml.setPageInsetsMM( new Insets(topValue, leftValue,
	bottomValue, rightValue) );
	      } else {
	             pd4ml.setPageInsets( new Insets(topValue, leftValue,
	bottomValue, rightValue) );
	      }
	      /**
	       * Create header
	       */
//	      PD4PageMark header2 = new PD4PageMark() {  
//	    	    public String getHtmlTemplate(int pageNumber) {  
//	    	        if ( pageNumber >= 1 ) { 
//	    	        	System.out.println("header is "+header);
//	    	            return header;  
//	    	        }
//					return header; 
//	    	    }  
//	    	}; 
//	      	PD4PageMark header2 = new PD4PageMark();
//	    	header2.setHtmlTemplate("<font color=\"red\"><i>page eeee $[page] of $[total]</i></font>");  
//	    	header2.setAreaHeight(-1);
//	    	//pd4ml.set
//	    	pd4ml.setPageHeader(header2);
//	      PD4PageMark footer = new PD4PageMark();  
//	      footer.setPageNumberTemplate("page $[page] of $[total]");  
//	      footer.setTitleAlignment(PD4PageMark.LEFT_ALIGN);  
//	      footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);  
//	      footer.setColor(new Color(0x008000));  
//	      footer.setInitialPageNumber(1);  
//	      footer.setPagesToSkip(1);  
//	      footer.setFontSize(14);  
//	      footer.setAreaHeight(18);     
//	      pd4ml.setPageFooter(footer);  


	      /**
	       * 
	       */
	      pd4ml.setHtmlWidth( userSpaceWidth );
	      FileOutputStream fos = new FileOutputStream(pdfOutput);
	      FileReader fileReader = new FileReader(htmlContents);
	      pd4ml.render("file:"+htmlContents.getPath(), fos); 
	      fos.close();
	   }
	   
	   
	   public void convert(String file) throws Exception{
		   PD4ML pd4ml = new PD4ML();
			 try {                                                              
	           //  pd4ml.setPageSize( landscapeValue ? pd4ml.changePageOrientation( format ): format );
				 pd4ml.setPageSize( PD4Constants.A4);
	          } catch (Exception e) {
	             e.printStackTrace();
	          }
	            
	      if ( unitsValue.equals("mm") ) {
	             pd4ml.setPageInsetsMM( new Insets(topValue, leftValue,
	bottomValue, rightValue) );
	      } else {
	             pd4ml.setPageInsets( new Insets(topValue, leftValue,
	bottomValue, rightValue) );
	      }

	      pd4ml.setHtmlWidth( userSpaceWidth );
	      FileOutputStream fos = new FileOutputStream(new File("template1.pdf"));
	      pd4ml.render("file:"+file, fos);
	      fos.close();
	   }
	
	public static void main(String args[]) throws Exception{
		
		HTMLToPDF htmlToPDF = new HTMLToPDF();
		htmlToPDF.convert("template2.html");
		
	}

}
