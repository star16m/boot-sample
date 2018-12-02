package star16m.bootsample.resource.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.net.InetAddress;


@Data
@RequiredArgsConstructor
public class TeamTest {
    @ApiModelProperty(notes = "팀의 key ID", example = "1")
    private Integer id;
    @ApiModelProperty(notes = "팀의 전체 이름", example = "롯데 자이언츠")
    private String fullName;
    @ApiModelProperty(notes = "팀의 이름", example = "롯데")
    private String shortName;
    @ApiModelProperty(notes = "test IP Address", example = "127.0.0.1", dataType = "java.lang.String")
    private InetAddress ipAddress;
}
