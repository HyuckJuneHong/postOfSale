package kr.co.postofsale.record;

public interface RecordRepository {

    RecordEntity save(RecordEntity recordEntity);

    void deleteAll();

}
