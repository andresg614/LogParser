package com.ef.config;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ef.model.Request;
import com.ef.model.Requester;
import com.ef.model.dto.LogRequesterDTO;
import com.ef.service.RequesterService;
import com.ef.utils.GeneralUtils;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DBItemWriter<T> implements ItemWriter<T> {

	@Override
	@Transactional(rollbackFor=Exception.class,  propagation=Propagation.REQUIRED)
	public void write(List<? extends T> items) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
		
		RequesterService requesterService = context.getBean(RequesterService.class);
		
		HashMap<String, Requester> requesterMap = new HashMap<String, Requester>();
		
		DateTimeFormatter dateFomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

		for (T item : items) {
			LogRequesterDTO requesterDTOTemp = (LogRequesterDTO) item;
			Requester requesterTemp = new Requester();
			Request requestTemp = new Request();			
			LocalDateTime formattedDate = LocalDateTime.parse(requesterDTOTemp.getDate(), dateFomatter);
			
			requestTemp.setRequestDate(Timestamp.valueOf(GeneralUtils.changeDateZoneForDB(formattedDate, "UTC")));
			requestTemp.setRequestDescription(requesterDTOTemp.getRequest());
			requestTemp.setRequestStatus(requesterDTOTemp.getStatus());
			requestTemp.setRequestUserAgent(requesterDTOTemp.getUserAgent());			
			
			if (requesterMap.containsKey(requesterDTOTemp.getIp())) {
				requesterTemp = requesterMap.get(requesterDTOTemp.getIp());
			}else {
				requesterTemp.setIpAddress(requesterDTOTemp.getIp());
				requesterTemp.setStatus("OK");		
			}
			
			requesterTemp.addToRequests(requestTemp);
			requesterMap.put(requesterTemp.getIpAddress(), requesterTemp);
		}
		
		for (Map.Entry<String, Requester> entry : requesterMap.entrySet()) {
			Requester requesterTemp = (Requester) entry.getValue();
			List<Requester> listRequesterDB = requesterService.findByIpAddress(requesterTemp.getIpAddress());

			if (listRequesterDB != null && listRequesterDB.size() > 0) {
				requesterTemp.setId(listRequesterDB.get(0).getId());

				requesterService.merge(entry.getValue());
			}else {
				requesterService.add(entry.getValue());
			}
		}

		context.close();
	}
}