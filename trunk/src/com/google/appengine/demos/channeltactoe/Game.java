// Copyright 2010 Google Inc. All Rights Reserved.

package com.google.appengine.demos.channeltactoe;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.org.json.JSONObject;

import org.mortbay.util.ajax.JSON;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
/**
 * @author moishel@google.com (Your Name Here)
 *
 */
@PersistenceCapable
public class Game {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;
  
  @Persistent
  private String userX;
  
  @Persistent
  private String userY;
  
  @Persistent
  private String board;
  
  @Persistent
  private Boolean moveX;
  
  Game(String userX, String userY, String board, boolean moveX) {
    this.userX = userX;
    this.userY = userY;
    this.board = board;
    this.moveX = moveX;
  }
  
  public Key getKey() {
    return key;
  }
  
  public String getUserX() {
    return userX;
  }
  
  public String getUserY() {
    return userY;
  }
  
  public void setUserY(String userY) {
    this.userY = userY;
  }
  
  public String getBoard() {
    return board;
  }
  
  public void setBoard(String board) {
    this.board = board;
  }
  
  public boolean getMoveX() {
    return moveX;
  }
  
  public void setMoveX(boolean moveX) {
    this.moveX = moveX;
  }
  
  public String getMessageString() {
    Map<String, String> state = new HashMap<String, String>();
    state.put("userX", userX);
    if (userY == null) {
      state.put("userY", "");
    } else {
      state.put("userY", userY);
    }
    state.put("board", board);
    state.put("moveX", moveX.toString());
    JSONObject message = new JSONObject(state);
    return message.toString();
  }
  
  public String getChannelKey(String user) {
    return user + KeyFactory.keyToString(key);
  }
  
  private void sendUpdateToUser(String user) {
    if (user != null) {
      ChannelService channelService = ChannelServiceFactory.getChannelService();
      String channelKey = getChannelKey(user);
      channelService.sendMessage(new ChannelMessage(channelKey, getMessageString()));
    }
  }
  
  public void sendUpdateToClients() {
    sendUpdateToUser(userX);
    sendUpdateToUser(userY);
  }
}
