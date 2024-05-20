package com.GuideAPP_AKS.mainHeading;

import com.GuideAPP_AKS.SecondSubHeading.CombinedDataSubSub;
import com.GuideAPP_AKS.firstSubHeading.CombinedDataSub;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import lombok.Data;

import java.util.List;
@Data
public class CombinedData {
    private String title;
    private String description;
    private String referenceUrl;
    private String uId;

    private String commonId;
    private String qrCodeUrl;

    private List<ImgData> imgDataList;
    private List<Mp3Data> mp3DataList;
    private List<Mp4Data> mp4DataList;

    private List<CombinedDataSub> combinedDataSubList;
    private List<CombinedDataSubSub> combinedDataSubSubList;

    public CombinedData() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public List<ImgData> getImgDataList() {
        return imgDataList;
    }

    public void setImgDataList(List<ImgData> imgDataList) {
        this.imgDataList = imgDataList;
    }

    public List<Mp3Data> getMp3DataList() {
        return mp3DataList;
    }

    public void setMp3DataList(List<Mp3Data> mp3DataList) {
        this.mp3DataList = mp3DataList;
    }

    public List<Mp4Data> getMp4DataList() {
        return mp4DataList;
    }

    public void setMp4DataList(List<Mp4Data> mp4DataList) {
        this.mp4DataList = mp4DataList;
    }


}
