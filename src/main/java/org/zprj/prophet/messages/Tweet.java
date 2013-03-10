package org.zprj.prophet.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: zorana
 * Date: Mar 9, 2013
 * Time: 12:20:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tweet extends JSONObject{

    private Long id;
    private Long userId;
    private String text;
    private String username;
    private String created;
    private Location location;
    private Integer friendsCount;
    private JSONObject obj;

    public Tweet(String str) {
        try {
            obj = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public Tweet getInstance(String str) {
//        try {
//            obj = new JSONObject(str);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return obj;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        try {
            text = obj.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }
}
