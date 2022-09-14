package com.parser.orderparser.enums;

/**
 * @author Arun Singh
 * Enum for Parser Type.
 * For Any New ParserType, New Enum to be Defined.
 */

public enum ParserType {
    CSV("csv"), JSON("json");

    private final String label;

    private ParserType(String label) {
        this.label= label;
    }

    public static ParserType valueOfLabel(String label) {
        for (ParserType parserType : values()) {
            if (parserType.label.equals(label.toLowerCase())
                    || parserType.label.equals(label.toUpperCase())
                    || parserType.label.equals(label)) {
                return parserType;
            }
        }
        return null;
    }
}