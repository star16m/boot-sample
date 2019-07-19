package star16m.bootsample.web.controller.sample;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;
import star16m.bootsample.core.error.ResourceNotfoundException;
import star16m.bootsample.core.error.SimpleRequestException;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.controller.annotations.SimpleRestController;
import star16m.bootsample.web.service.action.Action;
import star16m.bootsample.web.service.action.ActionExecutor;
import star16m.bootsample.web.service.action.ActionProvider;
import star16m.bootsample.web.service.action.ActionRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SimpleRestController(path="/api/rest/v1/action")
@Api(tags = "Action")
public class ActionController {
    private final ActionProvider actionProvider;
    private final ActionExecutor actionExecutor;

    public ActionController(ActionProvider actionProvider, ActionExecutor actionExecutor) {
        this.actionProvider = actionProvider;
        this.actionExecutor = actionExecutor;
    }
    @GetMapping
    public List<Action> getAction() {
        return this.actionProvider.getAllAction();
    }

    @PatchMapping("result")
    @ApiOperation("전체 Action 조회")
    public SimpleResponse<List<Map<String, Object>>> getReadResult(@Valid @RequestBody ActionRequest actionRequest, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new SimpleRequestException(errorMessage);
        }
        Action action = this.actionProvider.getAction(actionRequest.getActionName()).orElseThrow(() -> new ResourceNotfoundException(actionRequest.getActionName()));
        log.debug("action [{}], param [{}]", action.getActionName(), actionRequest.getActionParameter());
        return SimpleResponse.of(this.actionExecutor.getList(action, actionRequest.getActionParameter()));
    }
    @PostMapping
    @ApiOperation("Action 실행")
    public SimpleResponse<List<Map<String, Object>>> execute(@Valid @RequestBody ActionRequest actionRequest, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new SimpleRequestException(errorMessage);
        }
        Action action = this.actionProvider.getAction(actionRequest.getActionName()).orElseThrow(() -> new ResourceNotfoundException(actionRequest.getActionName()));
        log.debug("action [{}], param [{}]", action.getActionName(), actionRequest.getActionParameter());
        return SimpleResponse.of(this.actionExecutor.getList(action, actionRequest.getActionParameter()));
    }
}
