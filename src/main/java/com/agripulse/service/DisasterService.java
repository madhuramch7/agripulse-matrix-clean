package com.agripulse.service;

import com.agripulse.entity.DisasterReliefClaim;
import com.agripulse.entity.LandPlot;
import com.agripulse.repository.DisasterReliefClaimRepository;
import com.agripulse.repository.LandPlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisasterService {

    private final LandPlotRepository landPlotRepository;
    private final DisasterReliefClaimRepository claimRepository;

    /**
     * Triggers a simulated disaster event for a land plot and calculates payout amount.
     */
    public DisasterReliefClaim triggerDisasterEvent(Long plotId, String disasterType, Double damagePct) {
        LandPlot plot = landPlotRepository.findById(plotId)
                .orElseThrow(() -> new RuntimeException("Land plot not found for ID: " + plotId));

        // Base rate payout: $10,000 / ₹10,000 per acre scaled by damage %
        double plotAcres = (plot.getAreaInAcres() != null) ? plot.getAreaInAcres() : 1.0;
        double basePayoutPerAcre = 10000.0;
        double calculatedPayout = plotAcres * basePayoutPerAcre * (damagePct / 100.0);

        DisasterReliefClaim claim = new DisasterReliefClaim();
        claim.setLandPlot(plot);
        claim.setDisasterType(disasterType.toUpperCase());
        claim.setEstimatedDamagePct(damagePct);
        claim.setPayoutAmount(calculatedPayout);
        claim.setClaimStatus("APPROVED");
        claim.setTriggeredAt(LocalDateTime.now());

        return claimRepository.save(claim);
    }

    /**
     * Fetches all disaster claims for a given plot.
     */
    public List<DisasterReliefClaim> getClaimsForPlot(Long plotId) {
        return claimRepository.findByLandPlot_PlotId(plotId);
    }
}