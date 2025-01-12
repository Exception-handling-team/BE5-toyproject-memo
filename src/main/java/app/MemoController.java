package app;

import java.util.Scanner;

public class MemoController {
    private final Scanner sc;
    private MemoService memoService;

    public MemoController(Scanner sc) {
        this.sc = sc;
        memoService = new MemoService();
    }

    public void list() {
        System.out.println("== 메모 목록 ==");
        memoService.list().forEach(memo -> {
            System.out.println(memo.getId() + " : " + memo.getTitle());
        });
    }



    public void write() {
        System.out.println("제목 : ");
        String title = sc.nextLine();
        System.out.println("내용 : ");
        String content = sc.nextLine();
        memoService.write(title, content);


    }

    public void delete() {
        System.out.println("삭제할 메모 번호 : ");
        int id = Integer.parseInt(sc.nextLine());
        memoService.delete(id);
    }

    public void exit() {

    }


    public void modify() {
        System.out.println("수정할 메모 번호 : ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println("제목 : ");
        String title = sc.nextLine();
        System.out.println("내용 : ");
        String content = sc.nextLine();
        memoService.modify(id, title, content);
    }

    public void read() {
        System.out.println("읽을 메모 번호 : ");
        int id = Integer.parseInt(sc.nextLine());
        Memo memo = memoService.read(id);
        System.out.println("제목 : " + memo.getTitle());
        System.out.println("내용 : " + memo.getContent());
    }
}
