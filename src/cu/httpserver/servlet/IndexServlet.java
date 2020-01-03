package cu.httpserver.servlet;

import cu.httpserver.annotation.RequestMapping;
import cu.httpserver.entity.RequestEntity;
import cu.httpserver.entity.ResponseEntity;
import cu.httpserver.factory.ResponseEntityFactory;

import java.util.Calendar;

@RequestMapping(uri = "/")
public class IndexServlet extends Servlet {
    @Override
    public ResponseEntity doGet(RequestEntity requestEntity) {
        String name = requestEntity.getParameters().get("name");
        if (name == null) {
            name = "";
        }
        return ResponseEntityFactory.responseEntityBuilder_200().date(Calendar.getInstance())
                .contentType("text/html").contentLength(40 + name.length()).body("<h1>This is a index page, welcome " + name + "!</h1>").build();
    }
}
