package star16m.bootsample.web.service;

import star16m.bootsample.core.error.SimpleException;
import star16m.bootsample.web.resource.BaseResource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T extends BaseResource, I> {

    List<T> findAll();

    Optional<T> findOne(I id);

    T save(T object);

    T update(I id, Map<String, Object> map);

    void delete(I id) throws SimpleException;

    void patchedObject(final T o, final Map<String, Object> map);
}
