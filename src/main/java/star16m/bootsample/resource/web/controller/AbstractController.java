package star16m.bootsample.resource.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import star16m.bootsample.resource.entity.AbstractEntity;
import star16m.bootsample.resource.service.AbstractService;
import star16m.bootsample.resource.service.error.EntityNotfoundException;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.utils.SimpleUtil;
import star16m.bootsample.resource.web.action.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class AbstractController<T extends AbstractEntity, I extends Integer> extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(AbstractController.class);
    private String resouceName;
    private AbstractService<T, I> service;
    private boolean exceptionThrowIfNotPresent = false;
    public AbstractController(String resouceName, AbstractService<T, I> service) {
        this(resouceName, service, false);
    }
    public AbstractController(String resouceName, AbstractService<T, I> service, boolean exceptionThrowIfNotPresent) {
        this.resouceName = resouceName;
        this.service = service;
        this.exceptionThrowIfNotPresent = exceptionThrowIfNotPresent;
    }
    @GetMapping
    public final ResponseEntity<List<T>> findAll() {
        return SimpleUtil.response(this.service.findAll());
    }

    @PostMapping
    public final ResponseEntity<T> create(final T sourceObject) {
        logger.debug("try create {} with [{}]", this.resouceName, sourceObject);
        T createdObject = this.service.save(sourceObject);
        return SimpleUtil.response(createdObject);
    }

    @GetMapping(value="/{id}")
    public final ResponseEntity<T> get(@PathVariable final I id) {
        logger.debug("try get {} with id [{}]", this.resouceName, id);
        T object = getObject(id);
        return SimpleUtil.response(object, SimpleUtil.isNotNull(object) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value="/{id}")
    public final ResponseEntity<T> update(@PathVariable final I id, final T targetObject) {
        // validation check null and equals id
        SimpleUtil.mustNotNull(id);
        SimpleUtil.mustNotNull(targetObject);
        SimpleUtil.mustEqual(id, targetObject.getId());
        logger.debug("try update {} with id [{}] to [{}]", this.resouceName, id, targetObject);

        // find
        T sourceObject = getObject(id);
        if (SimpleUtil.isNull(sourceObject)) {
            SimpleUtil.response(null);
        }
        BeanUtils.copyProperties(sourceObject, targetObject);
        logger.debug("merged {} [{}]", this.resouceName, sourceObject);

        T updated = this.service.save(sourceObject);
        logger.debug("updated {} : [{}]", this.resouceName, updated);

        return SimpleUtil.response(updated);
    }

    @DeleteMapping(value="/{id}")
    public final ResponseEntity<Boolean> delete(@PathVariable final I id) {
        this.service.delete(id);
        return SimpleUtil.response(true);
    }

    private final T getObject(final I id) {
        Optional<T> optionalObject = this.service.findOne(id);
        if (exceptionThrowIfNotPresent) {
            return optionalObject.orElseThrow(() -> new EntityNotfoundException("not found object with id [{}].", id));
        } else {
            // return null
            return optionalObject.orElse(null);
        }
    }

    @ExceptionHandler(EntityNotfoundException.class)
    public final org.springframework.http.ResponseEntity<String> handleUserNotFoundException(EntityNotfoundException ex, WebRequest request) {
        return new org.springframework.http.ResponseEntity<>("not found entity", HttpStatus.NOT_FOUND);
    }
}
