package com.example.zdm.yourdaily.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 标签数据类
 * Created by ZDM on 2019/12/12.
 */
@Entity
public class TagEntity {

    @Id
    private Long id;

    private Long idItem;

    private Long idDiary;

    private String tag;

    @Generated(hash = 1705392441)
    public TagEntity(Long id, Long idItem, Long idDiary, String tag) {
        this.id = id;
        this.idItem = idItem;
        this.idDiary = idDiary;
        this.tag = tag;
    }

    @Generated(hash = 2114918181)
    public TagEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdItem() {
        return this.idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Long getIdDiary() {
        return this.idDiary;
    }

    public void setIdDiary(Long idDiary) {
        this.idDiary = idDiary;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
