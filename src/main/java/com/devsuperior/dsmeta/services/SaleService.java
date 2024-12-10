package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportDTO> searchSalesReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate finalDate = convertFinalDate(maxDate);
		LocalDate startDate = convertInitialDate(minDate, finalDate);

		Page<ReportDTO> result = repository.searchSalesReport(startDate, finalDate, name, pageable);
		return result;
	}

	public List<SummaryDTO> seachSalesSummary(String minDate, String maxDate) {
		LocalDate finalDate = convertFinalDate(maxDate);
		LocalDate startDate = convertInitialDate(minDate, finalDate);

		List<SummaryDTO> result = repository.seachSalesSummary(startDate, finalDate);
		return result;
	}

	public LocalDate convertInitialDate(String minDate, LocalDate maxDate) {
		if(minDate == null || minDate.isEmpty()) {
			LocalDate result = maxDate.minusYears(1L);
			return result;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(minDate,formatter);
	}

	public LocalDate convertFinalDate(String date) {
		if(date == null || date.isEmpty()) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			return today;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date,formatter);
	}
}
