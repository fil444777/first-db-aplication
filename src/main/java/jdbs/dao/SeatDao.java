package jdbs.dao;

import jdbs.entity.Seat;

import java.util.List;
import java.util.Optional;

public class SeatDao implements Dao<Long, Seat>{
    @Override
    public boolean update(Seat ticket) {
        return false;
    }

    @Override
    public List<Seat> findAll() {
        return List.of();
    }

    @Override
    public Optional<Seat> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Seat save(Seat ticket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
