package com.example.projectCommon.Controller;

import com.example.projectCommon.Model.ReadRecord;
import com.example.projectCommon.Service.BackGroundOps;
import com.example.projectCommon.Utils.OKHTTPHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    BackGroundOps backGroundOps;


    @Autowired
    OKHTTPHelper okhttpHelper;

    @GetMapping(value = "/test")
    public @ResponseBody ResponseEntity<String> test() {
        System.out.println("Execute method with configured executor - " + Thread.currentThread().getName() + "INNNNNNNN");
//        return ResponseEntity.ok().body(backGroundOps.test());
        return ResponseEntity.ok().body("OK");
    }
    @GetMapping(value = "/testHTTPGetWithHeaders")
    public @ResponseBody ResponseEntity<String> testHTTPGetWithHeaders() {
        HashMap<String, String> temp = new HashMap<>();
        temp.put("123", "456");
        temp.put("headerkey", "headervalue");
        logger.info(okhttpHelper.doHttpGetString("http://localhost:8080/test", temp));
        return ResponseEntity.ok().body("TEST");
    }

    @GetMapping(value = "/testHTTPGet")
    public @ResponseBody ResponseEntity<String> testHTTPGet() {
        logger.info(okhttpHelper.doHttpGetString("http://localhost:8080/test"));
        return ResponseEntity.ok().body("TEST");
    }

    @GetMapping(value = "/testURLParameter")
    public @ResponseBody ResponseEntity<String> testURLParameter(@RequestParam("bookId") String bookId,
                                                                 @RequestParam("labelId") String labelId) {

        return ResponseEntity.ok().body(backGroundOps.test());
    }

    @GetMapping(value = "/testURLWithPathVar/{id}")
    public @ResponseBody ResponseEntity<String> testURLWithPathVar(@PathVariable Integer id) {
        logger.info("ID : " + id);
        return ResponseEntity.ok().body(backGroundOps.test());
    }

    @GetMapping(value = "/testHeaders")
    public @ResponseBody ResponseEntity<String> testHeaders(@RequestHeader Map<String, String> headers) {
        logger.info(headers.toString());
        return ResponseEntity.ok().body(backGroundOps.test());
    }

    @GetMapping(value = "/testImage")
    public @ResponseBody ResponseEntity<byte[]> testImage() {

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new byte[10]);
    }

    @PostMapping(value = "/testPost")
    public @ResponseBody ResponseEntity<String> testPost(@RequestBody ReadRecord readRecord) {
        return ResponseEntity.ok().body(backGroundOps.test());
    }


    @PostMapping(value = "/testFormData", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> testFormData(@RequestParam("bookId") String bookId,
                                                             @RequestParam("bookContent") MultipartFile bookContent) throws IOException {

        String content = new String(bookContent.getBytes());
        logger.info(bookContent.getOriginalFilename());
        logger.info(bookContent.getContentType());
        logger.info(bookContent.getName());
        logger.info(content);
        return ResponseEntity.ok().body(backGroundOps.test());
    }
}
