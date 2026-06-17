package com.guide.me.guider.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import com.guide.me.guider.entities.ReservationDate;

@NoArgsConstructor @AllArgsConstructor @Data
public class ReservationRequest {

	@NotNull
	private Long guideId;
	@NotEmpty
    private List<LocalDate> dates;

}
