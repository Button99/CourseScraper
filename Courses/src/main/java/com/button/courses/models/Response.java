package com.button.courses.models;

import lombok.Data;

@Data
public class Response {
    private String url, title;

    @Override
    public String toString() {
        return "Response{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
