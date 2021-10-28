package fit.iterway.processor.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ServiceTypeView  {

    @Id
    private Long id;

    private String name;
    private String code;

    private Long serviceTypeId;
    private Long logisticsTypeId;
    private String logisticsTypeName;
    private Long commodityTypeId;
    private String commodityTypeName;


}
