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
        return memoRepository.findById(id);
    }
}
