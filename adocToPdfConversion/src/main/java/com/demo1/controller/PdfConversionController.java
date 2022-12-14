package com.demo1.controller;



import org.asciidoctor.Asciidoctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.asciidoctor.OptionsBuilder.options;

@RestController
public class PdfConversionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfConversionController.class);


	@GetMapping(value = "/convertAdocToPdf")
	public void downloadApiDocument(final jakarta.servlet.http.HttpServletResponse response) {
		LOGGER.info("Start of Download API method");
		try {
			generateAPIPdfFiles();
		} catch (final Exception e) {
			LOGGER.error("Failed to generate pdf documents", e);
		}
	}

	@SuppressWarnings("deprecation")
	private void generateAPIPdfFiles() {
		final Asciidoctor asciidoctor = create();
		asciidoctor.convertFile(new File("src/main/doc/DemoFile.adoc"), new HashMap<String, Object>());
		FileReader reader = null;
		try {
			reader = new FileReader(new File("src/main/doc/DemoFile.adoc"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringWriter writer = new StringWriter();

		
		try {
			asciidoctor.convert(reader, writer, options().asMap());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//asciidoctor.convert(reader, writer, options().asMap());
		
		Map<String, Object> options = options().inPlace(true).backend("pdf").asMap();

		String outfile = asciidoctor.convertFile(new File("src/main/doc/DemoFile.adoc"), options);

	}
}