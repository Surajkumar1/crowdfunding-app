package com.example.demo.repository.campaign;

import com.example.demo.entities.campaign.Campaign;
import com.example.demo.entities.campaign.Donation;
import com.example.demo.enums.DonationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    // Additional query methods if needed

    Page<Donation> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT SUM(p.amount) FROM Donation p WHERE p.status = :status AND p.campaign = :id")
    Double getDonatedMoney(DonationStatus status, Campaign id);

 //   @Query("SELECT * FROM Donation p WHERE p.userId = :userId")
   // List<Donation> getDonationByUserId(Long userId);

}
