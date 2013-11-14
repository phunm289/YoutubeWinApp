/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;

/**
 *
 * @author Wise-SW
 */
public class ChannelInfo {
    private String imgThumbnail;
    private String title;
    private String timeActive;
    private String videos;
    private String subcriber;
    private String uri;
    private String channelID;

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public ChannelInfo() {
    }

    public ChannelInfo(String id,String imgThumbnail, String title, String timeActive, String videos, String subcriber, String uri) {
        this.channelID = id;
        this.imgThumbnail = imgThumbnail;
        this.title = title;
        this.timeActive = timeActive;
        this.videos = videos;
        this.subcriber = subcriber;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImgThumbnail() {
        return imgThumbnail;
    }

    public void setImgThumbnail(String imgThumbnail) {
        this.imgThumbnail = imgThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeActive() {
        return timeActive;
    }

    public void setTimeActive(String timeActive) {
        this.timeActive = timeActive;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getSubcriber() {
        return subcriber;
    }

    public void setSubcriber(String subcriber) {
        this.subcriber = subcriber;
    }
    
    
}
