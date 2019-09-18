package core.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import core.log.Logs;
import global.CoreConfigs;
import func4j.StringFunc;

public class HttpLog {

    private String method;
    private String url;
    private String protocol;
    private String contentLength;
    private String contentType;
    private List<String> requestHeaders;
    private List<String> parameters;


    private String code;
    private List<String> responseHeaders;
    private String time;
    private String responseMessage;
    private String body;

    private String requestBody;

    private Throwable failedThrowable;
    private boolean isFailed;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Throwable getFailedThrowable() {
        return failedThrowable;
    }

    public void setFailedThrowable(Throwable failedThrowable) {
        this.failedThrowable = failedThrowable;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void addRequestHeader(String header) {
        if (this.requestHeaders == null)
            this.requestHeaders = new ArrayList<String>();
        this.requestHeaders.add(header);
    }

    public void addResponseHeader(String header) {
        if (this.responseHeaders == null)
            this.responseHeaders = new ArrayList<String>();
        this.responseHeaders.add(header);
    }

    public void addParameter(String parameter) {
        if (this.parameters == null)
            this.parameters = new ArrayList<String>();
        this.parameters.add(parameter);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void log() {
        if (!CoreConfigs.configs().isLog()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" \n");
        sb.append(
                "┌──接口请求──────────────────────────────────────────────────────────────────────────────\n");

        if (protocol != null) {
            sb.append("│\t--> protocol:" + protocol + "\n");
        }
        sb.append("│\t--> " + method + "\n");
        sb.append("│\t--> " + url + "\n");

        if (parameters != null) {
            sb.append("│\t--> 请求参数:" + "\n");
            for (String Parameter : parameters) {
                sb.append("│\t-->  " + Parameter + "\n");

            }
        } else {
            sb.append("│\t--> 请求参数:null\n");
        }


        if (contentLength != null)
            sb.append("│\t--> Content-Length: " + contentLength + "-byte\n");
        if (contentType != null)
            sb.append("│\t--> Content-Type: " + contentType + "\n");

        if (requestHeaders != null) {
            sb.append("│\t--> 请求头信息:\n");
            for (String header : requestHeaders) {
                sb.append("│\t-->  " + header + "\n");
            }
        } else {
            sb.append("│\t--> 请求头信息:null\n");
        }

        if (StringFunc.isNotBlank(requestBody)) {
            sb.append("│\t--> 请求体:" + requestBody + "\n");
        }

        sb.append("│\t--------------------------------------->\n");
        sb.append("│\n");
        sb.append("│\t<---------------------------------------\n");

        if (isFailed) {
            sb.append("│\t<-- 请求失败\n");
            if (failedThrowable != null) {
                Logs.w(CoreConfigs.configs().defaultLogTag() + "_API", failedThrowable);
            }
        } else {
            sb.append(
                    "│\t<-- " + code + (responseMessage == null ? "" : (" " + responseMessage)) + (time == null ? "" : (" " + time)) + "\n");

            if (responseHeaders != null) {
                sb.append("│\t<-- 返回头信息:\n");
                for (String header : responseHeaders) {
                    sb.append("│\t<--  " + header + "\n");
                }
            } else {
                sb.append("│\t<-- 返回头信息:null\n");
            }

            sb.append("│\t<-- 返回值:\n");
            sb.append("│\t");
            dealBody(sb, body);
            sb.append("\n");
        }

        sb.append(
                "└───────────────────────────────────────────────────────────────────────────────────────\n");

        Logs.i(CoreConfigs.configs().defaultLogTag() + "_API", sb.toString());
    }

    private String dealBody(StringBuilder sb, String body) {
        try {
            Object parse = JSON.parse(body);

            if (parse instanceof JSONObject) {
                int level = 0;
                for (int i = 0; i < body.length(); i++) {

                    String sub = body.substring(i, i + 1);

                    if ("{".equals(sub)) {
                        level++;
                        sb.append("{\n");
                        getSpace(sb, level);
                    } else if (",".equals(sub)) {
                        sb.append(",\n");
                        getSpace(sb, level);
                    } else if ("}".equals(sub)) {
                        level--;
                        sb.append("\n");
                        getSpace(sb, level);
                        sb.append("}");
                    } else {
                        sb.append(sub);
                    }
                }
                body = sb.toString();
            }

        } catch (Exception e) {
            Logs.w(e);
        }
        return body;
    }

    private void getSpace(StringBuilder sb, int num) {
        sb.append("│\t");
        for (int i = 0; i < num; i++) {
            sb.append("\t");
        }
    }

    public void setBody(String body) {
        this.body = body;
    }


    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

}