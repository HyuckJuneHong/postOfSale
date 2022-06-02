package kr.co.postofsale.record;

import org.springframework.stereotype.Repository;

@Repository
public class RecordRepositoryJDBC implements RecordRepository{

    @Override
    public RecordEntity save(RecordEntity recordEntity) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
