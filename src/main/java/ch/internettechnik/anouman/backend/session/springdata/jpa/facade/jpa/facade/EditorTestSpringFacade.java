package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.EditorTest;
import ch.internettechnik.anouman.backend.entity.QEditorTest;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.EditorTestSpringRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EditorTestSpringFacade {
    @PersistenceContext
    public EntityManager em;
    @Inject
    EditorTestSpringRepository repo;

    private JPAQuery<EditorTest> root() {
        return new JPAQuery<EditorTest>(em).from(QEditorTest.editorTest);
    }

    /**
     * A simple QueryDSL usage example.
     *
     * @param filter an optional filter parameter
     * @param category an optional category parameter
     * @return
     */
    /*
    public List<EditorTest> findWithQueryDSL(String filter, Category category) {
        JPAQuery<EditorTest> q = root();
        if(filter != null) {
            q = q.where(QBook.book.name.startsWithIgnoreCase(filter));
        }
        if(category != null) {
            q = q.where(QBook.book.category.eq(category));
        }
        return q.fetch();
    }
    */

    /**
     * A QueryDSL example of providing API for EditorTestDeltaspikeFacade users where they can just
     * pass the built predicate for "backend". This could be e.g. built
     * by a UI code implementing a complex search features.
     *
     * @param p the predicate for the query
     * @return
     */
    public List<EditorTest> findWithQueryDSL(Predicate p) {
        return root().where(p).fetch();
    }

    public List<EditorTest> findEditorTest(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return repo.getOneErsterLikeIgnoreCase(filter + "%");
        }
        return repo.findAll();
    }

    public List<EditorTest> findAll() {
        return repo.findAll();
    }

    /*
    public List<Book> findBooksByCategory(Category value) {
        return br.findByCategory(value);
    }

    public List<Category> findCategories(String filter) {
        if (filter == null || filter.isEmpty()) {
            return cr.findAll();
        }
        Category category = new Category();
        category.setName(filter);
        return cr.findByLike(category, Category_.name);
    }


    */


    public EditorTest save(EditorTest value) {
        return repo.save(value);
    }

    public void delete(EditorTest e) {
        repo.delete(e);
    }
}
