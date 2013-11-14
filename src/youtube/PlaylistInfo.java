/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;

/**
 *
 * @author Wise-SW
 */
public class PlaylistInfo {
    private String playlisID;
    private String uploader;
    private String title;
    private int video_count;
    private String image_url;

    public PlaylistInfo(String playlisID, String uploader, String title, int video_count, String image_url) {
        this.playlisID = playlisID;
        this.uploader = uploader;
        this.title = title;
        this.video_count = video_count;
        this.image_url = image_url;
    }

    public String getPlaylisID() {
        return playlisID;
    }

    public void setPlaylisID(String playlisID) {
        this.playlisID = playlisID;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    
}
