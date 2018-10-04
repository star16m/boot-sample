package star16m.bootsample.resource.service;

import star16m.bootsample.resource.entity.AbstractEntity;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.utils.SimpleUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractService<T extends AbstractEntity, ID> {
    private AbstractRepository<T, ID> simpleRepository;

    public AbstractService(AbstractRepository simpleRepository) {
        this.simpleRepository = simpleRepository;
    }
    public List<T> findAll() {
        return simpleRepository.findAll();
    }

    public Optional<T> findOne(ID id) {
        SimpleUtil.mustNotNull(id);
        return this.simpleRepository.findById(id);
    }

    public T save(T object) {
        // object must not null
        SimpleUtil.mustNotNull(object);
        // object#id must not null
        SimpleUtil.mustNotNull(object.getId());
        return this.simpleRepository.save(object);
    }

    public T update(ID id, Map<String, Object> map) {
        // object#id must not null
        SimpleUtil.mustNotNull(id);
        return simpleRepository.findById(id)
                .map(o -> {
                    patchedObject(o, map);
                    return simpleRepository.save(o);
                })
                .orElse(null);
    }

    protected abstract void patchedObject(final T o, final Map<String, Object> map);

    public void delete(ID id) throws SimpleException {
        SimpleUtil.mustNotNull(id);
        this.simpleRepository.deleteById(id);
    }
}
