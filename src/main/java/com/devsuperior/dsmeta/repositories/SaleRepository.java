package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

   //@Query(value = "SELECT SE.name, SUM(sa.amount) FROM Sale sa INNER JOIN sa.seller SE WHERE UPPER(SE.name) LIKE UPPER(CONCAT('%', :name, '%')) AND sa.date BETWEEN :startDate AND :finalDate GROUP BY SE.name")
   @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(SA.seller.name, SUM(SA.amount)) FROM Sale SA " +
           "WHERE SA.date BETWEEN :startDate AND :finalDate GROUP BY SA.seller.name")
   List<SummaryDTO> seachSalesByDate(LocalDate startDate, LocalDate finalDate);
}
