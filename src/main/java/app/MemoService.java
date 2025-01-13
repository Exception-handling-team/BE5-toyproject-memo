package app;

import repository.MemoFileRepository;

import java.util.List;

public class MemoService {
    private final MemoFileRepository memoRepository;

    public MemoService() {
        memoRepository = new MemoFileRepository();
    }
    public Memo write(String title, String content) {
        Memo memo = new Memo(title, content);
        return memoRepository.save(memo);
    }

    public List<Memo> list() {
        return memoRepository.findAll();
    }

    public void delete(int id) {
        memoRepository.deleteById(id);
    }

    public void modify(int id, String title, String content) {
        Memo memo = memoRepository.findById(id);
        memo.setTitle(title);
        memo.setContent(content);
        memoRepository.save(memo);
    }

    public Memo read(int id) {
        Memo memo = memoRepository.findById(id);
        if (memo == null) { // 메모가 없는 경우 처리
            throw new IllegalArgumentException("해당 번호의 메모가 존재하지 않습니다.");
        }
        return memo;
    }
}
