// Copyright 2010 Google Inc. All Rights Reserved.

package com.google.appengine.demos.channeltactoe;

import com.google.appengine.api.datastore.Key;
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
  private User userX;
  
  @Persistent
  private User userY;
  
  @Persistent
  private String board;
  
  @Persistent
  private Boolean moveX;
  
  Game(User userX, User userY, String board, boolean moveX) {
    this.userX = userX;
    this.userY = userY;
    this.board = board;
    this.moveX = moveX;
  }
  
  public Key getKey() {
    return key;
  }
  
  public User getUserX() {
    return userX;
  }
  
  public User getUserY() {
    return userY;
  }
  
  public String getBoard() {
    return board;
  }
  
  public boolean getMoveX() {
    return moveX;
  }
  
  public String getMessageString() {
    Map<String, String> state = new HashMap<String, String>();
    state.put("userX", userX.getUserId());
    if (userY == null) {
      state.put("userY", "");
    } else {
      state.put("userY", userY.getUserId());
    }
    state.put("board", board);
    state.put("moveX", moveX.toString());
    JSONObject message = new JSONObject(state);
    return message.toString();
  }
}
