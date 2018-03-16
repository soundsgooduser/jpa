package jpa.common;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionImpl;
import org.hibernate.stat.SecondLevelCacheStatistics;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class CacheUtil {

    private CacheUtil() {
    }

    public static void logSecondLevelCacheData(final EntityManager entityManager) {
        final SessionFactoryImplementor sessionFactory = ((SessionImpl) entityManager.getDelegate()).getSessionFactory();
        final List<String> regionNames = Arrays.asList(sessionFactory.getCache().getSecondLevelCacheRegionNames());
        System.out.println("ALL REGIONS:" + regionNames);

        regionNames.stream()
                .forEach(region -> {
                    final SecondLevelCacheStatistics secondLevelCacheStatistics = sessionFactory.getStatistics()
                            .getSecondLevelCacheStatistics(region);

                    System.out.println("region: " + region);
                    System.out.println("ElementCountInMemory: " + secondLevelCacheStatistics.getElementCountInMemory());
                    System.out.println("HitCount: " + secondLevelCacheStatistics.getHitCount());
                    System.out.println("ElementCountInMemory: " + secondLevelCacheStatistics.getElementCountInMemory());
                    System.out.println("ElementCountOnDisk: " + secondLevelCacheStatistics.getElementCountOnDisk());
                    System.out.println("PutCount: " + secondLevelCacheStatistics.getPutCount());
                    System.out.println("Entries size: " + secondLevelCacheStatistics.getEntries().size());


                    final Map entries = secondLevelCacheStatistics.getEntries();
                    System.out.println("all keys: " + entries.keySet());
                    System.out.println("all values: " + entries.values());
                    System.out.println();
                });
    }

    public static void containsCachedObject(final EntityManager entityManager, final Class clazz, final Object id) {
        final Cache cache = entityManager.getEntityManagerFactory().getCache();
        System.out.println("cache contains object: " + cache.contains(clazz, id));
    }
}
