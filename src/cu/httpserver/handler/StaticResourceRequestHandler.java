package cu.httpserver.handler;

import cu.httpserver.entity.RequestEntity;
import cu.httpserver.entity.ResponseEntity;
import cu.httpserver.factory.ResponseEntityBuilder;
import cu.httpserver.factory.ResponseEntityFactory;

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

        //如果请求的问html
        File file = new File(path);
        if (!file.exists()) { //文件不存在返回404
            return ResponseEntityFactory.responseEntity_404(Calendar.getInstance());
        } else if (file.isDirectory()) { //文件为目录返回403
            return ResponseEntityFactory.responseEntity_403(Calendar.getInstance());
        }

        if (path.endsWith(".html") || path.endsWith(".htm")) {
            StringBuilder sb = new StringBuilder();
            //读取文件
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                // ... handle IO cu.httpserver.exception
            }
            //返回资源
            return ResponseEntityFactory.responseEntityBuilder_200()
                    .date(Calendar.getInstance()).contentLength(sb.length())
                    .contentType("text/html").body(sb.toString()).build();
        }
        if (path.endsWith(".jpg") || path.endsWith(".png")) {
            //todo: 图片处理

        }
        return ResponseEntityFactory.responseEntity_403(Calendar.getInstance());
    }
}
