package handler;

import entity.RequestEntity;
import entity.ResponseEntity;
import factory.ResponseEntityBuilder;
import factory.ResponseEntityFactory;

import java.io.*;
import java.util.Calendar;

public class StaticResourceRequestHandler implements HttpRequestHandler {
    @Override
    public boolean canHandle(String uri) {
        if (uri.contains(".")) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity handle(RequestEntity requestEntity) {
        ResponseEntityBuilder builder = ResponseEntityBuilder.builder().httpVersion("HTTP/1.1");
        String path = requestEntity.getUri();
        path = "E:/http/static/" + path;

        if (path.endsWith(".html") || path.endsWith(".htm")) {
            File file = new File(path);
            if (!file.exists()) {
                return ResponseEntityFactory.responseEntity_404(Calendar.getInstance());
            } else if (file.isDirectory()) {
                return ResponseEntityFactory.responseEntity_403(Calendar.getInstance());
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                // ... handle IO exception
            }
            return ResponseEntityFactory.responseEntityBuilder_200()
                    .date(Calendar.getInstance()).contentLength(sb.length())
                    .contentType("text/html").body(sb.toString()).build();
        }
        if (path.endsWith(".jpg") || path.endsWith(".png")) {
            //todo: 图片处理

        }
        return null;
    }
}
