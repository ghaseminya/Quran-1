package com.bia.quran.dao;

import com.bia.quran.entity.Quran;
import com.bia.quran.entity.QuranConstants;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Intesar Mohammed <mdshannan@gmail.com>
 */
public class QuranRepositoryImpl implements QuranRespositorySearch {

    protected static final Logger logger = LoggerFactory.getLogger(QuranRepositoryImpl.class);
    @PersistenceUnit(unitName = "quran-pu")
    private EntityManagerFactory emf;

    @Override
    public List<Quran> search(String term) {
        
        EntityManager em = emf.createEntityManager();

        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);


        final QueryBuilder b = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Quran.class).get();

        org.apache.lucene.search.Query luceneQuery =
                b.keyword()
                .onField(QuranConstants.AYAH_TEXT).boostedTo(3)
                .matching(term)
                .createQuery();

        FullTextQuery fullTextQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery);

//        org.apache.lucene.search.Sort sort = new Sort(
//                new SortField(QuranConstants.AYAH_ID, SortField.INT));
        
//        fullTextQuery.setSort(sort);

        List<Quran> result = fullTextQuery.getResultList();

        return result;
    }
}
