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
    public ResponseEntity<Integer> uploadDoc(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("files") MultipartFile[] files) {

        int successCount = 0;
        try {
            // 打印标题和内容
            System.out.println("标题: " + title);
            System.out.println("内容: " + content);

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                // 转换为字节数组
                byte[] fileBytes = file.getBytes();  // <--- 核心转换

                // 保存文件到数据库或其他存储
                docService.saveDoc(fileBytes,title);

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