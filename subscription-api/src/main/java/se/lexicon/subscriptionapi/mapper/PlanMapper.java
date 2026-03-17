package se.lexicon.subscriptionapi.mapper;

import org.mapstruct.Mapper;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    Plan toEntity(Plan planRequest);
    PlanResponse toResponse(Plan plan);

}
