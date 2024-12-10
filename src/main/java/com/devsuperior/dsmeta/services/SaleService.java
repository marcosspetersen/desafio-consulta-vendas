package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
    @Autowired
    private SaleRepository saleRepository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SummaryDTO> seachSalesByDate(String minDate, String maxDate) {
		LocalDate finalDate = convertFinalDate(maxDate);
		LocalDate startDate = convertInitialDate(minDate, finalDate);

		List<SummaryDTO> result = saleRepository.seachSalesByDate(startDate, finalDate);
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
