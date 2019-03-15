package com.dfs.entity;

import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author taoxy 2019/02/02
 */
@ToString
public class SenseAgroSpecialist {
    private Integer id;

    private String name;

    private String avater;

    private String coverImage;

    private String mark;

    private Integer visit;

    private String cropName;

    private Integer workAge;

    private String area;

    private BigDecimal originPrice;

    private BigDecimal adjustPrice;

    private Integer addTime;

    private String uname;

    private String passwd;

    private String description;

    private String skill;

    private Integer timeShow;

    private byte isOnline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(BigDecimal adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDescription() {
        if (description == null) {
            return "";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(Integer timeShow) {
        this.timeShow = timeShow;
    }

    public byte getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(byte isOnline) {
        this.isOnline = isOnline;
    }
}