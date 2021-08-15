package com.nhsoft.demo.Dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -5809782578272943966L;
    private Integer code;
    private String msg;
    private T data;

    private Response() {

    }

    public Response(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static Response empty() {
        Response response = new Response();
        response.setCode(0);
        return response;
    }

    public static Response error(String msg) {
        return error(-1, msg);
    }

    public static Response error(Integer code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> response(int code, T data, String msg) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    public static <T> List<Response> responses(List<T> data) {
        List<Response> responses = new ArrayList<Response>();
        for(T i:data){
            responses.add(new Response(0, i));
        }
        return responses;
    }

    public static <T> Response response2(String msg) {
        Response response = new Response();
        response.setCode(0);
        response.setMsg(msg);
        return response;
    }
}