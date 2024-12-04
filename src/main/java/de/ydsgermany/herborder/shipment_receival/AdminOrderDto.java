package de.ydsgermany.herborder.shipment_receival;

import de.ydsgermany.herborder.order.HerbQuantity;
import de.ydsgermany.herborder.order.HerbQuantityDto;
import de.ydsgermany.herborder.order.Order;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record AdminOrderDto(

    String externalId,

    @NotNull
    String firstName,

    @NotNull
    String lastName,

    @NotNull
    String mail,

    @NotNull
    List<HerbQuantityDto> herbs,

    Long price

) {

    public static AdminOrderDto from(Order order, Long price) {
        return AdminOrderDto.builder()
            .externalId(order.getExternalId())
            .firstName(order.getFirstName())
            .lastName(order.getLastName())
            .mail(order.getMail())
            .herbs(herbsFrom(order.getHerbs()))
            .price(price)
            .build();
    }

    private static List<HerbQuantityDto> herbsFrom(List<HerbQuantity> herbs) {
        return herbs.stream()
            .map(HerbQuantityDto::from)
            .toList();
    }


}