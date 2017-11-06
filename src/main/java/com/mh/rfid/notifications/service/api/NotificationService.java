package com.mh.rfid.notifications.service.api;

import com.mh.rfid.dto.ReportDto;

public interface NotificationService {

	void notify(ReportDto report);
}