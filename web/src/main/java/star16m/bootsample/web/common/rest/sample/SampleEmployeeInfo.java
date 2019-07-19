package star16m.bootsample.web.common.rest.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleEmployeeInfo implements Serializable {
    private static final long serialVersionUID = -7494992498576407089L;
    private String employee_age;
    private String employee_name;
    private String employee_salary;
    private String id;
    private String profile_image;
}
