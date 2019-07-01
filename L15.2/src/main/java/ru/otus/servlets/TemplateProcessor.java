package ru.otus.servlets;

import freemarker.template.*;

import java.io.*;
import java.util.*;

public class TemplateProcessor {
	private Configuration configuration;

	public TemplateProcessor(String HTML_DIR) {
		configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setClassForTemplateLoading(this.getClass(), HTML_DIR);
		configuration.setDefaultEncoding("UTF-8");
	}

	String getPage(String filename, Map<String, Object> data) throws IOException {
		try (Writer stream = new StringWriter()) {
			Template template = configuration.getTemplate(filename);
			template.process(data, stream);
			return stream.toString();
		} catch (TemplateException e) {
			throw new IOException(e);
		}
	}
}
