package net.qsef1256.capstone2022server.api.enums;

import lombok.Getter;

/**
 * HTML Content Type
 *
 * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Content-Type">Mozilla Docs</a>
 */
public enum ContentType {
    TEXT("text/html"),
    JSON("application/json"),
    XML("application/xml");

    @Getter
    private final String data;

    ContentType(String data) {
        this.data = data;
    }
}