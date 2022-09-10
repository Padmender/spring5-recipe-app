package guru.springframework.services;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements  UnitOfMeasureService{

    private final UnitOfMeasureRepo unitOfMeasureRepo;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepo unitOfMeasureRepo) {
        this.unitOfMeasureRepo = unitOfMeasureRepo;
    }

    @Override
    public Set<UnitOfMeasure> findAll() {
        Set<UnitOfMeasure> unitOfMeasures=new HashSet<>();
        unitOfMeasureRepo.findAll().iterator().forEachRemaining(unitOfMeasures::add);
        return unitOfMeasures;
    }
}
