package com.rabbiter.ol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("docList") // 指定数据库表名
public class DocEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private byte[] files; // BLOB 类型
    private String txt;   // TEXT 类型
    private String title;   // TEXT 类型
    private Long stuId;   // BIGINT 类型（学生ID）
    private Long teacherId; // BIGINT 类型（教师ID）
    public boolean isEmpty() {
        return this.id == null &&
                this.files == null &&
                (this.txt == null || this.txt.isEmpty()) &&
                (this.title == null || this.title.isEmpty()) &&
                this.stuId == null &&
                this.teacherId == null;
    }
    @Override
    public String toString() {
        return "DocEntity{" +
                "id=" + id +
                ", files=" + (files != null ? "byte[" + files.length + "]" : "null") +
                ", txt='" + txt + '\'' +
                ", title='" + title + '\'' +
                ", stuId=" + stuId +
                ", teacherId=" + teacherId +
                '}';
    }
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public byte[] getFiles() { return files; }
    public void setFiles(byte[] files) { this.files = files; }

    public String getTxt() { return txt; }
    public void setTxt(String txt) { this.txt = txt; }

    public String getTitle() { return title; }
    public void setTitle(String txt) { this.title = txt; }

    public Long getStuId() { return stuId; }
    public void setStuId(Long stuId) { this.stuId = stuId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}
