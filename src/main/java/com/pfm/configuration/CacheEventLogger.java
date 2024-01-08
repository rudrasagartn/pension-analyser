package com.pfm.configuration;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

	private static final Logger log = LoggerFactory.getLogger(CacheEventLogger.class);

	@Override
	public void onEvent(CacheEvent<?, ?> cacheEvent) {
			log.info("Cache event type >> {}, cache key >> {}, cache old Value >> {},  cache new value >> {} ",
					cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}
}