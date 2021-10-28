package fit.iterway.processor.repository;

import fit.iterway.processor.model.DeviceSpeedResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeviceSpeedResumeRepository extends MongoRepository<DeviceSpeedResume, Long> {


    String queryFindAll = "select DISTINCT sr from DeviceSpeedResume sr inner join fetch sr.device d  ";
    String queryFindAllPage = "select DISTINCT sr from DeviceSpeedResume sr join sr.device d ";
    String queryPreviousToDateAndDevice = queryFindAllPage + "where sr.dateFrom < ?1 and d.id = ?2 order by sr.dateFrom desc";
    String queryListLateToDateAndDevice = queryFindAllPage + "where sr.dateFrom > ?1 and d.id = ?2 order by sr.dateFrom asc";
    String queryListToDateAndDevice = queryFindAllPage + "where sr.dateFrom = ?1 and d.id = ?2 order by sr.dateFrom asc";
    String queryFindAllByDeviceId = queryFindAll + "where sr.lastUpdate >= ?1 and d.id = ?2 order by sr.dateFrom asc";
    String queryFindAllByDateRange = queryFindAll + "where (sr.dateFrom between ?2 and ?3 or sr.dateTo between ?2 and ?3) and d.id = ?1 order by sr.dateFrom asc, sr.dateTo asc";
    String queryFindPage = queryFindAllPage + "where d.id = ?1 and sr.reportCount > 0 and sr.dateFrom < sr.dateTo order by sr.dateFrom desc ";

    @Query(value = queryPreviousToDateAndDevice)
    Page<DeviceSpeedResume> getPreviousToDateAndDevice(Date date, Long deviceId, Pageable pageable);

    @Query(queryListLateToDateAndDevice)
    Page<DeviceSpeedResume> getListLateToDateAndDevice(Date date, Long deviceId, Pageable pageable);

    @Query(queryListToDateAndDevice)
    Page<DeviceSpeedResume> getListToDateAndDevice(Date date, Long deviceId, Pageable pageable);

    @Query(queryFindAllByDeviceId)
    List<DeviceSpeedResume> findAllByDeviceId(Date date, Long deviceId);

    @Query(queryFindAllByDateRange)
    List<DeviceSpeedResume> findAllByDeviceAndDateRange(Long deviceId, Date start, Date end);

    DeviceSpeedResume findTopByDeviceId(Long deviceId);

    @Query(queryFindPage)
    Page<DeviceSpeedResume> findPage(Long deviceId, Pageable pageable);

}
