package fit.iterway.processor.repository;

import fit.iterway.processor.model.DeviceMoveHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DeviceMoveHistoryRepository extends MongoRepository<DeviceMoveHistory, Long> {

    String SELECT = " mh.deviceId, MIN(mh.dateIni), MAX(mh.reportDate), SUM(mh.reportTime), SUM(mh.moveTime), SUM(mh.stopTime), SUM(mh.cantReports), MAX(mh.speedMax),AVG(mh.speedAvg), SUM(mh.distanceLatLong), SUM(mh.distanceOdometer) ";
    String queryFindAll = "select new fit.truckbook.web.model.device.DeviceMoveHistory(" + SELECT + ") FROM DeviceMoveHistory mh ";
    String queryFindAllByDeviceId = queryFindAll + "where mh.dateIni >= ?1 and mh.deviceId = ?2 group by 1";


    String queryFindAllBySharedId = queryFindAll + "where mh.dateIni >= ?1 and mh.deviceId = " +
            " (select ds.device.id from DeviceShared ds where ds.id = ?2) " +
            " and exists " +
            " (select h.id from DeviceSharedActiveHistory h where h.deviceShared.id = ?2 and (mh.dateIni between h.dateFrom and h.dateTo) or" +
            " (h.dateTo is null and mh.dateIni > h.dateFrom) ) " +
            " group by 1 ";


//    @Query(value = "select dmh FROM DeviceMoveHistory dmh WHERE  dmh.deviceId = ?1 order by dmh.repodrDate DESC limit 1")
//    DeviceMoveHistory findLastMoveHistory(@Param("id") final Long id);

    DeviceMoveHistory findFirstByDeviceIdOrderByReportDateDesc(Long deviceId);

    @Query(value = queryFindAllByDeviceId)
    DeviceMoveHistory findAllByDeviceId(Date date, Long deviceId);

    String queryFindAllByDeviceIdAndDates = queryFindAll + "where mh.dateIni between ?1 and ?2 and mh.deviceId = ?3 group by 1";

    @Query(value = queryFindAllByDeviceIdAndDates)
    DeviceMoveHistory findAllByDeviceIdAndDateRange(Date dateStart, Date dateEnd, Long deviceId);


    String queryFindAllByVehicleIdAndDates = queryFindAll + "where mh.dateIni between ?1 and ?2 and mh.vehicleId = ?3 group by 1";

    @Query(value = queryFindAllByVehicleIdAndDates)
    DeviceMoveHistory findAllByVehicleIdAndDateRange(Date dateStart, Date dateEnd, Long vehicleId);

    String queryFindAllByPersonIdAndDates = queryFindAll + "where mh.dateIni between ?1 and ?2 and mh.personId = ?3 group by 1";

    @Query(value = queryFindAllByPersonIdAndDates)
    DeviceMoveHistory findAllByPersonIdAndDateRange(Date dateStart, Date dateEnd, Long personId);


    @Query(value = queryFindAllBySharedId)
    DeviceMoveHistory findAllBySharedId(Date date, Long sharedId);
}
