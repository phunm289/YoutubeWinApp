/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;

/**
 *
 * @author Wise-SW
 */
public class VideoInfo {
    private String idResult;
    private String title;
    private String time;
    private String uploader;
    private String imgUrl;
    private String viewCount;

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUploadUser() {
        return uploader;
    }

    public void setUploadUser(String uploadUser) {
        this.uploader = uploadUser;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public VideoInfo() {
    }

    
    
    public VideoInfo(String idResult, String title, String time, String uploadUser, String imgUrl, String viewCount) {
        this.idResult = idResult;
        this.title = title;
        this.time = time;
        this.uploader = uploadUser;
        this.imgUrl = imgUrl;
        this.viewCount = viewCount;
        
    }

    public String getIdResult() {
        return idResult;
    }

    public void setIdResult(String idResult) {
        this.idResult = idResult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
