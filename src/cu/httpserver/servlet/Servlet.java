package cu.httpserver.servlet;

import cu.httpserver.entity.RequestEntity;
import cu.httpserver.entity.ResponseEntity;
import cu.httpserver.factory.ResponseEntityFactory;

import java.util.Calendar;

public abstract class Servlet {
    public ResponseEntity service(RequestEntity requestEntity) {
        switch (requestEntity.getRequestMethod()) {
            case GET: return doGet(requestEntity);
            case POST: return doPost(requestEntity);
        }
        return ResponseEntityFactory.responseEntity_404(Calendar.getInstance()); //请求方法无效，返回404
    }

    public Servlet() {}

    public ResponseEntity doGet(RequestEntity requestEntity) {
        return ResponseEntityFactory.responseEntity_405(Calendar.getInstance());
    }

    public ResponseEntity doPost(RequestEntity requestEntity) {
        return ResponseEntityFactory.responseEntity_405(Calendar.getInstance());
    }
}
