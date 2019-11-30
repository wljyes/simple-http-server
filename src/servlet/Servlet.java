package servlet;

import entity.RequestEntity;
import entity.ResponseEntity;

public abstract class Servlet {
    public void service(RequestEntity requestEntity, ResponseEntity responseEntity) {
        switch (requestEntity.getRequestMethod()) {
            case GET: doGet(requestEntity, responseEntity); break;
            case POST: doPost(requestEntity, responseEntity); break;
        }
    }

    public abstract void doGet(RequestEntity requestEntity, ResponseEntity responseEntity);

    public abstract void doPost(RequestEntity requestEntity, ResponseEntity responseEntity);
}
