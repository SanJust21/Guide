package com.GuideAPP_AKS.firstSubHeading;

import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;

import java.util.List;

public class CombinedDataSub {
    private String title;
    private String description;
    private String referenceUrl;
    private String uId;
    private String mUid;

    private List<ImgSubFirst> imgDataList;

    private List<Mp3Data1> mp3DataList;
    private List<Mp4Data1> mp4DataList;

    public CombinedDataSub() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }

    public List<ImgSubFirst> getImgDataList() {
        return imgDataList;
    }

    public void setImgDataList(List<ImgSubFirst> imgDataList) {
        this.imgDataList = imgDataList;
    }

    public List<Mp3Data1> getMp3DataList() {
        return mp3DataList;
    }

    public void setMp3DataList(List<Mp3Data1> mp3DataList) {
        this.mp3DataList = mp3DataList;
    }

    public List<Mp4Data1> getMp4DataList() {
        return mp4DataList;
    }

    public void setMp4DataList(List<Mp4Data1> mp4DataList) {
        this.mp4DataList = mp4DataList;
    }
}
