package net.qsef1256.capstone2022server.api;

import net.qsef1256.capstone2022server.api.enums.ContentType;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 공공 API Connector, 비동기 처리
 */
public class APIConnector implements AutoCloseable, Closeable {

    // TODO: check memory leak

    private HttpURLConnection connection;
    private BufferedReader bufferedReader;

    /**
     * URL 로부터 데이터를 JSON 으로 얻어옵니다.
     *
     * <p><b>주의: </b>비동기로 처리 되어야 합니다.</p>
     *
     * @param urlString net.qsef1256.capstone2022server.api url
     * @return result BufferedReader
     * @throws IOException when can't connect to net.qsef1256.capstone2022server.api url
     * @see #getResult(String, ContentType)
     */
    @NotNull
    public BufferedReader getResult(@NotNull String urlString) throws IOException {
        return getResult(urlString, ContentType.JSON);
    }

    @NotNull
    public BufferedReader getResult(@NotNull String urlString, @NotNull ContentType contentType) throws IOException {
        if (connection != null) close();

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", contentType.getData());

        int code = conn.getResponseCode();

        BufferedReader br = (code >= 200 && code <= 300) ?
                new BufferedReader(new InputStreamReader(conn.getInputStream())) :
                new BufferedReader(new InputStreamReader(conn.getErrorStream()));

        this.connection = conn;
        this.bufferedReader = br;
        return br;
    }

    /**
     * {@link #getResult(String)} 과 같지만 StringBuilder 를 반환합니다.
     *
     * @param urlString net.qsef1256.capstone2022server.api url
     * @return result StringBuilder
     * @throws IOException when can't connect to net.qsef1256.capstone2022server.api url
     * @see #getResult(String)
     */
    @NotNull
    public StringBuilder getResultAsString(@NotNull String urlString) throws IOException {
        return getResultAsString(urlString, ContentType.JSON);
    }

    @NotNull
    public StringBuilder getResultAsString(@NotNull String urlString, ContentType contentType) throws IOException {
        BufferedReader br = getResult(urlString, contentType);

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb;
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
        connection.disconnect();
    }

}
