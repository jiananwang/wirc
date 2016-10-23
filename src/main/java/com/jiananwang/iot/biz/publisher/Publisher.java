package com.jiananwang.iot.biz.publisher;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiananwang.iot.parser.model.ImpinjLabelResult;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by root on 16-10-23.
 */
public class Publisher {
    public static void post(List<ImpinjLabelResult> data, Logger logger) {

        final String uri = "http://service.quartzx.com:8080/data-collector/v1.0/collector";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        // construct the uploading object
        JSONArray jsonArray = new JSONArray();
        for (ImpinjLabelResult r : data) {
            JSONObject object = new JSONObject();
            object.put("id", "12345");
            object.put("timestamp", new DateTime(DateTimeZone.UTC).toString());
            object.put("tagId", r.getEPC());

//            jsonArray.put(JSONObject.valueToString(object));
            jsonArray.add(object);
        }

        String jsonString = jsonArray.toJSONString();

        HttpEntity<String> entity = new HttpEntity(jsonString, headers);


        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            logger.debug(">>> uploaded");
        }catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }
}
