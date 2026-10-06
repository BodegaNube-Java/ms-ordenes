package cl.duoc.ms_ordenes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaStockRequestDto {

    private String ordenId;
    private List<ItemOrdenDto> items;
}