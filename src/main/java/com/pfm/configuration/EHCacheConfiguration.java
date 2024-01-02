package com.pfm.configuration;

import java.time.Duration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pfm.dto.PensionFundManagerDTO;
import com.pfm.dto.PensionFundManagerSchemesDTO;

@Configuration
@EnableCaching
public class EHCacheConfiguration {
	@Bean
	CacheManager getCacheManager() {
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cacheManager = provider.getCacheManager();

		CacheConfiguration<String, PensionFundManagerDTO> pfmConfiguration = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, PensionFundManagerDTO.class,
						ResourcePoolsBuilder.heap(100).offheap(10, MemoryUnit.MB))
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1))).add(initCacheManager())
				.build();

		javax.cache.configuration.Configuration<java.lang.String, PensionFundManagerDTO> pfmCacheConfiguration = Eh107Configuration
				.fromEhcacheCacheConfiguration(pfmConfiguration);

		cacheManager.createCache("pfm", pfmCacheConfiguration);

		CacheConfiguration<String, PensionFundManagerSchemesDTO> pfmsConfiguration = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, PensionFundManagerSchemesDTO.class,
						ResourcePoolsBuilder.heap(100).offheap(10, MemoryUnit.MB))
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1))).add(initCacheManager())
				.build();

		javax.cache.configuration.Configuration<java.lang.String, PensionFundManagerSchemesDTO> pfmsCacheConfiguration = Eh107Configuration
				.fromEhcacheCacheConfiguration(pfmsConfiguration);

		cacheManager.createCache("pfms", pfmsCacheConfiguration);

		return cacheManager;
	}

	private CacheEventListenerConfigurationBuilder initCacheManager() {
		return CacheEventListenerConfigurationBuilder.newEventListenerConfiguration(new CacheEventLogger(),
				EventType.CREATED, EventType.UPDATED, EventType.EXPIRED, EventType.REMOVED, EventType.EVICTED).ordered()
				.synchronous();

	}

}