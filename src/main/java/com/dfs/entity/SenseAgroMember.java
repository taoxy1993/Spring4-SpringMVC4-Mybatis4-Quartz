package com.dfs.entity;

import com.dfs.utils.EmojiUtil;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author taoxy 2018/12/15
 */
@ToString
public class SenseAgroMember implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer memberId;

    private String memberName;

    private String memberPassword;

    private String memberAvatar;

    private String memberModifyAvatar;

    private Integer memberSex = 0;

    private String memberNickname;

    private String memberCountry;

    private String memberProvince;

    private String memberCity;

    private String memberArea;

    private String memberMobile;

    private String memberOpenid;

    private String memberUnionid;

    private Integer registerTime;

    private Integer updateTime;

    private Integer isBanned;

    private String registerFrom;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword == null ? null : memberPassword.trim();
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar == null ? null : memberAvatar.trim();
    }

    public String getMemberModifyAvatar() {
        return memberModifyAvatar;
    }

    public void setMemberModifyAvatar(String memberModifyAvatar) {
        this.memberModifyAvatar = memberModifyAvatar == null ? null : memberModifyAvatar.trim();
    }

    public Integer getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(Integer memberSex) {
        this.memberSex = memberSex;
    }

    public String getMemberNickname() {
        return EmojiUtil.emojiRecovery(memberNickname);
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname == null ? null : EmojiUtil.emojiConvert(memberNickname.trim());
    }

    public String getMemberCountry() {
        return memberCountry;
    }

    public void setMemberCountry(String memberCountry) {
        this.memberCountry = memberCountry == null ? null : memberCountry.trim();
    }

    public String getMemberProvince() {
        return memberProvince;
    }

    public void setMemberProvince(String memberProvince) {
        this.memberProvince = memberProvince == null ? null : memberProvince.trim();
    }

    public String getMemberCity() {
        return memberCity;
    }

    public void setMemberCity(String memberCity) {
        this.memberCity = memberCity == null ? null : memberCity.trim();
    }

    public String getMemberArea() {
        return memberArea;
    }

    public void setMemberArea(String memberArea) {
        this.memberArea = memberArea == null ? null : memberArea.trim();
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile == null ? null : memberMobile.trim();
    }

    public String getMemberOpenid() {
        return memberOpenid;
    }

    public void setMemberOpenid(String memberOpenid) {
        this.memberOpenid = memberOpenid == null ? null : memberOpenid.trim();
    }

    public String getMemberUnionid() {
        return memberUnionid;
    }

    public void setMemberUnionid(String memberUnionid) {
        this.memberUnionid = memberUnionid == null ? null : memberUnionid.trim();
    }

    public Integer getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Integer registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Integer isBanned) {
        this.isBanned = isBanned;
    }

    public String getRegisterFrom() {
        return registerFrom;
    }

    public void setRegisterFrom(String registerFrom) {
        this.registerFrom = registerFrom == null ? null : registerFrom.trim();
    }
}