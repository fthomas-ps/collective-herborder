package de.ydsgermany.herborder.shipment_receival;

import de.ydsgermany.herborder.order_batch.OrderBatch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBatchesAggregationRepository extends JpaRepository<OrderBatch, Long> {

    @Query("SELECT new de.ydsgermany.herborder.shipment_receival.AggregatedHerbItemsDto(h.name, SUM(hqa.herb_quantity), SUM(bi.quantity), SUM(si.quantity)) "
        + "FROM "
        + "  Herb h "
        + "  LEFT JOIN (SELECT hq.herb.id AS herb_id,SUM(hq.quantity) AS herb_quantity FROM HerbQuantity hq GROUP BY hq.herb.id )"
        + " hqa ON h.id = hqa.herb_id "
        + "  FULL OUTER JOIN BillHerbItem bi ON h.id = bi.herb.id "
        + "  FULL OUTER JOIN ShipmentHerbItem si ON h.id = si.herb.id "
        + "WHERE "
        + "  hqa.herb_quantity IS NOT NULL "
        + "  OR bi.quantity IS NOT NULL "
        + "  OR si.quantity IS NOT NULL "
        + "GROUP BY h.id, h.name "
    )
    List<AggregatedHerbItemsDto> aggregateOrders(Long orderBatchId);

}
