package com.lab5;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class ParserErrorHandler implements ErrorHandler {
    public void warning(SAXParseException e) throws SAXException {
        show("Warning", e);
        throw (e);
    }

    public void error(SAXParseException e) throws SAXException {
        show("Error", e);
        throw (e);
    }

    public void fatalError(SAXParseException e) throws SAXException {
        show("Fatal Error", e);
        throw (e);
    }

    private void show(String type, SAXParseException e) {
        System.out.println(type + ": " + e.getMessage());
        System.out.println("Line " + e.getLineNumber() + " Column " + e.getColumnNumber());
        System.exit(0);
    }
}
