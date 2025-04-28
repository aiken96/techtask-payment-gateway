package com.example.payment.gateway.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService {
  private final Map<String, Object> cache = new ConcurrentHashMap<>();

  public boolean isProcessed(String key) {
    return cache.containsKey(key);
  }
  public void register(String key, Object result) {
    cache.put(key, result);
  }
  public Object getResult(String key) {
    return cache.get(key);
  }
}