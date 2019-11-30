package factory;

import entity.RequestEntity;
import exception.HttpRequestParseException;
import entity.RequestEntity.Headers;
import exception.UnSupportRequestMethodException;
import util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.Arrays;

import static entity.HttpRequestMethod.*;

public class RequestEntityFactory {
    public static RequestEntity getEntity(String requestMessage)  {
        RequestEntity entity = new RequestEntity();
        try (BufferedReader reader = new BufferedReader(new StringReader(requestMessage))) {
            System.out.println(requestMessage);
            String[] headLine = reader.readLine().split(" ");
            System.out.println(Arrays.toString(headLine));
            if (!(headLine.length == 3)) {
                throw new RuntimeException(new HttpRequestParseException("invalid http head line"));
            }
            //处理请求头
            Headers headers = new Headers();
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                System.out.println(line);
                StringUtils.setPairString(line, ": ", headers.getHeaders());
            }
            entity.setHeaders(headers);

            //分析headLine
            String uri = URLDecoder.decode(headLine[1], "UTF-8");
            System.out.println(uri);
            entity.setUri(StringUtils.getUriFromRequestUri(uri)); //请求资源路径
            entity.setProtocolVersion(headLine[2]); //http协议版本
            //根据请求方法获取不同地方的请求参数
            if (GET.toString().equals(headLine[0])) {
                entity.setParameters(StringUtils.getParamsFromRequestUri(headLine[1]));
            } else if (POST.toString().equals(headLine[0])) {
                entity.setParameters(StringUtils.getParamsFromRequestBody(reader.readLine()));
            } else {
                throw new UnSupportRequestMethodException(headLine[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
