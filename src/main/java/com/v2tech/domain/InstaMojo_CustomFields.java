package com.v2tech.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class InstaMojo_CustomFields {
    public final Field_53399 field_53399;
    public final Field_65046 field_65046;

    @JsonCreator
    public InstaMojo_CustomFields(@JsonProperty("field_53399") Field_53399 field_53399, @JsonProperty("field_65046") Field_65046 field_65046){
        this.field_53399 = field_53399;
        this.field_65046 = field_65046;
    }

    public static final class Field_53399 {
        public final boolean required;
        public final String value;
        public final String label;
        public final String type;

        @JsonCreator
        public Field_53399(@JsonProperty("required") boolean required, @JsonProperty("value") String value, @JsonProperty("label") String label, @JsonProperty("type") String type){
            this.required = required;
            this.value = value;
            this.label = label;
            this.type = type;
        }
    }

    public static final class Field_65046 {
        public final boolean required;
        public final String value;
        public final String label;
        public final String type;

        @JsonCreator
        public Field_65046(@JsonProperty("required") boolean required, @JsonProperty("value") String value, @JsonProperty("label") String label, @JsonProperty("type") String type){
            this.required = required;
            this.value = value;
            this.label = label;
            this.type = type;
        }
    }
}