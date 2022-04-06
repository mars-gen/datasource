package com.example.pan.idcode;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-05
 */
public enum Code {
    SUCCESS("0"),FAIL("-1001"), EXCEP("-9001");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Code(String code) {
        this.code = code;
    }
}
