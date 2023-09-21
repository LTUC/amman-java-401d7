package com.LTUC.springSecurityIndustryWorld.bo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class Response<T> {
    private T data;

    private Integer code;

    private Long allRecords;

    private String message;

    private Boolean success = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return Objects.equals(data, response.data) &&
                Objects.equals(code, response.code) &&
                Objects.equals(allRecords, response.allRecords) &&
                Objects.equals(message, response.message) &&
                Objects.equals(success, response.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, code, allRecords, message, success);
    }
}

