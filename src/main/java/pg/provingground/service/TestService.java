package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.GroundRentalRepository;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final GroundRentalRepository groundRentalRepository;
    private final CarRentalRepository carRentalRepository;



}
