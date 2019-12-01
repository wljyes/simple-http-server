package servlet;

import annotation.RequestMapping;
import entity.RequestEntity;
import entity.ResponseEntity;
import factory.ResponseEntityFactory;

import java.util.Calendar;

@RequestMapping(uri = "/hello")
public class HelloServlet extends Servlet {
    @Override
    public ResponseEntity doPost(RequestEntity requestEntity) {
        String name = requestEntity.getParameters().get("name");
        if (name == null) {
            name = "";
        }
        return ResponseEntityFactory.responseEntityBuilder_200().date(Calendar.getInstance())
                .contentType("text/html").contentLength(18 + name.length()).body("<h1>Welcome " + name + "!</h1>").build();
    }
}
