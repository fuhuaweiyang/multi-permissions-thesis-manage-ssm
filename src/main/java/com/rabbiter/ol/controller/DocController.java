package com.rabbiter.ol.controller;

import com.rabbiter.ol.entity.DocEntity;
import com.rabbiter.ol.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/docs")
public class DocController {

    @Autowired
    private DocService docService;

    @RequestMapping("/upload")
    public ResponseEntity<Integer> uploadDoc(@RequestParam("file") MultipartFile[] files) {
//        try {
////            String filePath = "C:\\Users\\18133\\Desktop\\shuiyue.doc";  // 替换成你的 Word 文件路径
//            DocEntity docEntity = docService.saveDoc(null);
//            return ResponseEntity.ok(docEntity.getId());
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
        int successCount = 0;
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                // 转换为字节数组
                byte[] fileBytes = file.getBytes();  // <--- 核心转换

                docService.saveDoc(fileBytes);
                // 示例：打印基本信息
                System.out.println("文件名：" + file.getOriginalFilename());
                System.out.println("文件类型：" + file.getContentType());
                System.out.println("文件大小：" + fileBytes.length + " bytes");

                successCount++;
            }
            return ResponseEntity.ok(successCount);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadDoc(@PathVariable Integer id) {
        try {
            Resource resource = docService.getDocById(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document.doc\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoc(@PathVariable Integer id) {
        try {
            docService.deleteDoc(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}