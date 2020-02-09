package com.github.altergr.meteringdemo.domain.validation;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
class ValidatedDummy {

    public static final String ID_MESSAGE = "id_message";
    public static final String NAME_MESSAGE = "name_message";
    public static final String NESTED_ID_MESSAGE = "nestedId_message";

    @Min(value = 1, message = ID_MESSAGE)
    private Integer id;

    @Size(min = 1, message = NAME_MESSAGE)
    private String name;

    @Valid
    private Nested nested;

    static ValidatedDummy valid() {
        return new ValidatedDummy(1, "name", null);
    }

    static ValidatedDummy idNameInvalid() {
        return new ValidatedDummy(-1, "", null);
    }

    static ValidatedDummy nestedInvalid() {
        return new ValidatedDummy(1, "name", new Nested(-1));
    }

    @Builder
    private static class Nested {
        @Min(value = 5, message = NESTED_ID_MESSAGE)
        private Integer id;
    }

}


