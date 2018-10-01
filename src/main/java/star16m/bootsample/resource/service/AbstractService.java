package star16m.bootsample.resource.service;

import star16m.bootsample.resource.entity.AbstractEntity;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.utils.SimpleUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractService<T extends AbstractEntity, I extends Integer> {
    private AbstractRepository<T, I> simpleRepository;

    public AbstractService(AbstractRepository simpleRepository) {
        this.simpleRepository = simpleRepository;
    }
    public List<T> findAll() {
        return simpleRepository.findAll();
    }

    public Optional<T> findOne(I id) {
        SimpleUtil.mustNotNull(id);
        return this.simpleRepository.findById(id);
    }

    public T save(T object) {
        Objects.nonNull(object);
        return this.simpleRepository.save(object);
    }

    public void delete(T object) throws SimpleException {
        SimpleUtil.mustNotNull(object);
        I id = (I) object.getId();
        Optional<T> deleteObject = findOne(id);
        deleteObject.orElseThrow(() -> new SimpleException("not found delete id [ + " + id + "]"));
        this.simpleRepository.delete(object);
        deleteObject = findOne(id);
        SimpleUtil.mustNull(deleteObject);
    }

    public void delete(I id) throws SimpleException {
        Optional<T> object = findOne(id);
        delete(object.get());
    }
}
