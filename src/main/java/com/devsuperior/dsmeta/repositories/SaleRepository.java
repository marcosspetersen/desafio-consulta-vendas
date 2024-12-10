package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

   @Query(value = "SELECT new com.devsuperior.dsmeta.dto.ReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) FROM Sale obj " +
           "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND obj.date BETWEEN :startDate AND :finalDate",
   countQuery = "SELECT COUNT(obj) FROM Sale obj " +
           "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND obj.date BETWEEN :startDate AND :finalDate")
   Page<ReportDTO> searchSalesReport (LocalDate startDate, LocalDate finalDate, String name, Pageable pageable);

   @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(SA.seller.name, SUM(SA.amount)) FROM Sale SA " +
           "WHERE SA.date BETWEEN :startDate AND :finalDate GROUP BY SA.seller.name")
   List<SummaryDTO> seachSalesSummary(LocalDate startDate, LocalDate finalDate);
}
