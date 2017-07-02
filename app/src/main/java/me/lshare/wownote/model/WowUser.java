package me.lshare.wownote.model;

/**
 * @author Lshare
 * @date 2017/7/2
 */
public class WowUser {
  private int id;
  private String name;
  private String avatarurl;

  public WowUser(int id, String name, String avatarurl) {
    this.id = id;
    this.name = name;
    this.avatarurl = avatarurl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatarurl() {
    return avatarurl;
  }

  public void setAvatarurl(String avatarurl) {
    this.avatarurl = avatarurl;
  }
}
