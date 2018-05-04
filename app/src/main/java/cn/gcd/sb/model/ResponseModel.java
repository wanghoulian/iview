package cn.gcd.sb.model;

public class ResponseModel {
    public int code;
    public String result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "code=" + code +
                ", result='" + result + '\'' +
                '}';
    }
}
