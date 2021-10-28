package fit.iterway.processor.repository;

import fit.iterway.processor.model.DeviceReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeviceReportRepository extends MongoRepository<DeviceReport, Long> {


    String SELECT = " dr.id, dr.device.id, MAX(dr.date), dr.longitude, dr.latitude, dr.deviceCode, dr.road, dr.city , " +
            "dr.state , dr.country , dr.countryCode , dr.houseNumber, dr.latLong , DATE_FORMAT(dr.date,  '%Y-%m-%d %H') as date_format, " +
            "COUNT(1) as countlatlong, dr.eventType, dr.eventId ";


    @Query("Select d from DeviceReport d where d.device.id = ?1 and d.date >= ?2 and d.latitude is not null and d.longitude is not null " +
            "group by concat(substring(d.latitude, 1, 9), substring(d.longitude, 1, 9)) order by d.date desc")
    List<DeviceReport> findLastDayReports(Long deviceId, Date date);

    @Query("Select d from DeviceReport d where d.device.id = " +
            " (select ds.device.id from DeviceShared ds where ds.id = ?1) " +
            " and d.date >= ?2 and exists " +
            " (select h.id from DeviceSharedActiveHistory h where h.deviceShared.id = ?1 and (d.date between h.dateFrom and h.dateTo) or" +
            " (h.dateTo is null and d.date > h.dateFrom) ) " +
            " group by concat(substring(d.latitude, 1, 9), substring(d.longitude, 1, 9)) order by d.date desc")
    List<DeviceReport> findLastDayReportsShared(Long sharedId, Date date);


    //    @Query(value = "select dr FROM DeviceReport dr WHERE  dr.device.id = ?1 order by dr.date DESC limit 1")
    //    @Query(value="SELECT * FROM users WHERE other_obj = ?1 LIMIT 1", nativeQuery = true)
    //    DeviceReport findLast(@Param("deviceId") final Long deviceId);
    //    LastnameOrderByIdDesc
    DeviceReport findFirstByDeviceIdOrderByDateDesc(Long deviceId);

}
