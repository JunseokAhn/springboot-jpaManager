package springboot.jpaManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class CompanyApiDTO {

    @Data
    @AllArgsConstructor
    public static class ListResponse<T> {

        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class Response {

        private Long id;
    }

    @Data
    public static class Request {
        @NotEmpty
        private String name;
        @Valid
        private AddressDTO address;
    }

    @Data
    public static class UpdateResponse {

        private Long id;
        private String name;
        private AddressDTO address;
    }

    @Data
    public static class UpdateRequest {

        private String name;
        private AddressDTO address;
    }
}
