package site.hclub.hyndai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String employeeNo;
    private String employeeDept;
    private String employeePosition;
    private String employeeName;
}
