package com.srini.sample.pdf.service;


import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@RestController
@RequestMapping(path="/sample")
public class SampleService {
	
	@RequestMapping(path="/exportPdf", method = RequestMethod.POST, produces = "application/pdf")
	ResponseEntity<byte[]> exportPdf(@RequestParam("inputToFile") String inputToFile){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Content-Disposition", "filename=sample.pdf");
		return new ResponseEntity<byte[]>(getFileBytePdf(inputToFile),headers,HttpStatus.OK);
	}

	@RequestMapping(path="/exportzip", method=RequestMethod.POST,produces="application/zip")
	ResponseEntity<byte[]> exportZip(@RequestParam("inputToCsv") String inputToCsv){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/zip"));
		headers.add("Content-Disposition", "filename=sample.zip");
		return new ResponseEntity<byte[]>(getFileByteZip(inputToCsv),headers,HttpStatus.OK);
	}
	
	
	private byte[] getFileByteZip(String inputToCsv) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(ZipOutputStream zipOutputStream = new ZipOutputStream(baos);){
			zipOutputStream.putNextEntry(new ZipEntry("sample.csv"));
			
			zipOutputStream.write(inputToCsv.getBytes());
		}catch(Exception e){
			
		}
		
		return baos.toByteArray();
	}

	private byte[] getFileBytePdf(String inputToFile) {
		Document document = new Document();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, bos);
			document.open();
			document.add(new Paragraph("A Hello World PDF document with "+inputToFile));
			document.close();
			writer.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		return bos.toByteArray();
	}
	
	@RequestMapping(path="/first")
	String getSampleString(){
		return "hello World";
	}
	
	
}