package fit.iterway.processor.repository;

import fit.iterway.processor.model.DeviceReportView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceReportViewRepository extends MongoRepository<DeviceReportView, Long> {


}
