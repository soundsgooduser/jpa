package jpa.common;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static jpa.common.Constants.PERSISTENCE_UNIT_CACHE_EXAMPLE;

public final class EntityManagerUtil {

    private EntityManagerUtil() {
    }

    public static EntityManager getCacheExampleEntityManager() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_CACHE_EXAMPLE).createEntityManager();
    }
}
