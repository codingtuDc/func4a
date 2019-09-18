package core.net;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class BaseInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private boolean isLog;

    public BaseInterceptor() {
        this(false);
    }

    public BaseInterceptor(boolean isLog) {
        this.isLog = isLog;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();

        //添加参数
        HttpUrl.Builder HttpUrlBuilder = original.url().newBuilder();

        addParameters(HttpUrlBuilder);

        HttpUrl url = HttpUrlBuilder.build();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.url(url);

        //添加请求头
        addHeaders(requestBuilder);

        return interceptForLog(chain, requestBuilder.build());

    }

    public Response interceptForLog(Chain chain, Request request) throws IOException {

        //获取打印信息
        if (!isLog) {
            try {
                return chain.proceed(request);
            } catch (Exception e) {
                throw e;
            }
        }

        HttpUrl url = request.url();

        HttpLog httpLog = new HttpLog();

        RequestBody requestBody = request.body();

        Connection connection = chain.connection();

        httpLog.setMethod(request.method());

        httpLog.setUrl(url.toString());

        int size = url.querySize();
        for (int i = 0; i < size; i++) {
            httpLog.addParameter(url.queryParameterName(i) + "=" + url.queryParameterValue(i));
        }

        if (connection != null)
            httpLog.setProtocol(connection.protocol().toString());

        if (requestBody != null) {

            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {
                httpLog.setRequestBody(buffer.readString(charset));
            }

            if (requestBody.contentType() != null) {
                httpLog.setContentType(requestBody.contentType().toString());
            }
            if (requestBody.contentLength() != -1) {
                httpLog.setContentLength(requestBody.contentLength() + "");
            }
        }

        Headers headers = request.headers();

        String name = null;

        for (int i = 0, count = headers.size(); i < count; i++) {
            name = headers.name(i);
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length"
                    .equalsIgnoreCase(name)) {
                httpLog.addRequestHeader(name + ": " + headers.value(i));
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            httpLog.setFailed(true);
            httpLog.setFailedThrowable(e);
            httpLog.log();
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";

        httpLog.setCode(response.code() + "");
        httpLog.setResponseMessage(response.message());
        httpLog.setTime("(" + tookMs + "ms)");

        headers = response.headers();
        String value = null;

        for (int i = 0; i < headers.size(); i++) {

            //获取返回cookie
            name = headers.name(i);
            value = headers.value(i);

            httpLog.addResponseHeader(name + ": " + value);


            //                        if ("set-cookie".equalsIgnoreCase(name)) {
            //                            cookieDb.cacheCookie(url, value);
            //                        }

        }

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        if (contentLength != 0) {
            httpLog.setBody(buffer.clone().readString(UTF8));
        }

        httpLog.log();

        return response;
    }

    protected void addParameters(HttpUrl.Builder httpUrlBuilder) {
    }

    private void addHeaders(Request.Builder requestBuilder) {
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
