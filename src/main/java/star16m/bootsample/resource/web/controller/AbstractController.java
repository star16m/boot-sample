package star16m.bootsample.resource.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import star16m.bootsample.resource.entity.AbstractEntity;
import star16m.bootsample.resource.service.AbstractService;
import star16m.bootsample.resource.service.error.EntityNotfoundException;
import star16m.bootsample.resource.utils.SimpleUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractController<T extends AbstractEntity, I extends Integer> extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(AbstractController.class);
    private String entityName;
    private AbstractService<T, I> service;
    public AbstractController(String entityName, AbstractService<T, I> service) {
        this.entityName = entityName;
        this.service = service;
    }
    @GetMapping
    public final ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping(value="/{id}")
    public final ResponseEntity<T> findById(@PathVariable final I id) {
        logger.debug("try get {} with id [{}]", this.entityName, id);
        T object = getObject(id);
        return ResponseEntity.ok(object);
    }

    @PostMapping
    public final ResponseEntity<T> create(@RequestBody final T updateObject) {
        SimpleUtil.mustNotNull(updateObject);
        logger.debug("try create {} with [{}]", this.entityName, updateObject);
        T createdObject = this.service.save(updateObject);
        return ResponseEntity.ok(createdObject);
    }

    @PostMapping("{id}")
    public final ResponseEntity<T> update(@PathVariable final I id, @RequestBody final Map<String, Object> newObjectMap) {
        SimpleUtil.mustNotNull(id);
        SimpleUtil.mustNotNull(newObjectMap);
        SimpleUtil.mustMin(newObjectMap.keySet(), 1);
        logger.debug("try update {} with id [{}] to [{}]", this.entityName, id, newObjectMap);
        return ResponseEntity.ok(this.service.update(id, newObjectMap));
    }

    @DeleteMapping(value="/{id}")
    public final ResponseEntity<Boolean> delete(@PathVariable final I id) {
        this.service.delete(id);
        return ResponseEntity.ok(true);
    }

    private final T getObject(final I id) {
        Optional<T> optionalObject = this.service.findOne(id);
        // return null
        return optionalObject.orElse(null);
    }

    @ExceptionHandler(EntityNotfoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(EntityNotfoundException e, WebRequest request) {
        return new ResponseEntity<>("not found entity " + this.entityName, HttpStatus.NOT_FOUND);
    }
}
