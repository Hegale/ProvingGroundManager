package pg.provingground.service;

import pg.provingground.domain.User;

public interface OwnershipService {
    boolean isOwnerMatched(Long entityId, User user);
}