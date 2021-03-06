/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.frc.games.guessnumber.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.frc.common.listener.InitServer;
import com.frc.games.framework.service.IRoomService;
import com.frc.games.guessnumber.service.impl.GuessNumberRoomService;

import antlr.debug.GuessingEvent;


@ServerEndpoint(value = "/websocket/room")
public class ChatAnnotation {
    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Set<ChatAnnotation> connections =
            new CopyOnWriteArraySet<ChatAnnotation>();

    private final String nickname;
    private Session session;
    
    @Resource(name="GuessNumberRoomService")
	protected IRoomService roomService;

    public ChatAnnotation() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
        
    }


    @OnOpen
    public void start(Session session) {
        this.session = session;
        
        if (roomService == null) {
        	roomService = (IRoomService) InitServer.getApplicationContext().getBean("GuessNumberRoomService");
//        	String[] names = InitServer.getApplicationContext().getBeanDefinitionNames();
//        	System.out.println(names.length);
        }
        
        System.out.println("Someone come in : " + Thread.currentThread().getId());
        try {
        	Thread.sleep(3*1000);
        } catch (InterruptedException e) {}
        
        String queyrString = session.getQueryString();
        Map<String, String> query = parseQuerySring(queyrString);
        System.out.println(query);
        
        String tokenId = query.get("tokenId");
        String roomId = query.get("roomId");
        boolean verifyResult = roomService.inRoom(tokenId, roomId);
        if (!verifyResult) {
        	try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return;
        }
        
        connections.add(this);
        session.getRequestParameterMap().get("");
        
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(message);
    }


    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("* %s %s",
                nickname, "has disconnected.");
        broadcast(message);
    }


    @OnMessage
    public void incoming(String message) {
    	System.out.println("Someone say something : " + Thread.currentThread().getId());
        // Never trust the client
//        String filteredMessage = String.format("%s: %s", nickname, HTMLFilter.filter(message.toString()));
    	String filteredMessage = String.format("%s: %s", nickname, message);
        broadcast(filteredMessage);
    }




    @OnError
    public void onError(Throwable t) throws Throwable {
   //     log.error("Chat Error: " + t.toString(), t);
    }


    private static void broadcast(String msg) {
        for (ChatAnnotation client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
       //         log.debug("Chat Error: Failed to send message to client", e);
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s",
                        client.nickname, "has been disconnected.");
                broadcast(message);
            }
        }
    }
    
    private Map<String, String> parseQuerySring(String queryString) {
    	Map<String, String> result = new HashMap<>();
    	String querys[] = queryString.split("&", -1);
    	for (String query : querys) {
    		String pairs[] = query.split("=", -1);
    		if (pairs.length == 2) {
    			result.put(pairs[0], pairs[1]);
    		}
    	}
    	return result;
    }
}
