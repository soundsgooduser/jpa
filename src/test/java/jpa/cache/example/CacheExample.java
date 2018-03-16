package jpa.cache.example;

import jpa.common.Constants;
import net.sf.ehcache.CacheManager;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;

import static jpa.common.CacheUtil.containsCachedObject;
import static jpa.common.CacheUtil.logSecondLevelCacheData;
import static jpa.common.Constants.CACHEABLE_HINT;
import static jpa.common.EntityManagerUtil.getCacheExampleEntityManager;
import static jpa.common.Queries.GET_CACHE_TEST_NAME;

public class CacheExample {

    public static void main(String[] args) {

        EntityManager entityManager = getCacheExampleEntityManager();
        entityManager.getTransaction().begin();

        final CacheTest cacheTest1 = new CacheTest(1, "cache-test-1");
        final CacheTest cacheTest2 = new CacheTest(2, "cache-test-2");
        entityManager.persist(cacheTest1);
        entityManager.persist(cacheTest2);

        entityManager.getTransaction().commit();

        containsCachedObject(entityManager, CacheTest.class, 1);
        logSecondLevelCacheData(entityManager);

        // test second level cache
        for (int i = 0; i < 2; i++) {
            entityManager.find(CacheTest.class, 1);
            containsCachedObject(entityManager, CacheTest.class, 1);
            logSecondLevelCacheData(entityManager);
        }

        // test query cache
        for (int i = 0; i < 2; i++) {
            entityManager.createQuery(GET_CACHE_TEST_NAME)
                    .setHint(CACHEABLE_HINT, Boolean.TRUE)
                    .getResultList();
            logSecondLevelCacheData(entityManager);
        }

        // USE - data is retrieved from the second-level cache ; BYPASS - call to the database is made
        entityManager.setProperty(Constants.RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
        entityManager.find(CacheTest.class, 1);

        // print cache keys
        CacheManager.ALL_CACHE_MANAGERS.get(0)
                .getCache(CacheTest.class.getCanonicalName())
                .getKeys().stream()
                .forEach(System.out::println);
    }
}
