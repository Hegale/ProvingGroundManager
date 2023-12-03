package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Ground;
import pg.provingground.repository.GroundRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroundService {

    private final GroundRepository groundRepository;

    @Transactional
    public void saveGround(Ground ground) {
        groundRepository.save(ground);
    }

    // TODO: 시험장 수정 메소드 구현
    // TODO: 시험장 삭제 메소드 구현

    public List<Ground> findItems() {
        return groundRepository.findAll();
    }

    public Ground findOne(Long groundId) {
        return groundRepository.findOne(groundId);
    }
}
