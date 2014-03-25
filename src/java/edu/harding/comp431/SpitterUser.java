package edu.harding.comp431;

import java.io.Serializable;

/**
 * This class implements a JavaBean for representing a Spitter User.
 * @author fmccown
 */
public class SpitterUser implements Serializable {

    public SpitterUser() {
        
    }
    
    public SpitterUser(String username, String password, String about) {
        this.username = username;
        this.password = password;
        this.about = about;
    }
    
    private String username;

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    private String password;

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    private String about;

    /**
     * Get the value of about
     *
     * @return the value of about
     */
    public String getAbout() {
        return about;
    }

    /**
     * Set the value of about
     *
     * @param about new value of about
     */
    public void setAbout(String about) {
        this.about = about;
    }
    
    private String profilePic = "default.jpg";
    
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    
    public String getProfilePic() {
        return profilePic;
    }
    
    @Override
    public String toString() {
        return "username: " + username + "  about: " + about;
    }   
    
    private boolean following;

    /**
     * Get the value of following
     *
     * @return the value of following
     */
    public boolean isFollowing() {
        return following;
    }

    /**
     * Set the value of following
     *
     * @param following new value of following
     */
    public void setFollowing(boolean following) {
        this.following = following;
    }
    
    private Boolean follower;

    /**
     * Get the value of follower
     *
     * @return the value of follower
     */
    public boolean isFollower() {
        return follower;
    }

    /**
     * Set the value of following
     *
     * @param follower new value of following
     */
    public void setFollower(boolean follower) {
        this.follower = follower;
    }

}
