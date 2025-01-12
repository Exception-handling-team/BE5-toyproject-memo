package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.Memo;
import global.AppConfig;
import standard.Util;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class MemoFileRepository {
 private static final String DB_PATH = AppConfig.getDbPath() + "/";
 private static final String LASTID_FILE_PATH = DB_PATH + "lastid.txt";
 private final ObjectMapper objectMapper = new ObjectMapper();

 public MemoFileRepository() {
     Init();
 }

 public void Init() {
     if(!Util.File.exists(LASTID_FILE_PATH)) {
         Util.File.createFile(LASTID_FILE_PATH);
     }

     if(!Util.File.exists(DB_PATH)) {
         Util.File.createDir(DB_PATH);
     }
 }

 public Memo save(Memo memo) {
     boolean isNew = memo.isNew();

     if(isNew) {
         memo.setId(getLastId() + 1);
     }

     try {
         String json = objectMapper.writeValueAsString(memo);
         String filePath = DB_PATH + memo.getId() + ".json";

         Util.File.write(filePath, json);

         if (isNew) {
             Util.File.write(LASTID_FILE_PATH, String.valueOf(memo.getId()));
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
     return memo;
 }

    public int getLastId() {
        String idStr = Util.File.readAsString(LASTID_FILE_PATH);

        if(idStr.isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public List<Memo> findAll() {
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 생성

        return Util.File.getPaths(DB_PATH).stream()
                .map(Path::toString) // 파일 경로를 문자열로 변환
                .filter(path -> path.endsWith(".json")) // JSON 파일 필터링
                .map(path -> { // JSON 파일을 Memo 객체로 변환
                    try {
                        String jsonContent = Util.File.readAsString(path); // JSON 파일 내용 읽기
                        return objectMapper.readValue(jsonContent, Memo.class); // JSON을 Memo 객체로 변환
                    } catch (Exception e) {
                        e.printStackTrace(); // 예외 처리
                        return null; // 변환 실패 시 null 반환
                    }
                })
                .filter(Objects::nonNull) // null 값 제거
                .toList(); // List로 수집
    }

    public void deleteById(int id) {
        String filePath = DB_PATH + id + ".json";
        Util.File.delete(filePath);
    }

    public Memo findById(int id) {
        String filePath = DB_PATH + id + ".json";

        try {
            String jsonContent = Util.File.readAsString(filePath);
            return objectMapper.readValue(jsonContent, Memo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
