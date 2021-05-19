package com.example.demo.dtos;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogCie {
	private String cieSerial;
	private String idRichiesta;
	private Date accessTime;
	private String userAgent;
	private String ipAddress;
	private String mobileIndirizzoIp;
	private String errorCode;
	

}
