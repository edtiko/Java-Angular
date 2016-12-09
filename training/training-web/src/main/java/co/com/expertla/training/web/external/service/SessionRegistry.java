/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.websocket.Session;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edwin G
 */
@Service
public class SessionRegistry {
   private final Set<Session> sessions = new HashSet<>();
   @Lock(LockType.READ)
   public Set<Session> getAll() {
       return Collections.unmodifiableSet(sessions);
   }
   @Lock(LockType.WRITE)
   public void add(Session session) {
       sessions.add(session);
   }
   @Lock(LockType.WRITE)
   public void remove(Session session) {
       sessions.remove(session);
   }
}