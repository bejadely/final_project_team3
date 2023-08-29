package com.trip.finalProject.common.mapper;

import java.util.List;
import java.util.Map;

public interface CommonMapper {
	public List<Map<String, String>> selectCode(String mainCode);
}
